package com.progetto.demo.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.progetto.demo.DemoApplication;
import com.progetto.demo.model.Aid;
import com.progetto.demo.model.Metadata;


/**
 * Classe che funziona da RestController per le WebAPI.
 * Permette di gestire le richieste di dati, metadati, dei filtri sui dati e visualizzare i dati filtrati
 * @author Lorenzo Deplano
 * 
 */

@RestController
public class FilterController {
	
	private static Vector<Aid> dataTab;
	private static Vector<Metadata> metaTab;
	

	/**
	 * Metodo che restituisce il dataset completo o filtrato in base alla stringa passata tramite
	 * richiesta Get.
	 * Formato: /aid/data?filter=attributo:valore:opLogico:attributo2:valore2:...  
	 * @param getCall, stringa che contiene il valore dell'attributo "filter" della richiesta
	 * @return Vettore di oggetti di tipo Aid che costituiscono il dataset intero oppure filtrato in base alla richiesta
	 */
	@GetMapping("/aid/data") //format ?filter=attributo:valore:opLogico:attributo2:valore2...
	public Vector<Aid> filterAid(@RequestParam(value="filter",defaultValue="", required=false) String getCall)
	{
		
		dataTab=DemoApplication.getCsv(); //acquisisce il dataset convertito in vettore di oggetti dopo il download è il parsing
		Filter myFilter=new Filter(); //inizializza un nuovo oggetto filter per applicare i filtri
		myFilter.setInput(dataTab); //inserisce in input al filter il dataset
	
		if(getCall.equals("")==false) //controlla che la stringa che contiene i parametri per i filtri non sia vuota
		{
			//Converte la stringa di input della get in stringhe utilizzabili per filtrare i dati
			Filter.stringSplitter(getCall, ":", myFilter.getAttributeNames(), myFilter.getValues(), myFilter.getLogical());
			if(checkAttributes(myFilter.getAttributeNames())) //se gli attributi passati nella richiesta esistono
			
				//Restituisce i valori filtrati come da richiesta
				return myFilter.applyFilters(myFilter.getInput());
			
			else //stampa un errore in console e ritorna un vettore vuoto
			{
				System.out.println("Attributi non corretti, non è stato possibile filtrare");
				return null;
			}

		}
		else //se nessun filtro è impostato ritorna l'intero dataSet
			return dataTab; 
		
	}
	
	/**
	 * Metodo che restituisce il dataset completo o filtrato in base alla stringa passata tramite
	 * richiesta Get. Consente di applicare anche filtri numerici condizionali.
	 * Formato: /aid/data/YA/opCond:numero?filter=attributo:valore:opLogico:attributo2:valore2:...  
	 * @param operator, operatore condizionale su cui effettuare il filtro numerico
	 * @param value, valore numerico da confrontare
	 * @param getCall, stringa che contiene il valore dell'attributo "filter" della richiesta
	 * @return Vettore di oggetti di tipo Aid che costituiscono il dataset intero oppure filtrato in base alla richiesta
	 */
	@GetMapping("/aid/data/YA/{operator}:{value}") //format -/opCond:numero?filter=attributo:valore:opLogico:attributo2:valore2:...
	public Vector<Aid> filterYA(@PathVariable String operator,@PathVariable double value,@RequestParam(value="filter",defaultValue="", required=false) String getCall)
	{
		dataTab=DemoApplication.getCsv(); //acquisisce il dataset convertito in vettore di oggetti dopo il download è il parsing
		Filter myFilter=new Filter(); //inizializza un nuovo oggetto filter per applicare i filtri
		Vector<Aid> local; //vettore locale temporaneo per immagazzinare l'output parziale dopo il filtro numerico
		
		myFilter.setInput(dataTab); //inserisce in input al filter il dataset
		local = myFilter.filterYears(operator, value); //applica il filtro numerico
		
		if(getCall.equals("")==false) //controlla che la stringa che contiene i parametri per i filtri non sia vuota
		{
			//Converto la stringa di input della get in stringhe utilizzabili per filtrare i dati
			Filter.stringSplitter(getCall, ":", myFilter.getAttributeNames(), myFilter.getValues(), myFilter.getLogical());
			
			if(checkAttributes(myFilter.getAttributeNames())) //se gli attributi passati nella richiesta esistono
			//Restituisce i dati filtrati come da richiesta
			return myFilter.applyFilters(local);
			
			else //stampa un errore in console e ritorna un vettore vuoto
			{
				System.out.println("Attributi non corretti, non è stato possibile filtrare");
				return null;
			}
		}
		else //se nessun filtro sugli attributi è impostato ritorna l'intero dataSet dopo il filtro numerico
			return local;
	}
	
	/**
	 * Metodo che restituisce i metadati
	 * @return Vettore di oggetti Metadata
	 */
	@GetMapping("/aid/metadata")
	public Vector<Metadata> getMetadata()
	{
		metaTab=DemoApplication.getMeta(); //acquisisce i metadati dopo il parsing
		return metaTab;
	}

	/**
	 * Metodo per verificare che gli attributi dei filtri richiesti siano compatibili con gli attributi degli
	 * oggetti Aid da filtrare
	 * @param attributes, vettore di stringhe (attributi) da verificare
	 * @return true se gli attributi sono compatibili, false altrimenti
	 */
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
