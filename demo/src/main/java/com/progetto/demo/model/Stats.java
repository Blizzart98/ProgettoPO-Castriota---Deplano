package com.progetto.demo.model;

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
	public double getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
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
