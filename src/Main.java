import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static String bruger;
    public static boolean taendt = true;
          

    public static void main(String[] args) throws InterruptedException {

        //opretter mappen
        File m = FileUtilKonkurrence.OpretKonkurrenceMappe();

        boolean formandExistens = false;
        boolean coachExistens = false;
        boolean kassererExistens = false;

        if (!formandExistens) {Formand formand = new Formand("toki", true, "03-07-2000","tokiarb@gmail.com","TheFormand", "123");}
        if (!coachExistens) {Coach coach = new Coach("ditlev", true, "03-07-2000","tokiarb@gmail.com","TheFormand", "123");}
        if (!kassererExistens) {Kasserer kasserer = new Kasserer("arzola", true, "03-07-2000","tokiarb@gmail.com","TheFormand", "123");}

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
 */
}

