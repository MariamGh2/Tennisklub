import java.io.*;
import java.io.File;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/*
Klassen opretter objektet Formand.
Funktionerne for Formand:
    - Logge ind og ud (via Bruger-interfacet)
    - Oprette nye medlemmer i klubben
    - Slette eksisterende medlemmer fra systemet
    - Oprette nye turneringer og gemme dem i fil
*/

public class Formand extends Medlem implements Bruger {

    public Formand(){} //Default Constructor

    private String brugernavn;
    private String password;

    //Constructor for Formand
    public Formand (String navn, boolean medlemskab, String foedselsdag, String mail,
                    String brugernavn, String password) {
        //Formand får ikke et medlemsnummer
        super(navn, medlemskab, foedselsdag, mail);
        this.brugernavn = brugernavn;
        this.password = password;
    }

    //Hjælpemetode: beregn hold (junior/senior) ud fra fødselsdag
    private String beregnHold(String foedselsdag) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate fodt = LocalDate.parse(foedselsdag, fmt);
        int alder = Period.between(fodt, LocalDate.now()). getYears();

        if (alder < 18) {
            return "junior";
        } else {
            return "senior";
        }
    }


                                        /// Bruger Interface
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
    public void menu() {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Menu ==========================");
        System.out.println("Mulige kommandoer:");
        System.out.println("    opret m - Opretter nyt medlem");
        System.out.println("    opret t - Opretter ny turnering");
        System.out.println("    slet - sletter et eksisterende medlem");
        System.out.println("    logud - Logger ud af bruger");

        String input = sc.nextLine();

        //Opret medlem
        if (input.equalsIgnoreCase("opret m")) {
            opretMedlem();

        //Opret turnering
        } else if (input.equalsIgnoreCase("opret t")){
            opretTurnering();

        //Slet medlem
        } else if (input.equalsIgnoreCase("slet")) {
            boolean loop = true;
            System.out.println("Indtast medlemsnummeret");
            int nummer = Integer.parseInt(sc.nextLine());
            System.out.println("Er du sikker på at du vil slette medlem " + nummer + "?");
            String svar = sc.nextLine();
            while (loop) {
                if (svar.equalsIgnoreCase("ja")) {
                    try {
                        sletMedlem(nummer);
                        loop = false;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else if (svar.equalsIgnoreCase("nej")) {
                    System.out.println("Annulleret.");
                    loop = false;
                } else {
                    System.out.println("Genkender ikke inputtet. Prøv igen.");
                }
            }

        //Logud
        } else if (input.equals("logud")) {
            logud();

        //Forkert input
        } else {
            System.out.println("Genkender ikke denne kommando. Prøv igen.");
        }
    }

                                    ///Opretter medlem
    public void opretMedlem(){

        String navn;
        String foedselsdag;
        String mail;
        String type;

        Scanner sc = new Scanner(System.in);

        System.out.println("Skriv navn");
        navn = sc.nextLine();

        System.out.println("Skriv fødselsdag");
        foedselsdag = sc.nextLine();

        System.out.println("Skriv mail");
        mail = sc.nextLine();

        System.out.println("Er medlemmen en motionist(1) eller konkurrencespiller(2)");
        type = sc.nextLine();

        if (type.equals("1")){
            type = "motionist";
            System.out.println("Du har valgt motionist");
        } else if (type.equals("2")){
            type = "konkurrencespiller";
            System.out.println("Du har valgt konkurrencespiller");
        } else {
            System.out.println("Ugyldigt valg. Afbryder oprettelse.");
            return;
        }

        //Opretter selve medlemmet
        Spiller s = new Spiller(navn, true, foedselsdag, mail, type);  //opret medlem (Medlems klassen klarer medlemsnummer + filskrivning + sortering)
        int medlemsNummer = s.getMedlemsNummer();

        //Beregner hold
        String hold = beregnHold (foedselsdag);
        s.setHold(hold);

        //NYT MEDLEM: betaling = false (ikke betalt endnu)
        boolean betaling = false;

        //Skriver medlemmet i medlem.txt
        FileUtil.appendTilFil(
                new File("medlem.txt"),
                navn + "_" +
                        medlemsNummer + "_" +
                        "true" + "_" +
                        foedselsdag + "_" +
                        mail + "_" +
                        type + "_" +
                        betaling + "_" +
                        hold);

        //Hvis konkurrencespiller -> spørg om disciplin og rangering og opret konkurrencefil
        if (type.equals("konkurrencespiller")) {

            System.out.println("Vælg disciplin:");
            System.out.println("   1 = single");
            System.out.println("   2 = double");
            System.out.println("   3 = mixed");
            String valg = sc.nextLine();

            String disciplin = "";
            if (valg.equals("1")) {
                disciplin = "singles";
            } else if (valg.equals("2")) {
                disciplin = "double";
            } else if (valg.equals("3")) {
                disciplin = "mixed";
            } else {
                System.out.println("Ugyldigt valg for disciplin");
            }

            System.out.println("Indtast start-rangering (lavt tal = bedre). Tom = 999:");
            String rangInput = sc.nextLine();
            int rangering;
            if (rangInput.isBlank()) {
                rangering = 999;   // ingen rigtig rangering endnu
            } else {
                rangering = Integer.parseInt(rangInput);
            }

            int startResultat = 0;
            String dato = "";   //Ingen dato endnu

            //Opret konkurrencespiller-objekt med alle data
            KonkurrenceSpillere ks = new KonkurrenceSpillere(
                    s,
                    disciplin,
                    hold,
                    rangering,
                    startResultat,
                    dato
            );

            FileUtil.opretSpillerFil(s, ks);
        }

        Medlem.sorterFilEfterMedlemsnummer(); //Sortere filen efter oprettelse

        System.out.println("Medlem oprette med medlemsnummer: " + medlemsNummer);
    }

                                ///Sletter medlem
    public void sletMedlem(int medlemsNummer) throws IOException {

        boolean medlemFundet = false;

        File originalFil = new  File("medlem.txt");
        File nyFil = new File("ny_medlems_fil.txt");

        BufferedReader reader = new BufferedReader(new FileReader(originalFil));
        BufferedWriter writer = new BufferedWriter(new FileWriter(nyFil));

        String linje;

        while ((linje = reader.readLine()) != null) {
            String[] dele = linje.split("_");
            if (String.valueOf(medlemsNummer).equals(dele[1])) {
                medlemFundet = true;
                continue;
            }
            writer.write(linje + System.lineSeparator());
        }

        if (!medlemFundet) {
            System.out.println("Medlem kunne ikke findes.");
        }

        writer.close();
        reader.close();

        if (!originalFil.delete()) {
            System.out.println("Could not delete file " + originalFil.getAbsolutePath());
            return;
        }
        nyFil.renameTo(originalFil);
    }

                        /// Opretter turnering
    public void opretTurnering(){
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Opret ny turnering ===");

        System.out.println("Skriv turneringsnavn: ");
        String navn = sc.nextLine();

        System.out.println("Skriv disciplin: ");
        String disciplin = sc.nextLine();

        System.out.println("Skriv dato: ");
        String dato = sc.nextLine();

        //Opretter turneringen.
        //Turnerings-klassen skriver selv data til turneringer.txt
        Turnering t = new Turnering(navn, disciplin, dato);

        System.out.println("Turnering oprettet: " + t.getTurnering() + " (" + t.getDisciplinen() + ", " + t.getDatoen()
        + ")");
    }
}
