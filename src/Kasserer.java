import java.io.File;
import java.time.LocalDate;

public class Kasserer extends Medlem implements Bruger {


    public Kasserer (){} //Default Constructor

    private String brugernavn;
    private String password;
    private String position;

    public Kasserer (String navn, int medlemsNummer, boolean medlemskab, String foedselsdag, String mail, String brugernavn, String password) {
        super(navn, medlemsNummer, medlemskab, foedselsdag, mail);
        this.brugernavn = brugernavn;
        this.password = password;
        this.position = "kasserer";

        FileUtil.appendTilFil(new File("medlem.txt"), position + "_" + navn + "_" + medlemsNummer + "_" + medlemskab + "_" + foedselsdag + "_" + mail);
        FileUtil.appendTilFil(new File("personale.txt"), position + "_" + navn + "_" + medlemsNummer + "_" + medlemskab + "_" + foedselsdag + "_" + mail + "_" + brugernavn + "_" + password);
    }


    public String getBrugernavn() {
        return brugernavn;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void logud() {

    }

    @Override
    public void menu() {}

    //Funktion for at give oversigt over betalinger
    public void oversigt(){}

    //Sender rykker for en manglende betaling
    public void rykker(){}
}
