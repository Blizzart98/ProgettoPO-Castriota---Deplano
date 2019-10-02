package com.progetto.demo.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.progetto.demo.model.Aid;
import com.progetto.demo.url.Parsing;

@RestController


public class simpleRestController {
	
	public static Vector<Aid> tab;
	
	/*	@GetMapping("/aid")
	 * public Aid getObj() throws FileNotException, IOException {
	return new Aid();
	}*/
	
	@RequestMapping("/aid")
	public String controls()
	{
		return "Richieste disponibili: -/get: riceve i dati degli Aid in formato JSon";
	}
	@GetMapping("/aid/get")
	public Vector<Aid> getAll()//Stampa l'intera collezione di oggetti aid convertita dal file alla richiesta "-/get"
	{
		try {
			tab=Parsing.fileParsing();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tab;
	}
}