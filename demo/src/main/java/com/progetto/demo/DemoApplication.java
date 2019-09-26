package com.progetto.demo;

import java.io.IOException;
/**
 * Classe da cui parte l'esecuzione del programma, avviando il servizio
 */
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
