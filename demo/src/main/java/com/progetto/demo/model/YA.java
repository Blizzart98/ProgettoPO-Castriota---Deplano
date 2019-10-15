package com.progetto.demo.model;

/**
 * Classe YA usata per definire rilevazione dei singoli anni.
 * Contiene l'anno di misurazione ed il valore misurato.
 * @author castr
 *
 */


public class YA {
	private int year;
	private double value;
	
	public int getYear() {
		return year;
	}
	
	public void setYear(int year) {
		this.year = year;
	}

	public double getValue() {
		return value;
	}
	
	public void setValue(double value) {
		this.value = value;
	}
	
	public YA(int year, double value) {
		this.year = year;
		this.value = value;
	}
	
	public YA() {
		this.year = 0;
		this.value = 0;
	}

	@Override
	/**
	 * Restituisce i valori di YA come stringa
	 */
	public String toString() {
		return "YA [year=" + year + ", value=" + value + "]";
	}
	
	public String toStringJson() {
		return "date\":\""+ year +"\",\"value\":\"" + value+"\"";
	}
}
