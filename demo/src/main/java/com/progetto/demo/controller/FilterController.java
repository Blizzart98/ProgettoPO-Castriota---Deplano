package com.progetto.demo.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

import javax.servlet.http.HttpServletResponse;

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
	
	private static Vector<Aid> dataTab=new Vector<Aid>();
	private static Vector<Metadata> metaTab;
	
	
	
	@RequestMapping("/aid")
	public String controls()
	{
		return "Richieste disponibili: -/data: riceve i dati degli Aid in formato JSon \n -/metadata: riceve i metadati della classe Aid in formato JSon \n"
				;
	}
	
	@GetMapping("/aid/data") 
	public Vector<Aid> getFiltered(@RequestParam(value="filter",defaultValue="", required=false) String getCall, @RequestParam(value="yearFilter",defaultValue="", required=false) String getYearCall) throws FileNotFoundException, IOException,NullPointerException
	{
		//format ?filter=attributo:valore:opLogico:attributo2:valore2 ecc.
		//format ?yearFilter=aid:operatore:numero:AND:attributo:=:valore:opLogico:attributo2:=:valore2 ecc.
		dataTab=DemoApplication.csv;
		Filter myFilter=new Filter();
		myFilter.setInput(dataTab);

		if(getCall.equals("")==false)
		{
			//Converto la stringa di input della get in stringhe utilizzabili per filtrare i dati
			Filter.stringSplitter(getCall, ":", myFilter.getAttributeNames(), myFilter.getValues(), myFilter.getLogical());
			//Restituisco i valori filtrati come da richiesta
			return myFilter.applyFilters(myFilter.getInput());
			
		}
		
		else if(getYearCall.equals("")==false)
		{
			String operatore;
			Double valore;
			
			//faccio il parsing della stringa di chiamata
			Filter.stringSplitter(getYearCall,":", myFilter.getAttributeNames(),myFilter.getOperators(), myFilter.getValues(), myFilter.getLogical());
			
			//Estraggo gli operatori e valori
			operatore=myFilter.getOperators().get(0);
			valore=Double.parseDouble(myFilter.getValues().get(0));
			
			
			myFilter.filterYears(operatore,valore,myFilter.getInput());//Applica a tutto il dataset il filtro numerico
			//Rimuove dalle strutture la chiamata al filtro numerico
			Filter.deleteFirstElement(myFilter.getAttributeNames(),myFilter.getValues(),myFilter.getLogical());
			return myFilter.applyFilters(myFilter.getInput());//Applica i filtri normalmente
		}
		else
			return dataTab; //se nessun filtro è impostato ritorna l'intero dataSet
		
	}
	
	@GetMapping("/aid/data/YA/{operator}:{value}")
	public Vector<Aid> filterYA(@PathVariable String operator,@PathVariable double value,@RequestParam(value="filter",defaultValue="", required=false) String getCall)
	{
		dataTab.clear();
		dataTab.addAll(DemoApplication.csv);
		Filter myFilter=new Filter();
		myFilter.setInput(dataTab);

		if(getCall.equals("")==false)
		{
			//Converto la stringa di input della get in stringhe utilizzabili per filtrare i dati
			Filter.stringSplitter(getCall, ":", myFilter.getAttributeNames(), myFilter.getValues(), myFilter.getLogical());
			//Restituisco i valori filtrati come da richiesta
			myFilter.applyFilters(myFilter.getInput());	
			myFilter.filterYears(operator, value,myFilter.getOutput());
			return myFilter.getOutput();
		}
		else
			return myFilter.getInput();
	}
	
	@GetMapping("/aid/metadata")
	public Vector<Metadata> getMetadata() throws FileNotFoundException, IOException
	{
		metaTab=DemoApplication.meta;
		return metaTab;
	}

}
