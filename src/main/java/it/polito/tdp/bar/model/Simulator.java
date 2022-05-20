package it.polito.tdp.bar.model;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import it.polito.tdp.bar.model.Event.EventType;

public class Simulator {
	
	// Modello
	private List<Tavolo> tavoli = new ArrayList<>();
	
	// Parametri della simulazione
	private final int NUM_EVENTI=2000;
	private final int T_ARRIVO_MAX = 10;
	private final int NUM_PERSONE_MAX = 10;
	private final int DURATA_MIN = 60;
	private final int DURATA_MAX = 120;
	private final double TOLLERANZA_MAX = 0.9;
	private final double OCCUPAZIONE_MAX = 0.5;
	
	// Coda degli eventi
	private Queue<Event> queue;
	
	// output della simulazione
	private Statistiche statistiche;
	
	public void init() {
		this.queue = new PriorityQueue<Event>();
		this.statistiche = new Statistiche();
		
		this.creaTavoli();
		this.creaEventi();
	}
	
	private void creaEventi() {
		
		Duration arrivo = Duration.ofMinutes(0);
		
		for(int i=0; i<this.NUM_EVENTI; i++) {
			
			int nPersone = (int) (Math.random() * this.NUM_PERSONE_MAX + 1); //aggiungo 1 perché altrimenti il max raggiungibile è 9.99999 ovvero 9
			Duration durata = Duration.ofMinutes( this.DURATA_MIN + (int)(Math.random() * (this.DURATA_MAX - this.DURATA_MIN) + 1) );
			double tolleranza = Math.random() * this.TOLLERANZA_MAX;
			
			this.queue.add(new Event(EventType.ARRIVO_GRUPPO_CLIENTI, arrivo, nPersone, durata, tolleranza, null));
			
			arrivo = arrivo.plusMinutes((int)(Math.random()*this.T_ARRIVO_MAX));
		}
	}

	private void creaTavolo(int quantita, int dimensione) {
		for(int i=0; i<quantita; i++) {
			this.tavoli.add(new Tavolo(dimensione));
		}
	}

	private void creaTavoli() {
		this.creaTavolo(2,10);
		this.creaTavolo(4,8);
		this.creaTavolo(4,6);
		this.creaTavolo(5,4);
		
		Collections.sort(tavoli, new Comparator<Tavolo>() {
			@Override
			public int compare(Tavolo o1, Tavolo o2) {
				return o1.getPosti()-o2.getPosti();
			}
		}
		);
	}
	
	public void run() {
		while(!queue.isEmpty()) {
			Event e = queue.poll();
			this.processEvent(e);
		}
	}

	private void processEvent(Event e) {
		switch (e.getType()){
		case ARRIVO_GRUPPO_CLIENTI:
			// aggiorno statistiche di clienti arrivati
			this.statistiche.incrementaClienti(e.getnPersone());
			
			// ricerca di un tavolo
			Tavolo tavolo = null;
			for(Tavolo t : this.tavoli) {
				if(!t.isOccupato() && t.getPosti() >= e.getnPersone() && ((t.getPosti()*this.OCCUPAZIONE_MAX) <= e.getnPersone())) {
					tavolo = t;
					break;
				}
			}
			if(tavolo != null) {
				System.out.format("Trovato un tavolo da %d, per %d persone\n", tavolo.getPosti(), e.getnPersone());
				this.statistiche.incrementaSoddisfatti(e.getnPersone());
				tavolo.setOccupato(true);
				e.setTavolo(tavolo); // inutile =)
				
				// evento per segnalare quando se ne andranno
				this.queue.add(new Event(EventType.TAVOLO_LIBERATO, e.getTime().plus(e.getDurata()), e.getnPersone(), e.getDurata(), e.getTolleranza(), tavolo));
			} else {
				// tavoli occupati :( resta solo il bancone
				Double probBancone = Math.random();
				if(probBancone <= e.getTolleranza()) { // i clienti si fermano al bancone yuppie
					System.out.format("%d persone si fermano al bancone\n", e.getnPersone());
					this.statistiche.incrementaSoddisfatti(e.getnPersone());
				} else { // insoddisfatti :(
					System.out.format("%d persone se ne vanno a casa\n", e.getnPersone());
					this.statistiche.incrementaInsoddisfatti(e.getnPersone());
				}
			}
			break;
			
		case TAVOLO_LIBERATO:
			e.getTavolo().setOccupato(false);
			break;
		}
		
	}

}
