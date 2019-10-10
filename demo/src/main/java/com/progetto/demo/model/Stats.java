package com.progetto.demo.model;

import java.util.Vector;

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
	

	public void setMax(Vector<Aid> v) {
		double max=0.0;
		for(Aid oggetto : v)
		{
			if(oggetto.getObj().equals("TOTAL"))
				break;
			for(int i=0; i<oggetto.getAidList().size();i++) {
				if(oggetto.getAidList().get(i).getValue() > max)
				max=oggetto.getAidList().get(i).getValue();
			}
		}
		this.max = max;
	}
	
	public Stats() {
		super();
		this.avg=0.00;
		this.min=0.00;
		this.max=0.00;
		this.devStd=0.00;
		this.sum=0.00;
		this.count = 0;

	}
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
	
	public void setAvg(Vector<Aid> v) {
		double avg=0;
		int j=0;
		for(Aid oggetto : v)
		{
			
			for(int i=0; i<oggetto.getAidList().size();i++) {
				if(oggetto.getObj().equals("TOTAL")==false)
				avg+=oggetto.getAidList().get(i).getValue();
				 j++;
		}
	}
		avg=Math.rint(avg/j*100)/100;
	this.avg=avg;
	}
	
	public void setDevStd(Vector<Aid> v, double m) {
		double dev=0;
		int j=0;
		for(Aid oggetto : v)
		{
			for(int i=0; i<oggetto.getAidList().size();i++) {
				dev=dev+Math.pow(((oggetto.getAidList().get(i).getValue())-m),2);
				j++;
							}
		}
		
		this.devStd = Math.rint(dev/j*100)/100;
	}
	
	
	
	public void setSum(Vector<Aid> v) {
		double sum=0;
		for(Aid oggetto : v)
		{
			for(int i=0; i<oggetto.getAidList().size(); i++) 
				sum+=oggetto.getAidList().get(i).getValue();						
		}
			
		this.sum = Math.rint(100*sum)/100;
	}
	public void setCount(Vector<Aid> v) {
		int count=0;
		for(Aid oggetto : v)
		{
			for(int i=0; i<oggetto.getAidList().size();i++) 
				count++;			
		}
		this.count = count;
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
