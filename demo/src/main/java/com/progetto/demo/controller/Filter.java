package com.progetto.demo.controller;

import java.util.Vector;

import com.progetto.demo.model.Aid;
import com.progetto.demo.model.YA;


/**
 * Classe che contiene attributi e metodi per filtrare i dati in base alla richiesta
 * passata come stringa alle WebAPI
 * @author Lorenzo Deplano
 * 
 */
public class Filter {

	
	private Vector<Aid> input;
	private Vector<String> attributeNames;
	private Vector<String> values;
	private Vector<String> operators;
	private Vector<String> logical;
	
	/**
	 * Costruttore che prevede il passaggio degli attributi.
	 * Gli attributi sono vettori che conterranno le stringhe passate dalla richiesta di filtro e 
	 * i dati da filtrare
	 * @param input, vettore di oggetti Aid che serve a contenere i dati da filtrare presi come input
	 * @param attributeNames, vettore che contiene gli attributi della classe Aid su cui si applicherà il filtro
	 * @param values, vettore che contiene i valori che gli oggetti restituiti in output dovranno contenere
	 * @param operators, vettore che contiene gli operatori condizionali per il confronto tra numeri
	 * @param logical, vettore che contiene gli operatori logici da usare per combinare l'applicazione di più filtri consecutivi
	 */
	public Filter(Vector<Aid> input, Vector<String> attributeNames,
			Vector<String> values, Vector<String> operators, Vector<String> logical) {
		super();
		this.input = input;

		this.attributeNames = attributeNames;
		this.values = values;
		this.operators = operators;
		this.logical = logical;
	}
	
	/**
	 * Costruttore di default, inizializza i vettori
	 */
	public Filter()
	{
		this.input =new Vector<Aid>();

		this.attributeNames = new Vector<String>();
		this.values = new Vector<String>();
		this.operators = new Vector<String>();
		this.logical = new Vector<String>();
	}
	
	public Vector<Aid> getInput() {
		return input;
	}

	public void setInput(Vector<Aid> input) {
		this.input = input;
	}

	public Vector<String> getAttributeNames() {
		return attributeNames;
	}

	public void setAttributeNames(Vector<String> attributeNames) {
		this.attributeNames = attributeNames;
	}

	public Vector<String> getValues() {
		return values;
	}

	public void setValues(Vector<String> values) {
		this.values = values;
	}

	public Vector<String> getOperators() {
		return operators;
	}

	public void setOperators(Vector<String> operators) {
		this.operators = operators;
	}

	public Vector<String> getLogical() {
		return logical;
	}

	public void setLogical(Vector<String> logical) {
		this.logical = logical;
	}

	/**
	 * Metodo che estrae l'attributo da filtrare dal vettore attributeNames, confronta il valore 
	 * corrispondente a quell'attributo di tutti gli oggetti nel dataset in ingresso con il valore
	 * estratto dal vettore values. Per come sono riempiti i vettori c'è una corrispondenza tra 
	 * l'attributo in prima posizione e il valore in prima posizione contenuti nel vettore.
	 * @param source, vettore di oggetti Aid su cui effettuare il filtro per attributo
	 * @return vettore di oggetti Aid che contiene solo gli oggetti risultato del filtro
	 */
	private Vector<Aid> filterAttributes(Vector<Aid> source)
	{
		String attribute= attributeNames.get(0);
		String value=values.get(0);
		
		Vector<Aid> appoggio=new Vector<Aid>();
		// for each object in tab (lista completa degli oggetti)
		for(int i=0; i<source.size();i++)
		{
			//switches different attributes based on "filter" parameter
			switch(attribute)
			{
				case "freq":
					if(source.get(i).getFreq()==(value.charAt(0)))
						appoggio.add(source.get(i));
					break;
				case "geo":
					if(source.get(i).getGeo().equals(value))
						appoggio.add(source.get(i));
					break;
				case "unit":
					if(source.get(i).getUnit().equals(value))
						appoggio.add(source.get(i));
					break;
				case "obj":
					if(source.get(i).getObj().equals(value))
						appoggio.add(source.get(i));
					break;	
					
				default: 
					System.out.println("Attributo non corretto");
					break;
			}
		}
		
		return appoggio;
	}
	
