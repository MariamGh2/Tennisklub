import java.io.File;
import java.util.Scanner;

public class Login {

    //Login Funktionen
   /* public static void login() throws InterruptedException {
        boolean loggetInt = false;
        while (loggetInt == false) {
            Scanner input = new Scanner(System.in);

            System.out.println("=== LOGIN =============");
            System.out.println("Brugernavn:");
            String brugernavn = input.next();

            System.out.println("Brugernavn: " + brugernavn);
            System.out.println("Password:");

            if (getBrugernavn().equals(brugernavn)) {
                String password = input.next();

                if (getPassword("coach").equals(password)) {
                    Main.bruger = "coach";
                    System.out.println("Du er nu logget ind som coach");
                    loggetInt = true;
                }
            } else if (Kasserer.getBrugernavn().equals(brugernavn)) {
                String password = input.next();

                if (getPassword("kasserer").equals(password)) {
                    Main.bruger = "kasserer";
                    System.out.println("Du er nu logget ind som kasserer");
                    loggetInt = true;
                }
            } else if (getBrugernavn("formand").equals(brugernavn)) {
                String password = input.next();

                if (getPassword("formand").equals(password)) {
                    Main.bruger = "formand";
                    System.out.println("Du er nu logget ind som formand");
                    loggetInt = true;
                }
            } else {
                System.out.println("");
                System.out.println("Forkert brugernavn eller kodeord.");
                System.out.println("Prøv igen");
                System.out.println("");
                Thread.sleep(1000);
            }
        }
    }

    */
}
