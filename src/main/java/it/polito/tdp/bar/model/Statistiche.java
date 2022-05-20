package it.polito.tdp.bar.model;

public class Statistiche {

	private int clientiTot;
	private int clientiSoddisfatti;
	private int clientiInsoddisfatti;
	
	public Statistiche(int clientiTot, int clientiSoddisfatti, int clientiInsoddisfatti) {
		super();
		this.clientiTot = clientiTot;
		this.clientiSoddisfatti = clientiSoddisfatti;
		this.clientiInsoddisfatti = clientiInsoddisfatti;
	}
	
	public Statistiche() {
		this.clientiTot = 0;
		this.clientiSoddisfatti = 0;
		this.clientiInsoddisfatti = 0;
	}
	
	public int getClientiTot() {
		return clientiTot;
	}
	public int getClientiSoddisfatti() {
		return clientiSoddisfatti;
	}
	public int getClientiInsoddisfatti() {
		return clientiInsoddisfatti;
	}
	
	public void incrementaClienti(int n) {
		this.clientiTot += n;
	}
	
	public void incrementaSoddisfatti(int n) {
		this.clientiSoddisfatti += n;
	}
	
	public void incrementaInsoddisfatti(int n) {
		this.clientiInsoddisfatti += n;
	}
	
}
