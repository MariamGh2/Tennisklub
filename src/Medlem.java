import java.time.LocalDate;
import java.time.Period;

public class Medlem {

//    private void alder;

    public Medlem() {
    } //Default Constructor


    private String navn;
    private String medlemskab;
    private int medlemNummer;
    private String mail;
    private LocalDate foedselsdag;

    public Medlem(String navn, String medlemskab, int medlemNummer, String mail, LocalDate foedselsdag) {
        this.navn = navn;
        this.medlemskab = medlemskab;
        this.mail = mail;
        this.medlemNummer = medlemNummer;
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

    public int getMedlemNummer() {
        return medlemNummer;
    }

    public int beregnAlder() {
        return Period.between(foedselsdag, LocalDate.now()).getYears();
    }
}