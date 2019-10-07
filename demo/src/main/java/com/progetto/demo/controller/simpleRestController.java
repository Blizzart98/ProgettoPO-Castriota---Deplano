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
	
	@GetMapping("/aid/data") //format ?filter=attributo:operatore:valore:opLogico:attributo2:operatore2:valore2 ecc.
	public Vector<Aid> getFiltered(@RequestParam(value="filter",defaultValue="", required=false) String filter) throws FileNotFoundException, IOException,NullPointerException
	{
		dataTab=DemoApplication.csv;
		
		if(filter.equals(""))
			{
				return dataTab;
			}
		else
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
	}
	
	
	@GetMapping("/aid/metadata")
	public Vector<Metadata> getMetadata() throws FileNotFoundException, IOException
	{
		metaTab=DemoApplication.meta;
		return metaTab;
	}
}