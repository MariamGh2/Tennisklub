import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;

/*
Klassen bruges til:
    - At finde dagens dato
    - At beregne hvornår der skal betales kontingent
    - At fastsætte betalingsfristen for kontingent
    - At håndtere rykkere, hvis medlemmet ikke har betalt inden fristen
*/

public class Dato {

    public Dato(){} //Default Constructor

    public static DateTimeFormatter Dato = DateTimeFormatter.ofPattern("dd-MM");      //Metode som skriver datoen med dato, måned og år


    public static LocalDate getDato(){        //Henter dagens dato
        return LocalDate.now();
    }

    public static LocalDate fristDato() {
        LocalDate dato = LocalDate.of(LocalDate.now().getYear(), 2, 1);
        return dato;
    }
}


