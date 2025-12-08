import java.io.*;
import java.io.File;
import java.util.Scanner;


/*
Klassen oprettet objektet Formand.
Funktionerne for hvordan der oprettes et nyt medlem.
Samt hvordan Formand logger ind (interface)
 */


public class Formand extends Medlem implements Bruger {

    public Formand(){} //Default Constructor

    private String brugernavn;
    private String password;

    public Formand (String navn, boolean medlemskab, String foedselsdag, String mail,
                    String brugernavn, String password) {
        //Formand får ikke et medlemsnummer
        super("formand", navn, medlemskab, foedselsdag, mail);
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
    public void menu() {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Menu ==========================");
        System.out.println("Mulige kommandoer:");
        System.out.println("    opret - opretter en ny medlem");
        System.out.println("    slet - sletter en eksisterende medlem");
        System.out.println("    logud - Logger ud af bruger");

        String input = sc.nextLine();

        //Opret medlem
        if (input.equals("opret")) {
            opretMedlem();

        //Slet medlem
        } else if (input.equals("slet")) {
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
                    System.out.println("Annuleret.");
                    loop = false;
                } else {
                    System.out.println("Genkender ikke inputtet. Prøv igen.");
                }
            }
        } else if (input.equals("logud")) {
            logud();
        } else {
            System.out.println("Genkender ikke denne kommando. Prøv igen.");
        }
    }

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
        }

        Spiller s = new Spiller(navn, true, foedselsdag, mail, type);  //opret medlem (Medlems klassen klarer medlemsnummer + filskrivning + sortering)
        int medlemsNummer = s.getMedlemsNummer();

        FileUtil.appendTilFil(
                new File("medlem.txt"),
                navn + "_" + medlemsNummer + "_" + "true" + "_" + foedselsdag + "_" + mail + "_" + type + "_" + "true" + System.lineSeparator());

        KonkurrenceSpillere ks = new KonkurrenceSpillere();

        if (type.equals("konkurrencespiller")) {
            FileUtil.opretSpillerFil(s, ks);
        }

        Medlem.sorterFilEfterMedlemsnummer(); //Sortere filen efter oprettelse

    }

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
}
