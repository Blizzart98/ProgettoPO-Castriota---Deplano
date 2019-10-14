package com.progetto.demo.model;
/**
 * Classe che descrive la struttura oggetti di tipo metadata.
 * Contiene costruttore, getters e setters.
 * @author castr
 *
 */
public class Metadata {

	private String alias;
	private String description;
	private String type;
	
	
	public Metadata(String alias, String description, String type) {
		super();
		this.alias = alias;
		this.description = description;
		this.type = type;
	}
	
	public void MetaData(String a,String d, String t)
	{
		this.alias=a;
		this.description=d;
		this.type=t;;
	}
	
	public String getAlias() {
		return alias;
	}


	public void setAlias(String alias) {
		this.alias = alias;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	
	
	

}
