import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
Klassen opretter objektet Kasserer.
Funktionerne for Kasserer:
    - Logge ind og ud (via Bruger-interfacet)
    - Registrere betaling af kontingent for medlemmer
    - Give oversigt over medlemmer i restance
    - Sende rykkere til medlemmer, der ikke har betalt til tiden
*/

public class Kasserer extends Medlem implements Bruger {

    public Kasserer() {
    } //Default Constructor

    private String brugernavn;
    private String password;

    //Constructor for Kasserer
    public Kasserer(String navn, boolean medlemskab, String foedselsdag, String mail, String brugernavn, String password) {
        //Kasserer får ikke et medlemsnummer
        super(navn, medlemskab, foedselsdag, mail);
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
    public void sluk() {
        System.exit(0);
    }

    @Override
    public void menu() throws Exception {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Menu ==========================");
        System.out.println("Mulige kommandoer:");
        System.out.println("    Betal - Betal kontingent for medlem");
        System.out.println("    oversigt - Viser oversigt over medlemmer i restance");
        System.out.println("    rykker - Sender rykker til alle medlemmer i restance");
        System.out.println("    logud - Logger ud af bruger");
        System.out.println("    sluk - Slukker for systemet");

        String input = sc.nextLine();

        if (input.equalsIgnoreCase("Betal")) {
            Kontingent.betalKontingent(); }

            //Viser oversigt
        else if (input.equalsIgnoreCase("Oversigt")) {
            oversigt();

        //Send Rykker
        } else if (input.equalsIgnoreCase("Rykker")){
            rykker();

        //Logud
        } else if (input.equalsIgnoreCase("logud")) {
            logud();

        } else if (input.equalsIgnoreCase("sluk")) {
            sluk();

            //Forkert Input
        } else {
            System.out.println("Genkender ikke denne kommando. Prøv igen.");
        }
    }

    //Sender rykker for en manglende betaling
    public void rykker() throws InterruptedException {
        boolean sendRykker = false;

        Scanner sc = new Scanner(System.in);

        List<Medlem> medlemList = FileUtil.laesMedlemFraFil();

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

                    Medlem m = null;

                    for (Medlem medlem : medlemList) {
                        if (medlem.getMedlemsNummer() == Integer.parseInt(nummer)) {
                            m = medlem;
                        }
                    }

                    if (!betalt) {
                        System.out.println("Sendt rykker til medlemsnummer " +  nummer + " på mail: " + m.getMail());
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