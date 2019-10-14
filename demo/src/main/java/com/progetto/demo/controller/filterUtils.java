package com.progetto.demo.controller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import com.progetto.demo.model.Aid;
import com.progetto.demo.model.YA;



public class filterUtils {


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
					System.out.println("Attributo non corretto");
					break;
			}
		}
		return filtered;
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
				String precedente=value.get(0);
				logicalOperators.removeElementAt(0); 
				attributeNames.removeElementAt(0);
				value.removeElementAt(0);
				Vector<Aid> temp=filterWithOp(source,attributeNames,value,logicalOperators);
				output.addAll(temp);
				checkDuplicates(output);
				return output;
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
		
		String call=("geo:"+ stato +":AND:obj:" + obiettivo);
		
		stringSplitter(call,":",attributi,valori,logici);
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
	
	public static void checkDuplicates(Vector<Aid> input)
	{
		for(int i=0;i<input.size();i++)
			for(int j=i+1;j<input.size();j++)
				if(input.get(i).equals(input.get(j)))
				{
					input.removeElementAt(j);
				}
	
	}


}