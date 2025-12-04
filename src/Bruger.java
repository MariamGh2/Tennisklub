import java.io.File;

public interface Bruger {

    //Funktion for at hente brugernavnet
    String getBrugernavn();

    //Funktion for at hente passwordet
    String getPassword();

    //Funktion for at logge ud af systemet
    void logud();

    //Funktion der giver menuen for brugeren
    void menu();
}
