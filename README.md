# Progetto Programmazione ad Oggetti (Castriota - Deplano)

Progetto per l'esame di programmazione ad oggetti realizzato dagli studenti Piero Castriota e Lorenzo Deplano, anno accademico 2019/2020.
## Descrizione
Il programma acquisisce un dataset in formato .csv, lo converte in un vettore di oggetti e restituisce gli oggetti in formato JSon.
In fase di restituzione i dati possono essere filtrati oppure possono essere elaborate statistiche su i dati stessi.
L'utente acquisisce i dati dal programma in base a opportune richieste GET passate alle Web API che gestiscono i filtri e le statistiche.
## Il dataset
Il dataset utilizzato contiene una lista di aiuti che ciascuno stato dell'Unione Europea ha elargito dall'anno 2000 all'anno 2017 in diversi ambiti, espressi in milioni di euro.
Il file in formato .csv contentente l'intero dataset viene scaricato dal programma e inserito nella cartella *file*
All'interno del programma ciascun aiuto è contenuto in un'istanza della classe "Aid". 
Ciascun Aid ha i seguenti attributi d'interesse:
 - **freq**: frequenza con cui è stato rilevato il dato (es. A=Annuale)
 - **geo**: Stato dell'unione a cui si riferisce l'aiuto
 - **unit**: Unità di misura dei valori numerici
 - **obj**: Codice che indica lo scopo per cui è stato stanziato l'aiuto
 - **aidList**: Vettore di coppie anno-valore che indicano l'entità di ciascun aiuto in quel dato anno

Di seguito un esempio di aiuto in formato JSon:

```bash
{
"freq":"A",
"geo":"IT",
"unit":"MEUR_KP_PRE",
"obj":"OBJ01",
"aidList":
[{
"year":2000,"value":1314.5
},
{
"year":2001,"value":987.8
},
{
"year":2002,"value":2198.8
},
{
"year":2003,"value":1505.6
},
{
"year":2004,"value":1153.9
},
{
"year":2005,"value":1033.3
},
{
"year":2006,"value":1217.5
},
{
"year":2007,"value":742.3
},
{
"year":2008,"value":868.5
},
{
"year":2009,"value":968.4
},
{
"year":2010,"value":1076.8
},
{
"year":2011,"value":896.2
},
{
"year":2012,"value":864.0
},
{
"year":2013,"value":536.3
},
{
"year":2014,"value":787.7
},
{
"year":2015,"value":508.9
},
{
"year":2016,"value":447.2
},
{
"year":2017,"value":509.3
}]
}
```
## Controller e Web API
Nel programma sono presenti 2 controller: **FilterController** per la gestione dei filtri e **StatsController** per la restituzione delle statistiche.
Ciascun controller dopo aver avviato il programma si interfaccia con l'utente tramite richieste di tipo "GET" alle WebAPI accessibili all'indirizzo
```bash
http://localhost:8080/aid
```
Per acquisire **l'intero dataset** senza filtri applicati si accede all'endpoint
```bash
GET /data
```
Per acquisire i **metadati** si accede all'endpoint
```bash
GET /metadata
```
## Filtri
Anzichè l'intero dataset è possibile acquisire i dati filtrati per attributo da specificare nella richiesta all'endpoint */data* usando il parametro *filter* secondo il seguente formato:
```bash
GET /data?filter="attributo1:valore1:opLogico:attributo2:valore2:opLogico2:..."
```
I termini *"**attributo**"* dovranno contenere uno dei campi della classe *Aid* precedentemente indicati. I termini *"**valore**"* dovranno contenere i valori relativi al corrispondente attributo che vogliono essere acquisiti. Non è possibile in questa richiesta specificare come attributi le "aidList" e quindi applicare filtri ai dati numerici di ogni anno, che saranno gestiti con un filtro dedicato.
Tra un attributo e l'altro è possibile specificare un operatore logico. Sono stati gestiti ***AND*** e ***OR*** (vengono riconosciuti solo se scritti in questo modo).

Per filtrare i dati numerici è necessario accedere all'endpoint *YA* secondo il formato:
```bash
GET /data/YA/"opCond":"numero"?filter="attributo1:valore1:opLogico:attributo2:valore2:opLogico2:..."
```
In questo modo vengono eseguiti confronti secondo l'operatore condizionale *"opCond"* tra i valori numerici contenuti nelle aidList di ogni oggetto e il *"numero"* passato con la richiesta e vengono restituiti solo gli anni i cui corrispettivi valori rispettano la condizione del confronto.
Sono stati gestiti gli operatori condizionali **<**,**<=**,**>**,**>=**,**==**
Agli oggetti con i valori numerici filtrati è possibile applicare anche i sopra citati filtri per attributo attraverso il parametro *filter*.

In caso di inserimento di attributi non corretti la richiesta non restituisce nessun dato e vengono stampati messaggi di errore sulla console di Ecplise.
In caso si inseriscano operatori condizionali non gestiti non vengono applicati filtri numerici.
## Statistiche
Il programma è in grado di elaborare statistiche sui dati filtrati accedendo al seguente endpoint secondo il formato:
```bash
GET /stats?filter="attributo1:valore1:opLogico:attributo2:valore:opLogico2:..."
```
E' necessario specificare almeno un attributo nel parametro *filter* e vengono restituite statistiche considerando tutti gli oggetti che soddisfano il filtro indicato.
Le statistiche che vengono restituite fanno riferimento ai valori numerici presenti nelle *aidList* di ciascun oggetto filtrato. Sono state gestitei:

 - **Avg**: media dei valori numerici
 - **Max**: valore massimo
 - **Min**: valore minimo (i valori 0 non vengono considerati)
 - **DevStd**: deviazione standard
 - **Sum**: somma dei valori (gli oggetti con obj = TOTAL non vengono considerati)
 - **Count**: conteggio dei valori numerici

Gli attributi più significativi da utilizzare nel filtro della richiesta delle statistiche sono *geo* e *obj* che rendono l'idea di quanto un singolo stato abbia contribuito complessivamente per i vari obiettivi, oppure l'entità degli aiuti complessivi che sono stati elargiti per un certo obiettivo.

In caso di attributi non corretti nella richiesta o di parametro filter non presente, vengono restituiti valori nulli.

## Modellazione dei dati  - UML
Di seguito vengono riportati i diagrammi UML che modellano le classi del programma.
[Diagramma delle classi](https://i.ibb.co/1MgRGXW/com-progetto-demo.png)
[Diagramma dei casi d'uso](https://i.ibb.co/rFVWTLD/use-case-diag.png)
[Diagramma delle sequenze](https://i.ibb.co/fFGXGgY/sequence-diag.png)
## Richieste per output di esempio
```bash
Dataset
GET http://localhost:8080/aid/data

Metadata
GET http://localhost:8080/aid/metadata

Filtri di esempio
GET http://localhost:8080/aid/data?filter=geo:IT:AND:obj:OBJ01
GET http://localhost:8080/aid/data?filter=geo:IT:OR:geo:UK:OR:obj:OBJ01

Statistiche di esempio
GET http://localhost:8080/aid/stats?filter=geo:IT
```
## Librerie
Per lo sviluppo del progetto è stata usata la libreria 

 - **json-simple** per convertire il file json contenente il link per il download del dataset
 
## Autori
Castriota Piero - Matricola s1084102
Deplano Lorenzo - Matricola s1085792
