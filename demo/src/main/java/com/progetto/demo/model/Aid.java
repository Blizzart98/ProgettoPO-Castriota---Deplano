package com.progetto.demo.model;

import java.util.Vector;

/**
 * Classe che descrive la struttura degli oggetti di tipo Aid.
 * Ogni oggetto contiene la frequenza, la nazione, l'unità di misura, l'obbiettivo ed 
 * uun vettore di oggetti di tipo YA, ognuno definisce il contributo per uno specifico anno.
 * @author castr
 *
 */
public class Aid {


	private char freq;
	private String geo;
	private String unit;
	private String obj;
	private Vector<YA> aidList;

	
	public char getFreq() {
		return freq;
	}
	
	public void setFreq(char freq) {
		this.freq = freq;
	}
	
	public String getGeo() {
		return geo;
	}

	public void setGeo(String geo) {
		this.geo = geo;
	}
	
	public String getUnit() {
		return unit;
	}
	
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	public String getObj() {
		return obj;
	}

	public void setObj(String obj) {
		this.obj = obj;
	}

	public Vector<YA> getAidList() {
		return aidList;
	}
	
	public void setAidList(Vector<YA> aidList) {
		this.aidList = aidList;
	}
	
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

	

	@Override
	/**
	 * 	Fornisce i dati dell'aid sotto forma di una stringa di testo
	 */
	public String toString() {
		return "Aid [freq=" + freq + ", geo=" + geo + ", unit=" + unit + ", obj=" + obj + ", aidList=" + aidList + "]";
	}
	
	/**
	 * Verifica se due oggetti Aid hanno gli stessi campi
	 * @param oggetto
	 * @return
	 */
	
	public boolean equals(Aid oggetto)
	{
		if(this.geo.equals(oggetto.getGeo()) && this.obj.equals(oggetto.getObj()))
			return true;
		else
			return false;
	}
	
	/**
	 * Copia gli attributi dell'oggetto in un altro oggetto Aid
	 * @return
	 */
	
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
