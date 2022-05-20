package it.polito.tdp.bar.model;

import java.time.Duration;

public class Event implements Comparable<Event>{

	public enum EventType {
		ARRIVO_GRUPPO_CLIENTI,
		TAVOLO_LIBERATO
	}
	
	private EventType type;
	private Duration time; // int
	private int nPersone;
	private Duration durata; // int
	private double tolleranza;
	private Tavolo tavolo;
	
	public Event(EventType type, Duration time, int nPersone, Duration durata, double tolleranza, Tavolo tavolo) {
		super();
		this.type = type;
		this.time = time;
		this.nPersone = nPersone;
		this.durata = durata;
		this.tolleranza = tolleranza;
		this.tavolo = tavolo;
	}

	public EventType getType() {
		return type;
	}

	public Duration getTime() {
		return time;
	}

	public int getnPersone() {
		return nPersone;
	}

	public Duration getDurata() {
		return durata;
	}

	public double getTolleranza() {
		return tolleranza;
	}

	public Tavolo getTavolo() {
		return tavolo;
	}
	
	public void setTavolo(Tavolo tavolo) {
		this.tavolo = tavolo;
	}

	@Override
	public int compareTo(Event o) {
		return time.compareTo(o.getTime());
	}
	
	
	
}
