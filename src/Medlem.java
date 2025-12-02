import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.Period;


public class Medlem {

//    private void alder;

    public Medlem(String medlem, int i, boolean b) {} //Default Constructor


    private String navn;
    private String medlemskab;
    private int medlemsNummer;
    private String mail;
    private LocalDate foedselsdag;

    //Fil til at gemme medlemmeer
    private static final Path FIL = Path.of("medlem.txt");

    public Medlem(String navn, String medlemskab, int medlemsNummer, String mail, LocalDate foedselsdag) {
        this.navn = navn;
        this.medlemskab = medlemskab;
        this.mail = mail;
        this.medlemsNummer = Medlemsnummer.hentNytMedlemsnummer();   //Genere nyt medlemsnr.
        this.foedselsdag = foedselsdag;

        skrivMedlemTilFil();   //Gem medlem i filen
    }

    /// GEMMER MEDLEM I medlem.txt
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

    /// GETTERS
    public String getNavn() {
        return navn;
    }

    public String getMedlemskab() {
        return medlemskab;
    }

    public String getMail() {
        return mail;
    }

    public void alder() {
    }

    public int getMedlemsNummer() {
        return medlemsNummer;
    }

    public int beregnAlder() {
        return Period.between(foedselsdag, LocalDate.now()).getYears();
    }



    @Override
    public String toString(){
        return navn + " (Medlemsnr.: "+ medlemsNummer + ")";
    }
}