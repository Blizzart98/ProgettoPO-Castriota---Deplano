package com.progetto.demo.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.progetto.demo.DemoApplication;
import com.progetto.demo.model.Aid;
import com.progetto.demo.model.Metadata;


@RestController

public class FilterController {
	
	private static Vector<Aid> dataTab;
	private static Vector<Metadata> metaTab;
	
	
	
	@RequestMapping("/aid")
	public String controls()
	{
		return "Richieste disponibili: -/data: riceve i dati degli Aid in formato JSon    -/metadata: riceve i metadati della classe Aid in formato JSon";
	}
	
	@GetMapping("/aid/data") 
	public Vector<Aid> getFiltered(@RequestParam(value="filter",defaultValue="", required=false) String getCall) throws FileNotFoundException, IOException,NullPointerException
	{
		//format ?filter=attributo:valore:opLogico:attributo2:valore2 ecc.
		dataTab=DemoApplication.csv;
		Filter myFilter=new Filter();
		myFilter.setInput(dataTab);

		if(getCall.equals("")==false)
		{
			
			//Converto la stringa di input della get in stringhe utilizzabili per filtrare i dati
			Filter.stringSplitter(getCall, ":", myFilter.getAttributeNames(), myFilter.getValues(), myFilter.getLogical());
			if(checkAttributes(myFilter.getAttributeNames()))
			//Restituisco i valori filtrati come da richiesta
			return myFilter.applyFilters(myFilter.getInput());
			
			else
			{
				System.out.println("Attributi non corretti, non è stato possibile filtrare");
				return null;
			}

		}
		else
			return dataTab; //se nessun filtro è impostato ritorna l'intero dataSet
		
	}
	
	@GetMapping("/aid/data/YA/{operator}:{value}") //format -/operatore:valore?filter=attributo1:valore1:opLogico:attributo2:valore2...
	public Vector<Aid> filterYA(@PathVariable String operator,@PathVariable double value,@RequestParam(value="filter",defaultValue="", required=false) String getCall)
	{
		dataTab=DemoApplication.csv;
		Filter myFilter=new Filter();
		Vector<Aid> appoggio; //
		
		myFilter.setInput(dataTab);
		appoggio = myFilter.filterYears(operator, value);
		
		if(getCall.equals("")==false)
		{
			//Converto la stringa di input della get in stringhe utilizzabili per filtrare i dati
			Filter.stringSplitter(getCall, ":", myFilter.getAttributeNames(), myFilter.getValues(), myFilter.getLogical());
			//Restituisco i valori filtrati come da richiesta
			if(checkAttributes(myFilter.getAttributeNames()))
			//Restituisco i valori filtrati come da richiesta
			return myFilter.applyFilters(myFilter.getInput());
			else
			{
				System.out.println("Attributi non corretti, non è stato possibile filtrare");
				return null;
			}
		}
		else
			return appoggio;
	}
	
	@GetMapping("/aid/metadata")
	public Vector<Metadata> getMetadata() throws FileNotFoundException, IOException
	{
		metaTab=DemoApplication.meta;
		return metaTab;
	}

	public boolean checkAttributes(Vector<String> attributes)
	{
		for (int i = 0; i < attributes.size(); i++) {
			if (attributes.get(i).equals("geo") || attributes.get(i).equals("obj") || attributes.get(i).equals("freq")|| attributes.get(i).equals("unit")) 
			{

			} 
			else
				return false;
		}
		return true;
	}
}
