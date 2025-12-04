import java.io.File;
import java.time.LocalDate;

public class Coach extends Medlem implements Bruger {

    public Coach(){} //Default Constructor

    private String brugernavn;
    private String password;

    public Coach(String navn, boolean medlemskab, String foedselsdag, String mail, String brugernavn, String password) {
        super("coach", navn, medlemskab, foedselsdag, mail);
        this.brugernavn = brugernavn;
        this.password = password;

        FileUtil.appendTilFil(new File("medlem.txt"), this.getPosition() + "_" + navn + "_" + this.getMedlemsNummer() + "_" + medlemskab + "_" + foedselsdag + "_" + mail);
        FileUtil.appendTilFil(new File("personale.txt"), this.getPosition() + "_" + navn + "_" + this.getMedlemsNummer() + "_" + medlemskab + "_" + foedselsdag + "_" + mail + "_" + brugernavn + "_" + password);
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