	/**
	 * Metodo ricorsivo che combina le chiamate al metodo precedente in base agli operatori logici presenti
	 * nel vettore logical. Se il vettore logical è vuoto (solo un attributo da filtrare) effettua
	 * un solo filtro (chiamata a filterAttributes), altrimenti controlla di che operatore si tratta.
	 * Nel caso di AND applica i filtri nuovamente sul dataset già filtrato alla prima chiamata (local), 
	 * nel caso di OR applica i filtri nuovamente sul dataset di partenza (source), aggiunge tutti i nuovi
	 * elementi filtrati, controlla eventuali duplicati.
	 * @param source, dataset (vettore di oggetti Aid) su cui effettuare i filtri 
	 * @return dataset (vettore di oggetti Aid) contenente solo gli oggetti selezionati dopo i filtri
	 */
	public Vector<Aid> applyFilters(Vector<Aid> source)
	{	
		//filtra il singolo attributo
		Vector<Aid> local=filterAttributes(source);
		
		
		if(logical.isEmpty()==false)
		{
		
			//se trova un operatore AND
			if(logical.get(0).contentEquals("AND"))
			{	
				//rimuove valori, attributi e operatori già confrontati
				logical.removeElementAt(0);
				attributeNames.removeElementAt(0);
				values.removeElementAt(0);
				//deve applicare ulteriori filtri a quelli già applicati, la source sarà "local"
				return applyFilters(local);
			}
			
			else if(logical.get(0).contentEquals("OR")) //trovo un operatore OR
			{ 	
				//rimuoeo valori, attributi e operatori già confrontati
				logical.removeElementAt(0); 
				attributeNames.removeElementAt(0);
				values.removeElementAt(0);
				//deve applicare altri filtri ai dati di partenza, la source rimane la source iniziale
				Vector<Aid> temp=applyFilters(source);
				
				local.addAll(temp);//aggiunge i valori filtrati dalla chiamata appena conclusa
				checkDuplicates(local); //rimuove eventuali oggetti duplicati
				return local;
			}
			else //nel caso l'operatore inserito non fosse corretto ritorna un vettore vuoto e stampa un messaggio su console
			{
				System.out.println("Operatore logico non corretto, i filtri ottenuti potrebbero essere incompleti");
				return null;
			}
		}
		
		return local;//se non trova operatori logici restituisce il risultato dell'unico filtro applicato
	}

	/**
	 * Metodo che estrae un singolo Aid con un certo stato e un certo obiettivo di riferimento
	 * dal dataset passato come sorgente dei dati
	 * @param source, dataset da cui estrarre il singolo Aid
	 * @param stato, stato corrispondente all'Aid da estrarre
	 * @param obiettivo, obiettivo a cui si riferisce l'Aid da estrarre
	 * @return oggetto Aid estratto
	 */
	public Aid filterSingle(Vector<Aid> source, String stato, String obiettivo)
	{
		Aid single= new Aid();
		
		//genera una chiamata fittizia ai filtri
		String call=("geo:"+ stato +":AND:obj:" + obiettivo);
		
		//inserisce nei rispettivi vettori le stringhe della chiamata
		stringSplitter(call,":",attributeNames,values,logical);
		
		//filtra estrae l'unico elemento restituito
		single=applyFilters(source).get(0);
		
		return single;
	}
	
	/**
	 * Metodo che restituisce un vettore di oggetti YA selezionandoli in base al confronto
	 * dei singoli elementi del vettore in ingresso con l'operatore condizionale e il valore 
	 * passati come parametri.
	 * @param source, vettore di oggetti YA su cui effettuare la selezione
	 * @param operator, operatore condizionale
	 * @param value, valore su cui effettuare il confronto
	 * @return vettore di oggetti YA contenente solo i selezionati
	 */
	private Vector<YA> selectYears(Vector<YA> source, String operator, Double value)
	{
		Vector<YA> selezionati = new Vector<YA>();
		
		for(YA anno:source)//per ogni elemento nel vettore
		{
			if(check(anno.getValue(),operator,value))//espressione vera solo se il confronto è rispettato
				{
					//System.out.println("Ho selezionato un anno  " + anno.getYear()); //debug
					selezionati.add(anno); //aggiunge ai selezionati
				}
		}
		
		return selezionati;
	}
	
