package com.progetto.demo.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.progetto.demo.DemoApplication;
import com.progetto.demo.model.Aid;
import com.progetto.demo.model.Stats;


@RestController 

public class StatsController {
	/**
	 * Classe per l'output di statistiche attraverso un filtro.
	 * La tabella viene scansionata a seconda dei parametri passati tramite formato indicato.
	 * I dati del vettore risultante vengono poi utlizzati per calcolare valore massimo, medio, minimo,
	 * deviazione standard, somma e conteggio.
	 * I dati vengono restituiti in formato JSON tramite un oggetto di tipo stats.
	 */

	Stats results = new Stats();
	private static Vector<Aid> dataTab;
	private static Vector<Aid> filtered=new Vector<Aid>();

	@GetMapping("/aid/stats")
	public Stats getFiltered(@RequestParam(value="filter",defaultValue="", required=false) String getCall) throws FileNotFoundException, IOException,NullPointerException
	{
		//format ?filter=attributo:valore:opLogico:attributo2:valore2 ecc.
		dataTab=DemoApplication.csv;
		Filter myFilter= new Filter();
		
		if(getCall.equals("")==false)
		{
			Filter.stringSplitter(getCall, ":", myFilter.getAttributeNames(),myFilter.getValues(),myFilter.getLogical());
			filtered=myFilter.applyFilters(dataTab);
		}
		results.setMax(filtered);
		results.setMin(filtered);
		results.setAvg(filtered);
		results.setDevStd(filtered, results.getAvg());
		results.setSum(filtered);
		results.setCount(filtered);
		
		return results;
	}
}