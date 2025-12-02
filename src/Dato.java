import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
public class Dato {

    public Dato(){} //Default Constructor

    public void ligMedDato(){}

    //Metode som skriver datoen med dato, måned og år
    public static DateTimeFormatter Dato = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    //Henter dagen dato
    public static String getDato(){
        return LocalDate.now().format(Dato);
    }

    //Boolean bliver true hvis dagens dato er d.1 januar og false hvis det ikke er
    public static boolean betalingsDato(){
        LocalDate iDag = LocalDate.now();

        return (iDag.getDayOfMonth() == 1 && iDag.getMonthValue() == 1);
    }

    //Metode der tjekker om medlem skal have rykker
    public static boolean skalHaveRykker(LocalDate sidsteBetalingDato) {

    //betalingsdato i nuværende år
    LocalDate betalingsdato = LocalDate.of(LocalDate.now().getYear(), 1, 1);

    // Betalingsfrist: 1. februar på nuværende år.
    LocalDate deadline = LocalDate.of(LocalDate.now().getYear(), 2, 1);

    // Hvis medlem aldrig har betalt (dato = null), fx ved nyt medlem
    if (sidsteBetalingDato == null) {
        return LocalDate.now().isAfter(deadline);
    }

    // Hvis sidste betaling var efter betalingsdato og før rykkerfristen, så ingen rykker
    if (!sidsteBetalingDato.isBefore(deadline)) {
        return false;
    }

    // Hvis sidste betaling var før betalingsdato og dagensdato er over rykkerfristen, så rykker
    return LocalDate.now().isAfter(deadline);
}


}


