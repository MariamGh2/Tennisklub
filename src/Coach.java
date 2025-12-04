import java.io.File;
import java.time.LocalDate;

public class Coach extends Medlem implements Bruger {

    public Coach(){} //Default Constructor

    private File medlemFil = new File("medlem.txt");

    private String brugernavn;
    private String password;
    private String position;

    public Coach(String navn, int medlemsNummer, boolean medlemskab, String foedselsdag, String mail, String brugernavn, String password) {
        super(navn, medlemsNummer, medlemskab, foedselsdag, mail);
        this.brugernavn = brugernavn;
        this.password = password;
        this.position = "coach";

        FileUtil.appendTilFil(new File("medlemFil.txt"), position + "_" + navn + "_" + medlemsNummer + "_" + medlemskab + "_" + foedselsdag + "_" + mail);
        FileUtil.appendTilFil(new File("personale.txt"), position + "_" + navn + "_" + medlemsNummer + "_" + medlemskab + "_" + foedselsdag + "_" + mail + "_" + brugernavn + "_" + password);
    }

    @Override
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
    public void menu() {

    }


    public void topFem(){}


    public void opdaterResultat(){}

}
