import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    /*
    Variabel der fortæller hvilken typer bruger der er logget ind
    */

    public static String bruger = "";


        /// LOGIN METODE
    public static void main(String[] args) throws InterruptedException {

        //opretter mappen
        //File m = FileUtilKonkurrence.OpretKonkurrenceMappe();

        Login.login();//login

        List<Object> personaleListe = new ArrayList<>();
        personaleListe = FileUtil.laesPersonaleFraFil();

        if ("coach".equals(bruger)) {     //Når login lykkedes, er Main.bruger sat til coach, formand eller kasserer
            System.out.println("Der er ingen funktion endnu");

        } else if ("formand".equals(bruger)) {
            for (Object o : personaleListe) {
                if (o instanceof Formand f) {
                    while (bruger == "formand") {
                        f.menu();
                    }
                }
            }
        } else if ("kasserer".equals(bruger)) {
            for (Object o : personaleListe) {
                if (o instanceof Kasserer k) {
                    while (bruger == "kasserer") {
                        //k.menu();
                        System.out.println("Ingen funktion endnu");
                    }
                }
            }
        }
    }
}



