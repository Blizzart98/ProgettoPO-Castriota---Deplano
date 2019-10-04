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
import com.progetto.demo.url.ParseMetadata;
import com.progetto.demo.url.Parsing;

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
	public Vector<Aid> getFiltered(@RequestParam(value="filter",defaultValue="", required=false) String filter) throws FileNotFoundException, IOException,NullPointerException
	{
		dataTab=DemoApplication.csv;
		
		if(filter.equals(""))
			{
				return dataTab;
			}
		else
		{
			String[] attributes=filter.split(":");
			filtered=filterUtils.filterAttributes(attributes[0],attributes[1],dataTab);
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