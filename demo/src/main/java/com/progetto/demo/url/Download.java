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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

/**
 * Classe DownloadData effettua il download del file CSV, nel caso in cui il link ne contenga una

 */
public class Download {
	
	/**
	 * Effettua il download del link di default nel caso in cui si digita 2
	 */
	public static void DownloadUrlData() {

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
			        	System.out.println( "OK, Il link in uso contiene un csv, Effettuo il download!" );
			        	Download(urlD, "savedfile/gdp.csv");
			        	System.out.println("Di seguito altri elementi presenti nel link:\n");
			        }
			    }
			}
			System.out.println( "Download Effettuato" );
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Effettua il dowmload del link inserito (caso in cui si digita 1)
	 * @param selectedUrl URL inserito
	 */
	public static void DownloadUrlData(String selectedUrl) {

		String url = selectedUrl;
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
			        	System.out.println( "OK, Il link in uso contiene un csv, Effettuo il download!" );
			        	Download(urlD, "savedfile/gdp.csv");
			        	System.out.println("Di seguito altri elementi presenti nel link:\n");
			        }
			    }
			}
			System.out.println( "Download Effettuato" );
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Scarica il CSV e lo salva chiamandolo fileName
	 * @param url CSV da scaricare
	 * @param fileName Destinazione e nome del file scaricato
	 * @throws Exception Controlla è se possibile aprire un flusso di input dall'URL
	 */
	public static void Download(String url, String fileName) throws Exception {
	    try (InputStream in = URI.create(url).toURL().openStream()) {
	        Files.copy(in, Paths.get(fileName),StandardCopyOption.REPLACE_EXISTING);
	    }
	}
}