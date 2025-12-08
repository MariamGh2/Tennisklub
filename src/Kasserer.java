/*
Klassen oprettet objektet Kasserer.
Funktionerne for oversigt over betaling og rykkere
Samt hvordan Kasserer logger ind (interface)
 */


import java.io.File;

public class Kasserer extends Medlem implements Bruger {


    public Kasserer (){} //Default Constructor

    private String brugernavn;
    private String password;

    public Kasserer (String navn, boolean medlemskab, String foedselsdag, String mail, String brugernavn, String password) {
        //Kasserer får ikke et medlemsnummer
        super("kasserer", navn, medlemskab, foedselsdag, mail);
        this.brugernavn = brugernavn;
        this.password = password;


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
    public void menu() {}

    //Funktion for at give oversigt over betalinger
    public void oversigt(){}

    //Sender rykker for en manglende betaling
    public void rykker(){}
}
