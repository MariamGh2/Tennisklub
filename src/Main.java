import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static String bruger;
    public static boolean taendt = true;

    public static void main(String[] args) {
        System.out.println("hej");

        //opretter mappen
        File m = FileUtilKonkurrence.OpretKonkurrenceMappe();




    public static void main(String[] args) throws InterruptedException {



        boolean formandExistens = false;
        boolean coachExistens = false;
        boolean kassererExistens = false;

        Formand formand = new Formand("toki", Medlemsnummer.hentNytMedlemsnummer(), true, "03-07-2000","tokiarb@gmail.com","TheFormand", "123");
        if (!formandExistens) {Formand ormand = new Formand("toki", Medlemsnummer.hentNytMedlemsnummer(), true, "03-07-2000","tokiarb@gmail.com","TheFormand", "123");}
        if (!coachExistens) {Coach coach = new Coach("ditlev", Medlemsnummer.hentNytMedlemsnummer(), true, "03-07-2000","tokiarb@gmail.com","TheFormand", "123");}
        if (!kassererExistens) {Kasserer kasserer = new Kasserer("arzola", Medlemsnummer.hentNytMedlemsnummer(), true, "03-07-2000","tokiarb@gmail.com","TheFormand", "123");}

        formand.opretMedlem();
        while (taendt) {

            //Login.login();

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

