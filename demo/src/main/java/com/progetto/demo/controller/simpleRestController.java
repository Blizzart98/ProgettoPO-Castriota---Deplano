package com.progetto.demo.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.progetto.demo.DemoApplication;
import com.progetto.demo.model.Aid;
import com.progetto.demo.model.Metadata;
import com.progetto.demo.model.YA;


@RestController


public class simpleRestController {
	
	private static Vector<Aid> dataTab;
	private static Vector<Aid> filtered=new Vector<Aid>();
	private static Vector<Metadata> metaTab;
	
	@RequestMapping("/aid")
	public String controls()
	{
		return "Richieste disponibili: -/get: riceve i dati degli Aid in formato JSon";
	}
	
	@GetMapping("/aid/data") 
	public Vector<Aid> getFiltered(@RequestParam(value="filter",defaultValue="", required=false) String filter, @RequestParam(value="sfilter",defaultValue="", required=false) String filterSingle) throws FileNotFoundException, IOException,NullPointerException
	{
		//format ?filter=attributo:valore:opLogico:attributo2:valore2 ecc.
		//format ?sfilter=codiceStato,codiceObj,operatore,numero
		dataTab=DemoApplication.csv;
		Vector<String> attributi=new Vector<String>();
		Vector<String> valori=new Vector<String>();
		Vector<String> logicalOps=new Vector<String>();
		
		if(filter.equals("")==false)
		{
			//Converto la stringa di input della get in stringhe utilizzabili per filtrare i dati
			filterUtils.stringSplitter(filter, ":", attributi,valori,logicalOps);
			//Restituisco i valori filtrati come da richiesta
			filtered=filterUtils.filterWithOp(dataTab, attributi, valori, logicalOps);
			return filtered;
		}
		
		else if(filterSingle.equals("")==false)
		{
			Aid singoloAid,temp= new Aid();
			Vector<YA> selezionati= new Vector<YA>();
			Vector<String> splitter=new Vector<String>();
			Double valore;
			
			//Splitta la stringa di comando in arrivo dalla get [geo,obj,op,num]
			filterUtils.stringSplitter(filterSingle,",",splitter);		
			valore=Double.parseDouble(splitter.get(3));
			
			//ottiene il singolo oggetto corrispondente allo stato e all'obiettivo desiderati
			singoloAid=filterUtils.filterSingle(dataTab,splitter.get(0),splitter.get(1));
			selezionati=filterUtils.filterYears(singoloAid.getAidList(),splitter.get(2),valore);
			
			//opera su temp in modo da non sovrascrivere la AidList originale (evita problemi in chiamate successive)
			//copio singoloAid in temp
			temp=singoloAid.copy();
			//non ho modificato la AidList di SingoloAid
			temp.setAidList(selezionati);
			
			//System.out.println(temp.toString()); //debug
			
			filtered.add(temp);
			return filtered;
		}
		else
			return dataTab; //se nessun filtro è impostato ritorna l'intero dataSet
		
	}
	
	
	@GetMapping("/aid/metadata")
	public Vector<Metadata> getMetadata() throws FileNotFoundException, IOException
	{
		metaTab=DemoApplication.meta;
		return metaTab;
	}
}