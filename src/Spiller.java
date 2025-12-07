import java.io.File;

//Denne klasse repræsenterer alle spillerne altså både motionister og konkurrence


public class Spiller extends Medlem {

    private File medlemFil = new File("medlem.txt");

    private String type;  // "motionist" eller "konkurrencespiller"

    // 1) Bruges når du OPRETTER en ny spiller i programmet
    public Spiller(String navn, boolean medlemskab, String foedselsdag,
                   String mail, String type) {

        // Her genererer Medlem SELV medlemsnummer med Medlemsnummer.hentNytMedlemsnummer()
        super("spiller", navn, medlemskab, foedselsdag, mail);
        this.type = type;
    }

    // 2) Bruges hvis du senere vil læse spillere IND FRA FIL (med eksisterende medlemsnummer)
    public Spiller(String navn, int medlemsNummer, boolean medlemskab,
                   String foedselsdag, String mail, String type, boolean betaling) {

        super("spiller", navn, medlemsNummer, medlemskab, foedselsdag, mail, betaling);
        this.type = type;
    }

    public String getType() {
        return type;
    }
}



