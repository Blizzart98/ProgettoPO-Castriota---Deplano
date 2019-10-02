package com.progetto.demo;
import com.progetto.demo.model.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.Vector;

//Classe da cui parte l'esecuzione del programma, avviando il servizio

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.progetto.demo.url.Download;
import com.progetto.demo.url.Parsing;

@SpringBootApplication
public class DemoApplication {
	private static Vector<Aid> csv;
	

	public static void main(String[] args) throws IOException, ParseException, org.json.simple.parser.ParseException {
		
		
		Download.selectUrl();
		csv=Parsing.fileParsing();
		//SpringApplication.run(DemoApplication.class, args);
		
	}

}
