package com.progetto.demo.url;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Parsing {
	/*
		 * Seleziona l'Url di default o quello inserito
		 * @throws IOException gestisce gli errori del flusso di lettura dell'url
		 */
		public static void selectUrl() throws IOException {
			Scanner input = new Scanner(System.in);
			BufferedReader urlinput = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Da dove vuoi scaricare il file? \n 1) Inserisci URL \n 2) URL di default" );
			int selector=input.nextInt();
			switch (selector) {
			case 1:
				System.out.println("inserisci l'url");
				String selurl=urlinput.readLine();
				Download.DownloadData(selurl);
				input.close();
				break;
			case 2:
				Download.DownloadData();
				input.close();
				break;
			}
		}

}
