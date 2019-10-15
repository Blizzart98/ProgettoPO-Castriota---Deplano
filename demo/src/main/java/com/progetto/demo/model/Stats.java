package com.progetto.demo.model;

import java.util.Vector;
/**
 * Classe che definisce elementi di tipo stats, utile per calcolo statistiche sui dati del csv
 * @author Piero Castriota
 *
 */
public class Stats {

	private double avg;
	private double max;
	private double min;
	private double devStd;
	private double sum;
	private int count;
	
	public double getAvg() {
		return avg;
	}
	public void setAvg(double avg) {
		this.avg = avg;
	}
	public double getMax() {
		return max;
	}
	public void setMax(double max) {
		this.max = max;
	}
	public double getMin() {
		return min;
	}
	public void setMin(double min) {
		this.min = min;
	}
	public double getDevStd() {
		return devStd;
	}
	public void setDevStd(double devStd) {
		this.devStd = devStd;
	}
	public double getSum() {
		return sum;
	}
	public void setSum(double sum) {
		this.sum = sum;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
/**
 * Setter di max nel caso in cui gli venga passato un vettore di tipo Aid
 * La funzione cerca all'interno del vettore il valore massimo escludendo i valori dei totali.
 * Nel caso in cui siano presenti solo valori totali, restituisce il massimo tra essi.
 * @param v, vettore di oggetti Aid
 */
	public void setMax(Vector<Aid> v) {
		double max=0.0;
		for(Aid oggetto : v)
		{
			if(oggetto.getObj().equals("TOTAL")==false) {
			for(int i=0; i<oggetto.getAidList().size();i++) {
				if(oggetto.getAidList().get(i).getValue() > max)
				max=oggetto.getAidList().get(i).getValue();
			}
			}
				
			}
		if(max==0)	{
			for(Aid oggetto : v) {
			for(int i=0; i<oggetto.getAidList().size();i++) {
				if(oggetto.getAidList().get(i).getValue() > max)
				max=oggetto.getAidList().get(i).getValue();
			}
			}
		}
		this.max = max;
	}
	
	/**
	 * Costruttore di default
	 */
	public Stats() {
		super();
		this.avg=0.00;
		this.min=0.00;
		this.max=0.00;
		this.devStd=0.00;
		this.sum=0.00;
		this.count = 0;

	}
	
	/**
	 * Setter di min nel caso in cui gli venga passato un vettore di tipo Aid
	 * La funzione cerca all'interno del vettore il valore minimo.
	 * Inizialmente viene assunto come minimo il primo valore del vettore.
	 * Come valore minimo non viene accettato lo 0.
	 * @param v, vettore di oggetti Aid
	 */
	public void setMin(Vector<Aid> v) {
		double min=0;
		int j=0;
		for(Aid oggetto : v)
		{
			if(j==0) {
				min=oggetto.getAidList().get(0).getValue();
				j++;
			}
			
			for(int i=0; i<oggetto.getAidList().size();i++) {
				
				if(oggetto.getAidList().get(i).getValue() < min && oggetto.getAidList().get(i).getValue()!=0 )
				min=oggetto.getAidList().get(i).getValue();
							}
		}
		this.min = min;
	}
	
	/**
	 * Setter di avg nel caso in cui gli venga passato un vettore di tipo Aid.
	 * La funzione calcola il valore medio degli elementi del vettore.
	 * Nel calcolo non vengono inclusi i valori con obbiettivo "TOTAL".
	 * Nel caso vi siano solo oggetti con obbietivi "TOTAL" restituisce la media tra essi
	 * @param v, vettore di oggetti Aid
	 */
	public void setAvg(Vector<Aid> v) {
		double avg=0;
		int j=0;
		for(Aid oggetto : v)
		{
			
			for(int i=0; i<oggetto.getAidList().size();i++) {
				if(oggetto.getObj().equals("TOTAL")==false) {
				avg+=oggetto.getAidList().get(i).getValue();
				 j++;
				}
		}
	}
		if(avg==0)	{
			for(Aid oggetto : v) {
			for(int i=0; i<oggetto.getAidList().size();i++) {
				avg+=oggetto.getAidList().get(i).getValue();
				 j++;
			}
			}
			}
		avg=Math.rint(avg/j*100)/100;
	this.avg=avg;
	}
	
	/**
	 * Setter di devStd nel caso in cui gli venga passato un vettore di tipo Aid.
	 * La funzione calcola la deviazione standard degli elementi presenti nel vettore.
	 * Nel calcolo non vengono inclusi i valori con obbiettivo "TOTAL".
	 * Nel caso vi siano solo oggetti con obbietivi "TOTAL" restituisce la deviazione
	 * standard tra essi.
	 * Questa funzione è utilizzata anche per incrementare il contatore.
	 * @param v, vettore di oggetti di tipo Aid.
	 * @param m, media aritmetica dei valori.
	 */
	public void setDevStd(Vector<Aid> v, double m) {
		double dev=0;
		for(Aid oggetto : v)
		{
			for(int i=0; i<oggetto.getAidList().size();i++) {
				if(oggetto.getObj().equals("TOTAL")==false) {
					dev=dev+Math.pow(((oggetto.getAidList().get(i).getValue())-m),2);
				count++;
				}
		}
	}
		if(dev==0)	{
			for(Aid oggetto : v) {
			for(int i=0; i<oggetto.getAidList().size();i++) {
				dev=dev+Math.pow(((oggetto.getAidList().get(i).getValue())-m),2);
				count++;
			}
			}
			}
			dev=Math.sqrt(dev);
		
		this.devStd = Math.rint(dev/count*100)/100;
	}
	
	
	/**Setter di sum nel caso in cui gli venga passato un vettore di tipo Aid
	 * La funzione calcola la somma dei valori all'interno del vettore.
	 * Nel calcolo non vengono inclusi i valori con obbiettivo "TOTAL".
	 * Nel caso vi siano solo oggetti con obbietivi "TOTAL" restituisce la somma tra essi  
	 * @param v, vettore di oggetti Aid
	 * */
	public void setSum(Vector<Aid> v) {
		double sum=0;
		for(Aid oggetto : v)
		{
			if(oggetto.getObj().equals("TOTAL")==false) {
			for(int i=0; i<oggetto.getAidList().size(); i++) 
				sum+=oggetto.getAidList().get(i).getValue();
			}
		}
		
		if(sum==0) {
			for(Aid oggetto : v)
			for(int i=0; i<oggetto.getAidList().size(); i++) 
				sum+=oggetto.getAidList().get(i).getValue();
		}
		this.sum = Math.rint(100*sum)/100;
	}
	

	public Stats(double avg, double max, double min, double devStd, double sum, int count) {
		super();
		this.avg = avg;
		this.max = max;
		this.min = min;
		this.devStd = devStd;
		this.sum = sum;
		this.count = count;
	}


}
