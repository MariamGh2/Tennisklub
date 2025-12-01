import java.io.File;
import java.util.Scanner;

public class Main {

    public static String bruger;
    public static boolean taendt = true;

    public static void main(String[] args) {

        while (taendt) {

            login();

            if (bruger == "coach") {
                //Coach.menu();
            } else if (bruger == "formand") {
                //Formand.menu();
            } else if (bruger == "kasserer") {
                //Kasserer.menu();

            }
        }


        File fil = new File("medlem.txt");

        FileUtil.aendreDataIFil(fil, "Toki", 2, "Bak");
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
}
