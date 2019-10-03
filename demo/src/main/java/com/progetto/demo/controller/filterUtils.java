package com.progetto.demo.controller;

import java.util.Vector;

import com.progetto.demo.model.Aid;
import com.progetto.demo.model.YA;


public class filterUtils {
	
	private static Vector<Aid> filtered=new Vector<Aid>() ;

	
	public static Vector<Aid> filterAttributes(String filter, String value, Vector<Aid> tab)
	{
		
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
}


