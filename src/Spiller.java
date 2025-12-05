import java.io.File;
import java.time.LocalDate;

//Denne klasse repræsenterer alle spillerne altså både motionister og konkurrence


public class Spiller extends Medlem {

    private File medlemFil = new File("medlem.txt");

    private String spillerType;

    public Spiller(String navn, int medlemsNummer, boolean medlemskab, String foedselsdag, String mail, String spillerType) {
        super("medlem", navn, medlemsNummer, medlemskab, foedselsdag, mail);
        this.spillerType = spillerType;
    }

}