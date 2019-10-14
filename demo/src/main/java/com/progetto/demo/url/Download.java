package com.progetto.demo.url;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

public class Download{
	
	/**
	  * Viene scansionato un file di tipo JSON passato tramite url alla ricerca di un link utile 
	  * per il download di un file di tipo .csv.
	  * Quando e se viene trovato comincia un download del file tramite chiamata ad un'altra funzione.
	  */
	 

	public static void DownloadData() {

		String url = "http://data.europa.eu/euodp/data/api/3/action/package_show?id=Ju4o7srAEdHz4OMPwCWiDQ";
		try {
			URLConnection openConnection = new URL(url).openConnection();
			openConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
			InputStream in = openConnection.getInputStream();
			
			 String data = "";
			 String line = "";
			 try {
			   InputStreamReader inR = new InputStreamReader( in );
			   BufferedReader buf = new BufferedReader( inR );
			  
			   while ( ( line = buf.readLine() ) != null ) {
				   data+= line;
				   System.out.println( line );
			   }
			 } finally {
			   in.close();
			 }
			JSONObject obj = (JSONObject) JSONValue.parseWithException(data); 
			JSONObject objI = (JSONObject) (obj.get("result"));
			JSONArray objA = (JSONArray) (objI.get("resources"));
			
			for(Object o: objA){
			    if ( o instanceof JSONObject ) {
			        JSONObject o1 = (JSONObject)o; 
			        String format = (String)o1.get("format");
			        String urlD = (String)o1.get("url");
			        System.out.println(format + " | " + urlD);
			        if(format.equals("http://publications.europa.eu/resource/authority/file-type/CSV")) {
			        	System.out.println( "CSV found. Downloading..." );
			        	DownloadUrl(urlD, "file/data.csv");
			        	System.out.println("Other files:\n");
			        }
			    }
			}
			System.out.println( "Download Completed." );
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Funzione che effettua il download del file.
	 * Nel caso in cui sia già presente un file nel percorso specificato esso viene sostituito.
	 */

	public static void DownloadUrl(String url, String fileName) throws Exception {
	    try (InputStream in = URI.create(url).toURL().openStream()) {
	        Files.copy(in, Paths.get(fileName),StandardCopyOption.REPLACE_EXISTING);
	    }
	}
}