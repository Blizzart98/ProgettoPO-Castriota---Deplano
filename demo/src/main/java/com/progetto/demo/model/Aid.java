package com.progetto.demo.model;

public class Aid {
	private String freq;
	private String geo;
	private String unit;
	private String Objective;
	private float[] aids = new float[18];
	
	public String getFreq() {
		return freq;
	}
	public void setFreq(String freq) {
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
	public String getObjective() {
		return Objective;
	}
	public void setObjective(String objective) {
		Objective = objective;
	}
	public float[] getAids() {
		return aids;
	}
	public void setAids(float[] aids) {
		this.aids = aids;
	}


	public Aid(String freq, String geo, String unit, String objective, float[] aids) {
		super();
		this.freq = freq;
		this.geo = geo;
		this.unit = unit;
		this.Objective = objective;
		this.aids = aids;
	}

}
