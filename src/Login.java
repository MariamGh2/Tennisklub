import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
Klassen har ansvaret for oprettelse og login for Coach, kasserer og formand.
 */

public class Login {

    //Login Funktionen og oprettelse af brugere
    public static void login() throws InterruptedException {

        boolean coachExists = false;
        boolean kassererExists = false;
        boolean formandExists = false;

        List<Object> personaleListe = new ArrayList<>();
        personaleListe = FileUtil.laesPersonaleFraFil();

        for (Object o : personaleListe) {

            if (o instanceof Coach) {
                coachExists = true;
            } else if (o instanceof Kasserer) {
                kassererExists = true;
            } else if  (o instanceof Formand) {
                formandExists = true;
            }
        }

        if (!coachExists) {
            Coach coach = new Coach("Lina", true, "03-07-2000",
                    "tokiarb@gmail.com", "Coach", "123");
            FileUtil.appendTilFil(new File("personale.txt"),
                    "coach_Lina_true_03-07-2000_tokiarb@gmail.com_Coach_123");
        }

        if (!kassererExists) {
            Kasserer kasserer = new Kasserer("toki", true, "03-07-2000",
                    "tokiarb@gmail.com", "Kasserer", "123");
            FileUtil.appendTilFil(new File("personale.txt"),
                    "kasserer_toki_true_03-07-2000_tokiarb@gmail.com_Kasserer_123");
        }

        if (!formandExists) {
            Formand formand = new Formand("Cass", true, "03-07-2000",
                    "tokiarb@gmail.com", "Formand", "123");
            FileUtil.appendTilFil(new File("personale.txt"),
                    "formand_Cass_true_03-07-2000_tokiarb@gmail.com_Formand_123");
        }

        while (Main.bruger == "") {
            Scanner input = new Scanner(System.in);

            System.out.println("=== LOGIN =============");
            System.out.println("Brugernavn:");
            String brugernavn = input.nextLine();

            for (Object o : personaleListe) {
                if (o instanceof Coach c) {
                    if (c.getBrugernavn().equals(brugernavn)) {
                        System.out.println("Brugernavn: " + brugernavn);

                        System.out.println("Password:");
                        String password = input.nextLine();
                        if (c.getPassword().equals(password)) {
                            Main.bruger = "coach";
                            System.out.println("Du er nu logget ind som coach");
                        }
                    }
                } else if (o instanceof Formand f) {
                    if (f.getBrugernavn().equals(brugernavn)) {
                        System.out.println("Brugernavn: " + brugernavn);

                        System.out.println("Password:");
                        String password = input.nextLine();
                        if (f.getPassword().equals(password)) {
                            Main.bruger = "formand";
                            System.out.println("Du er nu logget ind som formand");
                        }
                    }
                } else if (o instanceof Kasserer k) {
                    if (k.getBrugernavn().equals(brugernavn)) {
                        System.out.println("Brugernavn: " + brugernavn);

                        System.out.println("Password:");
                        String password = input.nextLine();
                        if (k.getPassword().equals(password)) {
                            Main.bruger = "kasserer";
                            System.out.println("Du er nu logget ind som kasserer");
                        }
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
    }
}
