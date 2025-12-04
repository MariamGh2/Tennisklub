import java.io.File;
import java.time.LocalDate;

//Denne klasse repræsenterer alle spillerem altså både motionister og konkurrence
public class Spiller extends Medlem {

    private File medlemFil = new File("medlem.txt");

    private String spillerType;
    private String position;

    public Spiller(String position, String navn, boolean medlemskab, String foedselsdag, String mail, String spillerType) {
        super(position, navn, medlemskab, foedselsdag, mail);
        this.spillerType = spillerType;
        this.position = "medlem";
    }

}