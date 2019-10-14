package com.progetto.demo.controller;

import java.util.Vector;

import com.progetto.demo.model.Aid;
import com.progetto.demo.model.YA;

public class Filter {

	

	private Vector<Aid> input;
	private Vector<Aid> output;
	private String call;
	
	private Vector<String> attributeNames;
	private Vector<String> values;
	private Vector<String> operators;
	private Vector<String> logical;
	
	
	
	//costruttore
	public Filter(Vector<Aid> input, Vector<Aid> output, String call, Vector<String> attributeNames,
			Vector<String> values, Vector<String> operators, Vector<String> logical) {
		super();
		this.input = input;
		this.output = output;
		this.call = call;
		this.attributeNames = attributeNames;
		this.values = values;
		this.operators = operators;
		this.logical = logical;
	}
	
	//costruttore di default
	public Filter()
	{
		this.input =new Vector<Aid>();
		this.output =new Vector<Aid>();
		this.call = "";
		this.attributeNames = new Vector<String>();
		this.values = new Vector<String>();
		this.operators = new Vector<String>();
		this.logical = new Vector<String>();
	}
	
	public Vector<Aid> getInput() {
		return input;
	}

	public void setInput(Vector<Aid> input) {
		this.input = input;
	}

	public Vector<Aid> getOutput() {
		return output;
	}

	public void setOutput(Vector<Aid> output) {
		this.output = output;
	}

	public String getCall() {
		return call;
	}

	public void setCall(String call) {
		this.call = call;
	}

	public Vector<String> getAttributeNames() {
		return attributeNames;
	}

	public void setAttributeNames(Vector<String> attributeNames) {
		this.attributeNames = attributeNames;
	}

	public Vector<String> getValues() {
		return values;
	}

	public void setValues(Vector<String> values) {
		this.values = values;
	}

	public Vector<String> getOperators() {
		return operators;
	}

	public void setOperators(Vector<String> operators) {
		this.operators = operators;
	}

	public Vector<String> getLogical() {
		return logical;
	}

	public void setLogical(Vector<String> logical) {
		this.logical = logical;
	}

	public Vector<Aid> filterAttributes(Vector<Aid> source)
	{
		String attribute= attributeNames.get(0);
		String value=values.get(0);
		
		Vector<Aid> appoggio=new Vector<Aid>();
		// for each object in tab (lista completa degli oggetti)
		for(int i=0; i<source.size();i++)
		{
			//switches different attributes based on "filter" parameter
			switch(attribute)
			{
				case "freq":
					if(source.get(i).getFreq()==(value.charAt(0)))
						appoggio.add(source.get(i));
					break;
				case "geo":
					if(source.get(i).getGeo().equals(value))
						appoggio.add(source.get(i));
					break;
				case "unit":
					if(source.get(i).getUnit().equals(value))
						appoggio.add(source.get(i));
					break;
				case "obj":
					if(source.get(i).getObj().equals(value))
						appoggio.add(source.get(i));
					break;	
					
				default: 
					System.out.println("Attributo non corretto");
					break;
			}
		}
		
		return appoggio;
	}
	
	public Vector<Aid> applyFilters(Vector<Aid> source)
	{	
		output=filterAttributes(source);
		
		if(logical.isEmpty()==false)
		{
		
			//se trovo un operatore AND
			if(logical.get(0).contentEquals("AND"))
			{	//devo applicare ulteriori filtri a quelli già applicati, il source sarà "filtered"
				//rimuovo valori, attributi e operatori già confrontati
				logical.removeElementAt(0);
				attributeNames.removeElementAt(0);
				values.removeElementAt(0);
				
				return filterAttributes(output);
			}
			else if(logical.get(0).contentEquals("OR")) //trovo un operatore OR
			{ 	//devo applicare altri filtri ai dati di partenza, la source sarà tutto l'input
				
				logical.removeElementAt(0); 
				attributeNames.removeElementAt(0);
				values.removeElementAt(0);
				Vector<Aid> temp=filterAttributes(source);
				output.addAll(temp);
				checkDuplicates(output);
				return output;
			}
		}
		
		return output;
	}

	
	public Aid filterSingle(Vector<Aid> source, String stato, String obiettivo)
	{
		Aid single= new Aid();
		
		call=("geo:"+ stato +":AND:obj:" + obiettivo);
		
		stringSplitter(call,":",attributeNames,values,logical);
		single=applyFilters(source).get(0);
		
		return single;
	}
	
	
	public Vector<YA> selectYears(Vector<YA> source, String operator, Double value)
	{
		Vector<YA> selezionati = new Vector<YA>();
		
		for(YA anno:source)
		{
			if(check(anno.getValue(),operator,value))
				{
					//System.out.println("Ho selezionato un anno  " + anno.getYear()); //debug
					selezionati.add(anno);
				}
		}
		
		return selezionati;
	}
	
	
	void filterYears(String operator,Double value,Vector<Aid> source)
	{
		Vector<YA> selected=new Vector<YA>();
		
		for(Aid oggetto:source)
		{
			for(YA anno:oggetto.getAidList())
			{
				if(check(anno.getValue(),operator,value))
					{
						//System.out.println("Ho selezionato un anno  " + anno.getYear()); //debug
						selected.add(anno);
					}
			}
			oggetto.setAidList(selected);
			selected.clear();
		}
	}
	
	//Metodo che effettua il confronto tra numeri o tra stringhe con gli operatori ><= e restituisce vero o falso
	public static boolean check(Object value, String operator, Object th) {
			
			if (th instanceof Number && value instanceof Number) {	
				Double thC = ((Number)th).doubleValue();
				Double valuec = ((Number)value).doubleValue();
				
				if (operator.equals("=="))
					return value.equals(th);
				else if (operator.equals(">"))
					return valuec > thC;
				else if (operator.equals("<"))
					return valuec < thC;
				else if (operator.equals("<="))
					return valuec<=thC;
				else if (operator.equals(">="))
					return valuec>=thC;
			}
			
			else if(th instanceof String && value instanceof String)
				return value.equals(th);
			
			return false;		
		}
	

	
	@SafeVarargs
	public static void stringSplitter(String source,String delimiter, Vector<String>...singoli )
	{
		int n = singoli.length;//numero di varArgs che passo al metodo
		String[] splitter = source.split(delimiter);//splitta la stringa in ingresso

		int maxSplit=splitter.length/n; //calcola il numero massimo di elementi per ogni vettore
		
		if(splitter.length%n >0)//tiene conto di divisioni non intere
			maxSplit++;
		
		//inserisce le stringhe splittate nei rispettivi vettori passati come parametri singoli
		for(int j=0;j<maxSplit;j++)
			for(int i=0;i<n;i++)
			{
				if(i+(n*j)<splitter.length)
				singoli[i].add(splitter[i+(n*j)]);
			}
			
	}
	
	public void checkDuplicates(Vector<Aid> vector)
	{
		for(int i=0;i<vector.size();i++)
			for(int j=i+1;j<vector.size();j++)
				if(vector.get(i).equals(vector.get(j)))
				{
					vector.removeElementAt(j);
				}
	
	}

	//elimina il primo elemento di ogni vettore passato come parametro

	public static void deleteFirstElement(Vector<String>...singoli)
	{
		for(int i=0;i<singoli.length;i++)
		{
			singoli[i].removeElementAt(0);
		}
	}
	

}
