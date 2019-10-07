package com.progetto.demo.controller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import com.progetto.demo.model.Aid;
import com.progetto.demo.model.YA;



public class filterUtils {
	
	//private static Vector<Aid> filtered=new Vector<Aid>() ;

	
	public static Vector<Aid> filterAttributes(String filter, String value, Vector<Aid> tab)
	{
		Vector<Aid> filtered = new Vector<Aid>();
		// for each object in tab (lista completa degli oggetti)
		for(Aid oggetto:tab)
		{
			//switches different attributes based on "filter" parameter
			
			switch(filter)
			{
				case "freq":
					if(oggetto.getFreq()==(value.charAt(0)))
						filtered.add(oggetto);
					break;
				case "geo":
					if(oggetto.getGeo().equals(value))
						filtered.add(oggetto);
					break;
				case "unit":
					if(oggetto.getUnit().equals(value))
						filtered.add(oggetto);
					break;
				case "obj":
					if(oggetto.getObj().equals(value))
							filtered.add(oggetto);
					break;	
					
				default: 
					break;
			}
		}
		return filtered;
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
	
	public static Vector<Aid> filterWithOp(Vector<Aid> source, Vector<String> attributeNames, Vector<String> value, Vector<String> logicalOperators)
	{	
	
		Vector<Aid> output=filterAttributes(attributeNames.get(0),value.get(0),source);
			
		if(logicalOperators.isEmpty()==false)
		{
		
			//se trovo un operatore AND
			if(logicalOperators.get(0).contentEquals("AND"))
			{	//devo applicare ulteriori filtri a quelli già applicati, il source sarà "filtered"
				//rimuovo valori, attributi e operatori già confrontati
			
				logicalOperators.removeElementAt(0);
				attributeNames.removeElementAt(0);
				value.removeElementAt(0);
				
				return filterWithOp(output,attributeNames,value,logicalOperators);
			}
			else if(logicalOperators.get(0).contentEquals("OR")) //trovo un operatore OR
			{ 	//devo applicare altri filtri ai dati di partenza, la source rimarrà "source"
				logicalOperators.removeElementAt(0); 
				attributeNames.removeElementAt(0);
				value.removeElementAt(0);
				return filterWithOp(source,attributeNames,value,logicalOperators);
			}
		}
		
		return output;
	}


	public static Aid filterSingle(Vector<Aid> source, String stato, String obiettivo)
	{
		Aid filtrato= new Aid();
		
		Vector<String> attributi = new Vector<String>();
		Vector<String> valori = new Vector<String>();
		Vector<String> logici = new Vector<String>();
		
		attributi.add("geo");
		attributi.add("obj");
		
		valori.add(stato);
		valori.add(obiettivo);
		logici.add("AND");
		
		filtrato=filterWithOp(source,attributi,valori,logici).get(0);
		return filtrato;
	}

	public static Vector<YA> filterYears(Vector<YA> source, String operator, Double value)
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
	
	
}