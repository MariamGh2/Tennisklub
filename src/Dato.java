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

    public void ligMedDato(){}

    public static DateTimeFormatter Dato = DateTimeFormatter.ofPattern("dd-MM");      //Metode som skriver datoen med dato, måned og år


    public static LocalDate getDato(){        //Henter dagens dato
        return LocalDate.now();
    }

    public static LocalDate fristDato() {
        LocalDate dato = LocalDate.of(LocalDate.now().getYear(), 2, 1);
        return dato;
    }

    public static boolean betalingsDato(){         //Boolean bliver true hvis dagens dato er d.1 januar og false hvis det ikke er
        LocalDate iDag = LocalDate.now();

        return (iDag.getDayOfMonth() == 1 && iDag.getMonthValue() == 1);
    }

    public static boolean skalHaveRykker(LocalDate sidsteBetalingDato) {        //Metode der tjekker om medlem skal have rykker


        LocalDate betalingsdato = LocalDate.of(LocalDate.now().getYear(), 1, 1);     //Betalingsdato i nuværende år

        LocalDate deadline = LocalDate.of(LocalDate.now().getYear(), 2, 1);   //Betalingsfrist: 1. februar på nuværende år.


        if (sidsteBetalingDato == null) {       // Hvis medlem aldrig har betalt (dato = null), fx ved nyt medlem
        return LocalDate.now().isAfter(deadline);
        }

        if (!sidsteBetalingDato.isBefore(deadline)) {      //Hvis sidste betaling var efter betalingsdato og før rykkerfristen, så ingen rykker
        return false;
        }

        return LocalDate.now().isAfter(deadline);      //Hvis sidste betaling var før betalingsdato og dagens dato er over rykkerfristen, så rykker
    }
}


