package com.progetto.demo.controller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import com.progetto.demo.model.Aid;



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
	public static boolean check(Object value, String operator, String th) {
		if(th instanceof String && value instanceof String)
			return value.equals(th);
		
		return false;		
	}
	
	public static Vector<Aid> filterWithOp(Vector<Aid> source, Vector<String> attributeNames, Vector<String> value, Vector<String> logicalOperators)
	{	
	
		Vector<Aid> output=filterAttributes(attributeNames.get(0),value.get(0),source);
			
		if(logicalOperators.isEmpty()==false)
		{
			System.out.println("Sono entrato nella ricorsione");
			//se trovo un operatore AND
			if(logicalOperators.get(0).contentEquals("AND"))
			{	//devo applicare ulteriori filtri a quelli già applicati, il source sarà "filtered"
				//rimuovo valori, attributi e operatori già confrontati
				System.out.println("Ho trovato una AND");
				logicalOperators.removeElementAt(0);
				attributeNames.removeElementAt(0);
				value.removeElementAt(0);
				System.out.println(attributeNames.get(0));
				System.out.println(value.get(0));
				System.out.println(logicalOperators.size());
				return filterWithOp(output,attributeNames,value,logicalOperators);
			}
			else //trovo un operatore OR
			{ 	//devo applicare altri filtri ai dati di partenza, la source rimarrà "source"
				logicalOperators.removeElementAt(0); 
				attributeNames.removeElementAt(0);
				value.removeElementAt(0);
				return filterWithOp(source,attributeNames,value,logicalOperators);
			}
		}
		
		return output;
	}
}


