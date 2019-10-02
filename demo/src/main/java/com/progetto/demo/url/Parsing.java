package com.progetto.demo.url;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import com.progetto.demo.model.*;

public class Parsing {
	final static String DELIMITER = ",|\\;"; //delimitatori che andremo a cercare nel CSV
	private static String[] aux; //variabile di supporto per i campi
	
	/*
	 * Funzione che effettua il parsing del CSV e restituisce una tabella strutturata
	 */
	
	public static Vector<Aid> fileParsing() throws FileNotFoundException, IOException{
		
		Vector<Aid> tab= new Vector<Aid>(); //la tabella sarà un vettore di oggetti di classe Aid
		
		try(BufferedReader br = new BufferedReader(new FileReader("file/data.csv"))){
			String line;
			int y=0;
			while((line = br.readLine()) != null) {
				String[] campi = line.split(DELIMITER);
				if(y!=0) { //condizione necessaria per riconoscere quali sono  nostri campi, y sarà 0 solo la prima volta, poi memorizzerà l'anno iniziale
					
					tab.add(new Aid(campi[0].charAt(0), campi[1],campi[2], campi[3], addYA(campi,y) ));
					System.out.println("");
				}
				else {
				
					aux = campi;
					campi[4]=campi[4].trim(); //levo gli spazi
					y=Integer.parseInt(campi[4]);
					
				}
		}
			br.close();
		}catch(IOException i) {
			i.printStackTrace();
		}
		return tab;
	}
	
	
	//funzione che prende la linea di aiuti, e crea un vettore di oggetti di tipo YA.
	private static Vector<YA> addYA(String[] campi,int year) {
		Vector<YA> aidLine = new Vector<YA>();
		for(int i=4;i<campi.length;i++) {
		aidLine.add(new YA((year++), Double.parseDouble(campi[i].trim())));
		}
		return aidLine;
	}
}