	/**
	 * Metodo che scandisce il vettore di input e applica confronto numerico a tutti gli elementi 
	 * nelle aidList degli oggetti Aid, secondo l'operatore e il valore passati come parmetri.
	 * Richiede che gli oggetti da elaborare vengano inseriti nell'input dell'oggetto Filter.
	 * @param operatore, operatore condizionale da considerare nel confronto
	 * @param valore, valore da considerare nel confronto
	 * @return vettore di oggetti Aid che contengono nella aidList solo i valori che hanno superato il confronto
	 */
	public Vector<Aid> filterYears(String operatore,Double valore)
	{
		Vector<YA> selected;
		Aid temp = new Aid();
		Vector<Aid> local = new Vector<Aid>();
		
		for(Aid oggetto:input)
		{
			selected=selectYears(oggetto.getAidList(),operatore,valore);
			temp=oggetto.copy();
			temp.setAidList(selected);
			local.add(temp);
			
		}
		return local;
	}
	
	/**
	 * Metodo che riconosce il tipo dell'oggetto passato come parametro (numeri o stringhe) e effettua 
	 * un confronto secondo l'operatore e il valore di riferimento passati come parametri.
	 * Sono gestiti per numeri gli operatori maggiore/maggiore o uguale, minore/minore o uguale, uguale;
	 * per le stringhe l'uguaglianza
	 * @param value, numero o stringa su cui effettuare in confronto
	 * @param operator, operatore condizionale
	 * @param th, valore di riferimento su cui effettuare il confronto
	 * @return true se il confronto è verificato, false altrimenti
	 */
	private boolean check(Object value, String operator, Object th) {
			
			if (th instanceof Number && value instanceof Number) {	
				Double thC = ((Number)th).doubleValue();
				Double valuec = ((Number)value).doubleValue();
				
				if (operator.equals("=="))
					return value.equals(th);
				else if (operator.equals(">"))
					return valuec > thC;
				else if (operator.equals("<"))
					return valuec < thC;
				else if (operator.equals("<="))
					return valuec<=thC;
				else if (operator.equals(">="))
					return valuec>=thC;
			}
			
			else if(th instanceof String && value instanceof String)
				return value.equals(th);
			
			return false;		
		}
	

	/**
	 * Metodo che separa le sottostringhe contenute tra un carattere considerato separatore in una stringa
	 * passata come parametro. Le sottostringhe vengono distribuite nei vettori di stringhe passati come 
	 * parametri. Il numero di vettori in ingresso è variabile.
	 * Se il numero di sottostringhe eccede il numero di vettori, le strighe successive vengono aggiunte ordinatamente
	 * ai vettori ricominciando dal primo
	 * @param source, stringa sorgente da splittare
	 * @param delimiter, carattere da considerare come separatore
	 * @param singoli, (varargs) vettori di stringhe in cui vengono immagazzinate le sottostringhe splittate
	 */
	@SafeVarargs
	public static void stringSplitter(String source,String delimiter, Vector<String>...singoli )
	{
		int n = singoli.length;//numero di varArgs che passo al metodo
		String[] splitter = source.split(delimiter);//splitta la stringa in ingresso

		int maxSplit=splitter.length/n; //calcola il numero massimo di elementi per ogni vettore
		
		if(splitter.length%n >0)//tiene conto di divisioni non intere
			maxSplit++;
		
		//inserisce le stringhe splittate nei rispettivi vettori passati come parametri singoli
		for(int j=0;j<maxSplit;j++)
			for(int i=0;i<n;i++)
			{
				if(i+(n*j)<splitter.length)
				singoli[i].add(splitter[i+(n*j)]);
			}
			
	}
	
	/**
	 * Metodo che confronta coppie di oggetti Aid in un vettore e elimina eventuali duplicati
	 * @param vector, vettore che viene scandito
	 */
	public void checkDuplicates(Vector<Aid> vector)
	{
		for(int i=0;i<vector.size();i++)
			for(int j=i+1;j<vector.size();j++)
				if(vector.get(i).equals(vector.get(j)))
				{
					vector.removeElementAt(j);
				}
	}
	
}
