import java.util.Scanner;

public class Main {

    /*
    Variabel der fortæller hvilken typer bruger der er logget ind
    */


                                       /// LOGIN METODE
    //Spørger efter brugernavn og password indtil en af de tre roller logger ind
    public static void login(Coach coach, Formand formand, Kasserer kasserer) throws InterruptedException {

        Scanner input = new Scanner(System.in);


        while (bruger.equals("")) {   //Kører så længe der ikke er logget ind
            System.out.println("=== LOGIN =============");
            System.out.println("Brugernavn: ");
            String brugernavn = input.next();

            System.out.println("Password: ");
            String password = input.next();

            //Coach login
            if (coach.getBrugernavn().equals(brugernavn)
                    && coach.getPassword().equals(password)) {

                bruger = "coach";
                System.out.println("Du er nu logget ind som coach");
            }

            //Kasserer login
            else if (kasserer.getBrugernavn().equals(brugernavn)
                    && kasserer.getPassword().equals(password)) {

                bruger = "kasserer";
                System.out.println("Du er nu logget ind som kasserer");
            }

            //Formand login
            else if (formand.getBrugernavn().equals(brugernavn)
                    && formand.getPassword().equals(password)) {

                bruger = "formand";
                System.out.println("Du er nu logget ind som formand");
            }

            //Hvis ingen passede -> fejlbesked + prøv igen
            else {
                System.out.println("Forkert brugernavn eller kodeord.");
                System.out.println("Prøv igen");
                System.out.println();
                Thread.sleep(1000); //Lille pause før næste forsøg
            }
        }
    }


    public static String bruger = "";

    public static void main(String[] args) throws InterruptedException {

        //opretter mappen
        //File m = FileUtilKonkurrence.OpretKonkurrenceMappe();

        //Opretter de tre system brugere
        Formand formand = new Formand("Cass", 0, true, "03-07-2000",
                "tokiarb@gmail.com", "Formand", "123");

        Coach coach = new Coach("Lina", 0, true, "03-07-2000",
                "tokiarb@gmail.com", "Coach", "123");

        Kasserer kasserer = new Kasserer("toki", 0, true, "03-07-2000",
                "tokiarb@gmail.com", "Kassere", "123");

        //login
        login(coach, formand, kasserer);

        //Når login lykkedes, er Main.bruger sat til coach, formand eller kasserer
        if ("coach".equals(bruger)) {
            System.out.println("Der er ingen funktion endnu");

        } else if ("formand".equals(bruger)) {
            System.out.println("Skriv - opret - for at oprette et medlem");

            Scanner sc = new Scanner(System.in);
            String input = sc.nextLine();

            if (input.equalsIgnoreCase("Opret")) {
                formand.opretMedlem(); //kalder opret-medlem-funktion
                Medlem.sorterFilEfterMedlemsnummer(); //Sortere filen efter oprettelse
            } else {
                System.out.println("Jeg genkender ikke denne kommando");
            }
        } else if ("kasserer".equals(bruger)) {
            System.out.println("Der er ingen funktioner endnu");
        }
    }
}



