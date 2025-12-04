import java.io.*;
import java.time.LocalDate;
import java.util.Scanner;

public class Formand extends Medlem implements Bruger {
    public Formand(){} //Default Constructor

    private String brugernavn;
    private String password;
    private String position;

    public Formand(String navn, int medlemsNummer, boolean medlemskab, String foedselsdag, String mail, String brugernavn, String password) {
        super(navn, medlemsNummer, medlemskab, foedselsdag, mail);
        this.brugernavn = brugernavn;
        this.password = password;
        this.position = "formand";

        FileUtil.appendTilFil(new File("medlemFil.txt"), position + "_" + navn + "_" + medlemsNummer + "_" + medlemskab + "_" + foedselsdag + "_" + mail);
        FileUtil.appendTilFil(new File("personale.txt"), position + "_" + navn + "_" + medlemsNummer + "_" + medlemskab + "_" + foedselsdag + "_" + mail + "_" + brugernavn + "_" + password);
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

    }

    @Override
    public void menu() {

    }


    public void opretMedlem(){

        String navn;
        int medlemsNummer;
        String foedselsdag;
        String mail;
        String type;


        Scanner sc = new Scanner(System.in);

        System.out.println("Skriv navn");
        navn = sc.nextLine();

        medlemsNummer = Medlemsnummer.hentNytMedlemsnummer();

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

        File fil = new File("medlem.txt");

        FileUtil.appendTilFil(fil, "medlem" + "_" + navn + "_" + medlemsNummer + "_" + "true" + "_" + foedselsdag + "_" + mail + "_" + type);
    }


    public void sletMedlem(int medlemsNummer) throws IOException {
        File originalFil = new  File("medlem.txt");
        File nyFil = new File("ny_medlems_fil.txt");

        BufferedReader reader = new BufferedReader(new FileReader(originalFil));
        BufferedWriter writer = new BufferedWriter(new FileWriter(nyFil));

        String linje;

        while ((linje = reader.readLine()) != null) {
            String[] dele = linje.split("_");

            if (String.valueOf(medlemsNummer).equals(dele[1])) {
                continue;
            }
            writer.write(linje + System.lineSeparator());
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
