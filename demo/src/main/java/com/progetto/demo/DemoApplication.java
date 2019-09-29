package com.progetto.demo;

import java.io.IOException;
import java.text.ParseException;

/**
 * Classe da cui parte l'esecuzione del programma, avviando il servizio
 */
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.progetto.demo.url.Download;
import com.progetto.demo.url.Parsing;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) throws IOException, ParseException, org.json.simple.parser.ParseException {
		SpringApplication.run(DemoApplication.class, args);
		Parsing.selectUrl();
	}

}
