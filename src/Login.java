import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
Klassen har ansvaret for:
    - Oprettelse af systemets brugere:
        - Coach
        - Kasserer
        - Formand
    - Login-funktionalitet baseret på brugernavn og kodeord
    - At identificere hvilken bruger der logger ind
    - At håndtere udlogning
*/

public class Login {

    //Login funktionen og oprettelse af brugere
    public static void login() throws Exception {

        boolean coachExists = false;
        boolean kassererExists = false;
        boolean formandExists = false;

        //Læs personale fra fil (coach, kassere, formand)
        List<Object> personaleListe = FileUtil.laesPersonaleFraFil();


        //Tjek om de tre roller findes i filen
        for (Object o : personaleListe) {
            if (o instanceof Coach) {
                coachExists = true;
            } else if (o instanceof Kasserer) {
                kassererExists = true;
            } else if  (o instanceof Formand) {
                formandExists = true;
            }
        }

        //His coach ikke findes i filen -> opret standard coach
        if (!coachExists) {
            Coach coach = new Coach("Lina", true, "03-07-2000",
                    "tokiarb@gmail.com", "Coach", "123");

            //Skriv til fil
            FileUtil.appendTilFil(new File("personale.txt"),
                    "coach_Lina_true_03-07-2000_tokiarb@gmail.com_Coach_123");
        }

        //His kasserer ikke findes i filen -> opret standard coach
        if (!kassererExists) {
            Kasserer kasserer = new Kasserer("toki", true, "03-07-2000",
                    "tokiarb@gmail.com", "Kasserer", "123");

            //Skriv til til
            FileUtil.appendTilFil(new File("personale.txt"),
                    "kasserer_toki_true_03-07-2000_tokiarb@gmail.com_Kasserer_123");
        }

        //Hvis formand ikke findes i filen -> opret standard formand
        if (!formandExists) {
            Formand formand = new Formand("Cass", true, "03-07-2000",
                    "tokiarb@gmail.com", "Formand", "123");

            //SKriv til fil
            FileUtil.appendTilFil(new File("personale.txt"),
                    "formand_Cass_true_03-07-2000_tokiarb@gmail.com_Formand_123");
        }

        Scanner input = new Scanner(System.in);

        while (Main.bruger.equals("")) {
            System.out.println("=== LOGIN =============");
            System.out.println("Brugernavn:");
            String brugernavn = input.nextLine();

            boolean foundUser = false;

            for (Object o : personaleListe) {

                // ====== Coach login ======
                if (o instanceof Coach c) {
                    if (c.getBrugernavn().equals(brugernavn)) {
                    foundUser = true;
                        System.out.println("Brugernavn: " + brugernavn);
                        System.out.println("Password:");
                        String password = input.nextLine();
                        if (c.getPassword().equals(password)) {
                            Main.bruger = "coach";
                            System.out.println("Du er nu logget ind som coach");
                            c.menu();
                            break;
                        }
                    }

                // ======= Formand login ======
                } else if (o instanceof Formand f) {
                    if (f.getBrugernavn().equals(brugernavn)) {
                        foundUser = true;
                        System.out.println("Brugernavn: " + brugernavn);
                        System.out.println("Password:");
                        String password = input.nextLine();
                        if (f.getPassword().equals(password)) {
                            Main.bruger = "formand";
                            System.out.println("Du er nu logget ind som formand");
                            f.menu();
                            break;
                        }
                    }

                // ====== Kasserer login ======
                } else if (o instanceof Kasserer k) {
                    if (k.getBrugernavn().equals(brugernavn)) {
                        foundUser = true;
                        System.out.println("Brugernavn: " + brugernavn);
                        System.out.println("Password:");
                        String password = input.nextLine();
                        if (k.getPassword().equals(password)) {
                            Main.bruger = "kasserer";
                            System.out.println("Du er nu logget ind som kasserer");
                            k.menu();
                            break;
                        }
                    }
                }
            }
            if (!foundUser) {
                System.out.println();
                System.out.println("Forkert brugernavn eller kodeord.");
                System.out.println("Prøv igen.");
                System.out.println();
                Thread.sleep(1000);
            }
        }
    }
}
