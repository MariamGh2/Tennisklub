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

    // tjekker om datoen er den 1.januar, returnerer en boolean, hvor den viser true, hvis dagens dato er 1.januar og false, hvis det er en hvilken som helst dag
    public static boolean betalingsDato(){
        LocalDate iDag = LocalDate.now();

        return (iDag.getDayOfMonth() == 1 && iDag.getMonthValue() == 1);
    }

}

