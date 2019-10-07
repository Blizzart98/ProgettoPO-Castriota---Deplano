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
	private static Vector<Aid> filtered;
	private static Vector<Metadata> metaTab;
	
	@RequestMapping("/aid")
	public String controls()
	{
		return "Richieste disponibili: -/get: riceve i dati degli Aid in formato JSon";
	}
	
	@GetMapping("/aid/data") 
	public Vector<Aid> getFiltered(@RequestParam(value="filter",defaultValue="", required=false) String filter, @RequestParam(value="sfilter",defaultValue="", required=false) String filterSingle) throws FileNotFoundException, IOException,NullPointerException
	{
		//format ?filter=attributo:operatore:valore:opLogico:attributo2:operatore2:valore2 ecc.
		//format ?sfilter=codiceStato,codiceObj,operatore,numero
		dataTab=DemoApplication.csv;
		filtered = new Vector<Aid>();
		
		if(filter.equals("")==false)
		{
			//Converto la stringa di input della get in stringhe utilizzabili per filtrare i dati
			Vector<String> attributi=new Vector<String>();
			Vector<String> operatori=new Vector<String>();
			Vector<String> valori=new Vector<String>();
			Vector<String> logicalOps=new Vector<String>();
			
			String[] singoli = filter.split(":");
	
			int maxSplit=singoli.length/4;
			
			if(singoli.length%4 >0)
				maxSplit++;
				
			for(int i=0;i<maxSplit;i++)
			{
				attributi.add(singoli[0 +(4*i)]);
				operatori.add(singoli[1 +(4*i)]);
				valori.add(singoli[2 +(4*i)]);
				if(singoli.length>(3+4*i))
					logicalOps.add(singoli[3 +(4*i)]);
			}
			
			
			filtered=filterUtils.filterWithOp(dataTab, attributi, valori, logicalOps);
			return filtered;
		}
		
		else if(filterSingle.equals("")==false)
		{
			Aid singoloAid,temp= new Aid();
			Vector<YA> selezionati= new Vector<YA>();
			Double valore;
			
			//Splitta la stringa di comando in arrivo dalla get [geo,obj,op,num]
			String[] splitter = filterSingle.split(",");
			
			valore=Double.parseDouble(splitter[3]);
			
			//ottiene il singolo oggetto corrispondente allo stato e all'obiettivo desiderati
			singoloAid=filterUtils.filterSingle(dataTab,splitter[0],splitter[1]);
			selezionati=filterUtils.filterYears(singoloAid.getAidList(),splitter[2],valore);
			
			//opera su temp in modo da non sovrascrivere la AidList originale (evita problemi in chiamate successive)
			//copio singoloAid in temp
			temp.setFreq(singoloAid.getFreq());
			temp.setUnit(singoloAid.getUnit());
			temp.setGeo(singoloAid.getGeo());
			temp.setObj(singoloAid.getObj());
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