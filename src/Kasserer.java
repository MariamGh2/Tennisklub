/*
Klassen oprettet objektet Kasserer.
Funktionerne for oversigt over betaling og rykkere
Samt hvordan Kasserer logger ind (interface)
 */


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Scanner;

public class Kasserer extends Medlem implements Bruger {


    public Kasserer (){} //Default Constructor

    private String brugernavn;
    private String password;

    //Constructor for Kasserer
    public Kasserer (String navn, boolean medlemskab, String foedselsdag, String mail, String brugernavn, String password) {
        //Kasserer får ikke et medlemsnummer
        super("kasserer", navn, medlemskab, foedselsdag, mail);
        this.brugernavn = brugernavn;
        this.password = password;
    }

    @Override
    public String getBrugernavn() {
        return brugernavn;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void logud() {
        Main.bruger = "";
    }

    @Override
    public void menu() throws Exception {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Menu ==========================");
        System.out.println("Mulige kommandoer:");
        System.out.println("    oversigt - Viser oversigt over medlemmer i restance");
        System.out.println("    rykker - Sender rykker til alle medlemmer i restance");
        System.out.println("    logud - Logger ud af bruger");

        String input = sc.nextLine();

        //Viser oversigt
        if (input.equalsIgnoreCase("oversigt")) {
            oversigt();

        //Send Rykker
        } else if (input.equalsIgnoreCase("rykker")){
            rykker();

        //Logud
        } else if (input.equals("logud")) {
            logud();

        //Forkert Input
        } else {
            System.out.println("Genkender ikke denne kommando. Prøv igen.");
        }
    }

    //Sender rykker for en manglende betaling
    public void rykker() throws InterruptedException {
        boolean sendRykker = false;

        Scanner sc = new Scanner(System.in);

        LocalDate dagsDato = Dato.getDato();
        LocalDate frist = Dato.fristDato();

        if (dagsDato.isAfter(frist) || dagsDato.isEqual(frist)) {
            System.out.println("Send rykker?");
            String input = sc.nextLine();
            if (input.equalsIgnoreCase("ja")) {
                sendRykker = true;
            } else if  (input.equalsIgnoreCase("nej")) {
                sendRykker = false;
                System.out.println("Annulleret");
            } else {
                System.out.println("Fejl input");
            }
        } else {
            System.out.println("Rykker skal ikke sendes før den 1. februar");
        }

        if (sendRykker) {
            try (BufferedReader br = new BufferedReader(new FileReader("betaling.txt"))) {
                String linje;

                while ((linje = br.readLine()) != null) {
                    String[] dele = linje.split("_");

                    String nummer = dele[0];
                    boolean betalt = Boolean.parseBoolean(dele[1]);
                    String restance = dele[2];

                    if (!betalt) {
                        System.out.println("Sendt rykker til medlemsnummer " +  nummer);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        Thread.sleep(1000);
        System.out.println("");
    }

    //Funktion for at give oversigt over betalinger
    public void oversigt() throws Exception {
        System.out.println("=== Oversigt ================");
        try (BufferedReader br = new BufferedReader(new FileReader("betaling.txt"))) {
            String linje;

            while ((linje = br.readLine()) != null) {
                String[] dele = linje.split("_");

                String nummer = dele[0];
                boolean betalt = Boolean.parseBoolean(dele[1]);
                String restance = dele[2];

                if (!betalt) {
                    System.out.println("Medlemsnummer: " + nummer + "  Restance: " + restance + "kr.");
                }
            }
        }  catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("");
        Thread.sleep(1000);
    }
}