/*
Henter brugernavn og password.
Interface for Coach, formand og kasserer
Interface er tom funktion - super klasse som har tomme funktioner
 */

public interface Bruger {

    //Funktion for at hente brugernavnet
    String getBrugernavn();

    //Funktion for at hente passwordet
    String getPassword();

    //Funktion for at logge ud af systemet
    void logud();

    //Funktion der giver menuen for brugeren
    void menu() throws Exception;
}
