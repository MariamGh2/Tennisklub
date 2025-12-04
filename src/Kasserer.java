import java.io.File;
import java.time.LocalDate;

public class Kasserer extends Medlem implements Bruger {


    public Kasserer (){} //Default Constructor

    private String brugernavn;
    private String password;

    public Kasserer (String navn, boolean medlemskab, String foedselsdag, String mail, String brugernavn, String password) {
        super("kasserer", navn, medlemskab, foedselsdag, mail);
        this.brugernavn = brugernavn;
        this.password = password;

        FileUtil.appendTilFil(new File("medlem.txt"), this.getPosition() + "_" + navn + "_" + this.getMedlemsNummer() + "_" + medlemskab + "_" + foedselsdag + "_" + mail);
        FileUtil.appendTilFil(new File("personale.txt"), this.getPosition() + "_" + navn + "_" + this.getMedlemsNummer() + "_" + medlemskab + "_" + foedselsdag + "_" + mail + "_" + brugernavn + "_" + password);
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
