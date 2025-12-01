public interface Bruger {

    String password = "";
    String username = "";

    //Funktion for at logge ud af systemet
    void logud();

    //Funktion der giver menuen for brugeren
    void menu();

    //Funktion for at slukke systemet
    void sluk();
}
