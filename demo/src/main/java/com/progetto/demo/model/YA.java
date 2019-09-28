package com.progetto.demo.model;

import java.io.IOException;

/*
 * Classe composta dall'anno e dall'aiuto
 */


public class YA {
	private int year;
	private double value;
	
//	@metadati(alias="Year", sourcefield="Data Rilevazione", type="int")

	//Fornisce l'anno del YA
	public int getYear() {
		return year;
	}
	
	//Imposta l'anno del YA

	
	public void setYear(int year) {
		this.year = year;
	}
	
	//Fornisce il valore del YA

	public double getValue() {
		return value;
	}
	
	//Imposta il valore del YA
	
	public void setValue(double value) {
		this.value = value;
	}
	
	//Costruttore classe passati i parametri
	public YA(int year, double value) {
		this.year = year;
		this.value = value;
	}
	
	//Costruttore classe default
	public YA() {
		this.year = 0;
		this.value = 0;
	}

	//Restituisce i dati del YA sotto forma di stringa
	@Override
	public String toString() {
		return "YA [year=" + year + ", value=" + value + "]";
	}
	
	public String toStringJson() {
		return "date\":\""+ year +"\",\"value\":\"" + value+"\"";
	}
	
	

}
