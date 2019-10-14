package com.progetto.demo.url;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import com.progetto.demo.model.*;
/**
 * Classe che effettua il parsing del file metaData.csv, e restituisce un vettore di oggetti
 * di tipo MetaData.
 * @author castr
 *
 */
public class ParseMetadata {
	
	final static String DELIMITER = ";"; 

	
	public static Vector<Metadata> fileParsing() throws FileNotFoundException, IOException{
		
		Vector<Metadata> metaTab= new Vector<Metadata>(); //la tabella sarà un vettore di oggetti di classe Metadata
		
		try(BufferedReader br = new BufferedReader(new FileReader("file/metaData.csv"))){
			String line;
			
			while((line = br.readLine()) != null) {
				String[] campi = line.split(DELIMITER);
			
					
					metaTab.add(new Metadata(campi[0], campi[1],campi[2]));

				
			}
			br.close();
		}catch(IOException i) {
			i.printStackTrace();
		}
		return metaTab;
	}
	
}
	
	



