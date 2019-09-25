import java.io.BufferedReader;


public class Download {
	
	public Download() throws IOException, ParseException{
		File data = new File("data.csv");
		String input;
		String dati="";
		String url="http://data.europa.eu/euodp/data/api/3/action/package_show?id=Ju4o7srAEdHz4OMPwCWiDQ";
		URL connessione = new URL(url);
		System.setProperty("http.agent","Chrome");
		URLConnection web = connessione.openConnection();
		BufferedReader s = new BufferedReader(new InputStreamReader(web.getInputStream()));
		
		//sto copiando il file
		while ((input = s.readLine()) != null) {
			dati+=input;
		}
		
		s.close();
		JSONObject ogg1 = (JSONObject) JSONValue.parseWiththException(dati);
		JSONObject ogg2 = (JSONObject) ogg1.get("result");
		JSONObject ogg3 = (JSONArray) ogg2.get("resources");
		
		for(Object test:ogg3)
		{
			if(test instanceof JSONObject)
			{
				JSONObject Ob2 = (JSONObject) test;
				String formato = (String)Ob2.get("format");
				URL url1 = new URL ((String) Ob2.get("url"));
				if(formato.equals("csv"))
				{
					FileUtils.copyURLToFile(url1,data);
					break;
				}
					
				}
			}
		}

		
		
		
		
}
}