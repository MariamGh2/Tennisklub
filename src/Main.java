import java.io.File;
import java.util.Scanner;

public class Main {

    public static String bruger;
    public static boolean taendt = true;

    public static void main(String[] args) {

        Formand.opretMedlem();


        System.out.println("hej");

        //opretter mappen
        File m = FileUtilKonkurrence.OpretKonkurrenceMappe();


        while (taendt) {

            //login();

            if (bruger == "coach") {
                //Coach.menu();
            } else if (bruger == "formand") {
                //Formand.menu();
            } else if (bruger == "kasserer") {
                //Kasserer.menu();
            }
        }


    }

/*
    // Kontingent
    public static void main(String[] args) {
        {

            // Opretter et nyt medlem med navn "medlem", alder 22 og aktivt medlemskab
            Medlem m = new Medlem("medlem", 22, true);

            // Beregner et kontingent-objekt, som kan beregne medlemskontingent
            Kontingent k = new Kontingent();

            // Beregner kontingentet for medlemmet "m" og gemmer resultatet i variablen pris
            double pris = k.beregnKontingent(m);
            System.out.println("Kontingent: " + pris + " kr");
        }


        //Login Funktionen
        protected static void login() {
            Scanner input = new Scanner(System.in);

            System.out.println("=== LOGIN =============");
            System.out.println("Brugernavn:");
            String brugernavn = input.next();

            System.out.println("Brugernavn: " + brugernavn);
            System.out.println("Password:");

            if (Coach.getBrugernavn().equals(brugernavn)) {
                String password = input.next();

                if (Coach.getPassword().equals(password)) {
                    bruger = "coach";
                    System.out.println("Du er nu logget ind som coach");
                }
            }
        }


 */

    }

