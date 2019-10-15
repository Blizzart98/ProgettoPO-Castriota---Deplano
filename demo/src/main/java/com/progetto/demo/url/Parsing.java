package com.progetto.demo.url;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import com.progetto.demo.model.*;

public class Parsing {
	
	/**
	 * Funzione che effettua una lettura del csv e salva i dati all'interno di classi opportunamente strutturate.
	 * Vengono utilizzati i caratteri ";" e "," per riconoscere la separazione tra i valori di due attributi diversi.
	 * Tramite la variabile "y" solo per la prima riga vengono memorizzati i nomi degli attributi, tale variabile assumerà 
	 * poi il valore 2000, corrispondente al valore del primo anno dei rilevamenti.
	 * La funzione restituisce un vettore di oggetti di classe Aid a partire da un file csv.
	 * @author Piero Castriota, Lorenzo Deplano
	 */
	
	final static String DELIMITER = ",|\\;"; //delimitatori che andremo a cercare nel CSV
	
	
	
	public static Vector<Aid> fileParsing() throws FileNotFoundException, IOException{
		
		Vector<Aid> tab= new Vector<Aid>(); //la tabella sarà un vettore di oggetti di classe Aid
		
		try(BufferedReader br = new BufferedReader(new FileReader("file/data.csv"))){
			String line;
			int y=0;
			while((line = br.readLine()) != null) {
				String[] campi = line.split(DELIMITER);
				if(y!=0) { //condizione necessaria per riconoscere quali sono  nostri campi, y sarà 0 solo la prima volta, poi memorizzerà l'anno iniziale	
					tab.add(new Aid(campi[0].charAt(0), campi[1],campi[2], campi[3], addYA(campi,y) ));
				}
				else {
				
					
					campi[4]=campi[4].trim(); //rimuove gli spazi
					y=Integer.parseInt(campi[4]);
					
				}
		}
			br.close();
		}catch(IOException i) {
			i.printStackTrace();
		}
		return tab;
	}
	
	
	/**
	 * Funzione che prende la linea di aiuti, e crea un vettore di oggetti di tipo YA.
	 * @param campi, valori presi in input dal csv.
	 * @param year, anno iniziale
	 * @return vettore di oggetti di tipo YA.
	 */
	private static Vector<YA> addYA(String[] campi,int year) {
		Vector<YA> aidLine = new Vector<YA>();
		for(int i=4;i<campi.length;i++) {
		aidLine.add(new YA((year++), Double.parseDouble(campi[i].trim())));
		}
		return aidLine;
	}
}



