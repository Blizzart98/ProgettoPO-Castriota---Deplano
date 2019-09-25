
import java.io.IOException;

/**
 * Classe da cui parte l'esecuzione del programma, avviando il servizio
 */
@SpringBootApplication
public class Inizio {

	/**
	 * Funzione che inizializza l'applicazione e la fa partire. Prima di avviare l'applicazione Spring Boot verrà scaricato
	 * il file CSV dal JSON fornito tramite le specifiche del progetto, nel caso in cui questo non sia presente
	 * @param args Eventuali argomenti passati tramite cli
	 */
	public static void main(String[] args) throws IOException, ParseException{
		
		SpringApplication.run(Inizio.class, args);
		Download classe = new Download();
	}

}