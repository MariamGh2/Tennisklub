import java.io.File;
import java.time.LocalDate;

//Denne klasse repræsenterer alle spillerem altså både motionister og konkurrence
public class Spiller extends Medlem {

    private File medlemFil = new File("medlem.txt");

    private String spillerType;
    private String position;

    public Spiller(String navn, int medlemsNummer, boolean medlemskab, String foedselsdag, String mail, String spillerType) {
        super(navn, medlemsNummer, medlemskab, foedselsdag, mail);
        this.spillerType = spillerType;
        this.position = "medlem";
    }

}