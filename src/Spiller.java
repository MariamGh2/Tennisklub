import java.io.File;

/*
Klassen har ansvaret for:
    - At repræsentere klubbens spiller både,
        - Motionister
        - Konkurrence spillere
    - At gemme hvilken type spiller det er,
        - "motionist"
        - "konkurrenceSpiller"
    - At håndtere oprettelse af nye spillere med automatisk medlemsnummer
    - At kunne genopbygge spillere ved indlæsning fra fil (med eksistrerende data)
 */

public class Spiller extends Medlem {

    private File medlemFil = new File("medlem.txt");

    private String spillerType;  // "motionist" eller "konkurrencespiller"

    // 1) Bruges når du OPRETTER en ny spiller i programmet
    public Spiller(String navn, boolean medlemskab, String foedselsdag,
                   String mail, String spillerType) {

        // Her genererer Medlem SELV medlemsnummer med Medlemsnummer.hentNytMedlemsnummer()
        super(navn, medlemskab, foedselsdag, mail);
        this.spillerType = spillerType;
    }

    // 2) Bruges hvis du senere vil læse spillere IND FRA FIL (med eksisterende medlemsnummer)
    public Spiller(String navn, int medlemsNummer, boolean medlemskab,
                   String foedselsdag, String mail, String spillerType, boolean betaling) {

        super(navn, medlemsNummer, medlemskab, foedselsdag, mail, betaling);
        this.spillerType = spillerType;
    }

    public String getSpillerType() {
        return spillerType;
    }
}



