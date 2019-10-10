package com.progetto.demo.model;

import java.util.Vector;

public class Aid {

	private char freq;
	private String geo;
	private String unit;
	private String obj;
	private Vector<YA> aidList;
	
	//@metadati(alias="Frequenza", sourcefield="Frequenza di Rilevazione", type="Char")

	//Fornisce la frequenza dell'aid
	
	public char getFreq() {
		return freq;
	}
	
	//Imposta la frequenza dell'aid
	public void setFreq(char freq) {
		this.freq = freq;
	}
	
	//@metadati(alias="Geo", sourcefield="Stato contribuente", type="String")

	// Fornisce lo stato dell'aid
	public String getGeo() {
		return geo;
	}
	// Imposta lo stato dell'aid

	public void setGeo(String geo) {
		this.geo = geo;
	}
	
	//@metadati(alias="Unit", sourcefield="Unità di misura", type="String")

	//Fornisce unità di misura
	public String getUnit() {
		return unit;
	}
	
	//Imposta unità di misura

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
//	@metadati(alias="Obj", sourcefield="Codice dell'obbiettivo", type="String")

	// Fornisce l'obiettivo
	public String getObj() {
		return obj;
	}
	
	// Imposta l'obiettivo
	public void setObj(String obj) {
		this.obj = obj;
	}
	
//	@metadati(alias="Gdpdata", sourcefield="Vettore di aiuti", type="Char")

	// Fornisce il vettore YA
	public Vector<YA> getAidList() {
		return aidList;
	}
	
	// Imposta il vettore YA

	public void setAidList(Vector<YA> aidList) {
		this.aidList = aidList;
	}

	//Costruttore oggetto Aid 
	
	public Aid(char freq, String geo, String unit, String obj, Vector<YA> aidList) {
		super();
		this.freq = freq;
		this.geo = geo;
		this.unit = unit;
		this.obj = obj;
		this.aidList = aidList;
	}
	
	/**
	 * Costruttore di default
	 */
	public Aid() {
		this.freq='0';
		this.geo ="";
		this.unit = "";
		this.obj = "";
		this.aidList=null;
	}

	//Fornisce i dati dell'aid sotto forma di una stringa di testo

	@Override
	public String toString() {
		return "Aid [freq=" + freq + ", geo=" + geo + ", unit=" + unit + ", obj=" + obj + ", aidList=" + aidList + "]";
	}
	
	//Verifica se due oggetti Aid hanno gli stessi campi
	
	public boolean equals(Aid oggetto)
	{
		if(this.geo.equals(oggetto.getGeo()) && this.obj.equals(oggetto.getObj()))
			return true;
		else
			return false;
	}
	
	//Copia gli attributi dell'oggetto in un altro oggetto Aid
	
	public Aid copy()
	{
		Aid copy=new Aid();
		copy.setFreq(this.freq);
		copy.setGeo(this.geo);
		copy.setObj(this.obj);
		copy.setUnit(this.unit);
		copy.setAidList(this.aidList);
		
		return copy;
	}

}
