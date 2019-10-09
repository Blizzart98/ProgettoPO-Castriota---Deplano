package com.progetto.demo.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.progetto.demo.DemoApplication;
import com.progetto.demo.model.Aid;
import com.progetto.demo.model.Stats;


@RestController 

public class StatsController {
	/*Stats results = new Stats();

	private static Vector<Aid> dataTab;
	private static Vector<Aid> filtered;
	private static Vector<Metadata> metaTab;
	
	@GetMapping("/aid/stats") 
	public Stats getFiltered(@RequestParam(value="filter",defaultValue="", required=false) String filter) throws FileNotFoundException, IOException,NullPointerException
	{
		//format ?filter=attributo:operatore:valore:opLogico:attributo2:operatore2:valore2 
		dataTab=DemoApplication.csv;
		filtered = new Vector<Aid>();
		
		if(filter.equals("")==false)
		{
			//Converto la stringa di input della get in stringhe utilizzabili per filtrare i dati
			Vector<String> attributi=new Vector<String>();
			Vector<String> operatori=new Vector<String>();
			Vector<String> valori=new Vector<String>();
			Vector<String> logicalOps=new Vector<String>();
			
			String[] singoli = filter.split(":");
	
			int maxSplit=singoli.length/4;
			
			if(singoli.length%4 >0)
				maxSplit++;
				
			for(int i=0;i<maxSplit;i++)
			{
				attributi.add(singoli[0 +(4*i)]);
				operatori.add(singoli[1 +(4*i)]);
				valori.add(singoli[2 +(4*i)]);
				if(singoli.length>(3+4*i))
					logicalOps.add(singoli[3 +(4*i)]);
			}
			
			
			filtered=filterUtils.filterWithOp(dataTab, attributi, valori, logicalOps);
			
			results.setMax(calcMax(filtered));
			results.setMin(calcMin(filtered));
			results.setAvg(calcAvg(filtered));
			results.setDevStd(Math.rint(calcDevStd((filtered), results.getAvg())));
			results.setSum(calcSum(filtered));
			results.setCount(calcCount(filtered));
		}
		return results;
}*/
	Stats results = new Stats();
	private static Vector<Aid> dataTab;
	private static Vector<Aid> filtered=new Vector<Aid>();

	
	@GetMapping("/aid/stats") 
	public Stats getFiltered(@RequestParam(value="filter",defaultValue="", required=false) String filter) throws FileNotFoundException, IOException,NullPointerException
	{
		//format ?filter=attributo:valore:opLogico:attributo2:valore2 ecc.
		//format ?sfilter=codiceStato,codiceObj,operatore,numero
		dataTab=DemoApplication.csv;
		Vector<String> attributi=new Vector<String>();
		Vector<String> valori=new Vector<String>();
		Vector<String> logicalOps=new Vector<String>();
		
		if(filter.equals("")==false)
		{
			//Converto la stringa di input della get in stringhe utilizzabili per filtrare i dati
			filterUtils.stringSplitter(filter, ":", attributi,valori,logicalOps);
			//Restituisco i valori filtrati come da richiesta
			filtered=filterUtils.filterWithOp(dataTab, attributi, valori, logicalOps);
		
		}
		results.setMax(calcMax(filtered));
		results.setMin(calcMin(filtered));
		results.setAvg(Math.rint(calcAvg(filtered))/100);
		results.setDevStd(Math.rint(calcDevStd((filtered), results.getAvg()))/100);
		results.setSum(Math.rint(calcSum(filtered))/100);
		results.setCount(calcCount(filtered));
		
		return results;
	}
	
	public double calcMax(Vector<Aid> v) {
		
		double max=0.0;
		for(Aid oggetto : v)
		{
			if(oggetto.getObj().equals("TOTAL"))
				break;
			for(int i=0; i<oggetto.getAidList().size();i++) {
				if(oggetto.getAidList().get(i).getValue() > max)
				max=oggetto.getAidList().get(i).getValue();
			}
		}
		return max;

	}
	
	public double calcMin(Vector<Aid> v) {
		double min=10000000;
		for(Aid oggetto : v)
		{
			for(int i=0; i<oggetto.getAidList().size();i++) {
				if(oggetto.getAidList().get(i).getValue() < min && oggetto.getAidList().get(i).getValue()!=0 )
				min=oggetto.getAidList().get(i).getValue();
							}
		}
		return min;
	}
	
	public double calcAvg(Vector<Aid> v) {
		double avg=0;
		for(Aid oggetto : v)
		{
			
			for(int i=0; i<oggetto.getAidList().size();i++) {
				avg+=oggetto.getAidList().get(i).getValue();
				if(oggetto.getObj().equals("TOTAL"))
					avg/=2;
		}
	}
		avg=avg/18;
		return avg*100;
}
	
	
	public double calcDevStd(Vector<Aid> v, double m) {
		double dev=0;
		int j=0;
		for(Aid oggetto : v)
		{
			for(int i=0; i<oggetto.getAidList().size();i++) {
				dev+=Math.pow((oggetto.getAidList().get(i).getValue()-m),2);
				j++;
							}
		}
		return 100*dev/j;
}



public double calcSum(Vector<Aid> v) {
	double sum=0;
	for(Aid oggetto : v)
	{
		for(int i=0; i<oggetto.getAidList().size();i++) {
			sum+=oggetto.getAidList().get(i).getValue();
						}
	}
	return sum;
}

public int calcCount(Vector<Aid> v) {
	int j=0;
	for(Aid oggetto : v)
	{
		for(int i=0; i<oggetto.getAidList().size();i++) 
			j++;			
	}
	return j;
}

}