import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;

/*
Klassen repræsenterer ét medlem i klubben.
Hvert medlem har:
    - Navn
    - Medlemskabstype (aktiv / passiv)
    - Medlemsnummer (genereres automatisk)
    - Mailadresse
    - Fødselsdato
    - Betalingsstatus for kontingent

Klassen har desuden ansvaret for:
    - At tildele medlemsnummer gennem Medlemsnummer-klassen
    - At kunne gemme og indlæse medlemsdata fra medlem.txt
    - At beregne alder ud fra fødselsdato
*/

public abstract class Medlem {


    public Medlem() {} //Default Constructor

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private String navn;
    private int medlemsNummer;
    private boolean medlemskab;  //true = aktiv, false = passiv
    private String mail;
    private LocalDate foedselsdag;
    private boolean betaling;
    private String hold;

    private static final Path FIL = Path.of("medlem.txt");   //Sti til filen hvor ALT gemmes

    public Medlem(String navn, boolean aktivPassiv, String foedselsdag, String mail) {
        this.navn = navn;
        this.medlemskab = aktivPassiv;
        this.mail = mail;
        this.foedselsdag = LocalDate.parse(foedselsdag, formatter);
        this.betaling = false;   //Nyt medlem -> ikke betalt

        //ALTID nyt nummer her
        this.medlemsNummer = Medlemsnummer.hentNytMedlemsnummer();
    }

    public Medlem(String navn, int medlemsNummer, boolean aktivPassiv, String foedselsdag, String mail, boolean betaling){
        this.navn = navn;
        this.medlemskab = aktivPassiv;
        this.mail = mail;
        this.foedselsdag = LocalDate.parse(foedselsdag, formatter);
        this.betaling = betaling;  //betalingsstatus som står i filen

        //Brug det nummer vi læser fra fil - INGEN ny generering
        this.medlemsNummer = medlemsNummer;
    }

                               /// GETTERS
    public String getNavn() {
        return navn;
    }

    public boolean getMedlemskab() {
        return medlemskab;
    }

    public String getMail() {
        return mail;
    }

    public int getMedlemsNummer() {
        return medlemsNummer;
    }

    public int getBeregnAlder() {
        return Period.between(foedselsdag, LocalDate.now()).getYears();
    }

    public String getHold(){
        return hold;
    }

    public void setHold(String hold){
        this.hold = hold;
    }

    @Override
    public String toString() {
        return navn + " (Medlemsnr.: " + medlemsNummer + ")";
    }
}