package com.progetto.demo.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.progetto.demo.model.Aid;
import com.progetto.demo.url.Parsing;

@RestController


public class simpleRestController {
	
	private static Vector<Aid> tab;
	private static Vector<Aid> filtered;
	
	@RequestMapping("/aid")
	public String controls()
	{
		return "Richieste disponibili: -/get: riceve i dati degli Aid in formato JSon";
	}
	
	@GetMapping("/aid/data")
	public Vector<Aid> getFiltered(@RequestParam(value="filter",defaultValue="", required=false) String filter) throws FileNotFoundException, IOException,NullPointerException
	{
		tab=Parsing.fileParsing();
		
		if(filter.equals(""))
			{
				return tab;
			}
		else
		{
			String[] attributes=filter.split(":");
			filtered=filterUtils.filterAttributes(attributes[0],attributes[1],tab);
			return filtered;
		}
	}
}