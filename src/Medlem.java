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

    public Medlem(String navn, String medlemskab, int medlemsNummer, String mail, LocalDate foedselsdag) {
        this.navn = navn;
        this.medlemskab = medlemskab;
        this.mail = mail;
        this.medlemsNummer = Medlemsnummer.hentNytMedlemsnummer();
        this.foedselsdag = foedselsdag;
    }

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