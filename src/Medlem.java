import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import static java.lang.String.format;

public abstract class Medlem {

//    private void alder;

    public Medlem() {} //Default Constructor

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private String navn;
    private int medlemsNummer;
    private boolean medlemskab;
    private String mail;
    private LocalDate foedselsdag;

    //Fil til at gemme medlemmeer
    //private static final Path FIL = Path.of("medlem.txt");

    public Medlem(String navn, int medlemsNummer, boolean aktivPassiv, String foedselsdag, String mail) {
        this.navn = navn;
        this.medlemskab = aktivPassiv;
        this.mail = mail;
        this.medlemsNummer = medlemsNummer;   //Medlemsnummeret er genereret da formanden laver en medlem inde på formand klassen. Her på medlems objektet bliver det indputtet fra filen efter generering
        this.foedselsdag = LocalDate.parse(foedselsdag, formatter);
    }

   /* /// GEMMER MEDLEM I medlem.txt
    private void skrivMedlemTilFil() {
        try {
            if (!Files.exists(FIL)) {  //Sørg for at filen findes (ellers opret tom fil)
                Files.writeString(FIL, "");
            }
            String linje = navn + ";" + medlemsNummer + ";" + foedselsdag + ";" + mail + "\n";    //Format på linjen der skrives

            Files.writeString(FIL, linje, StandardOpenOption.APPEND); //Tilføj til linjen nederst i filen

            System.out.println("Skrev linje: " + linje); //DEBUG

        } catch (IOException e) {
            throw new RuntimeException("Kunne ikke skrive til medlem.txt");
        }
    }

    */

    /// GETTERS
    public String getNavn() {
        return navn;
    }

    public int getMedlemsNummer() {
        return medlemsNummer;
    }

    public boolean getMedlemskab() {
        return medlemskab;
    }

    public String getMail() {
        return mail;
    }

    public void alder() {
    }

    public int beregnAlder() {
        return Period.between(foedselsdag, LocalDate.now()).getYears();
    }


    @Override
    public String toString() {
        return navn + " (Medlemsnr.: " + medlemsNummer + ")";
    }
}