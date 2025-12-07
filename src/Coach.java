/*
Klassen oprettet objektet Coach.
Funktionerne for hvad coachen gerne vil se, top 5 osv.
Samt hvordan Coach logger ind (interface)
 */

public class Coach extends Medlem implements Bruger {

    public Coach(){} //Default Constructor

    private String brugernavn;
    private String password;

    public Coach(String navn, boolean medlemskab, String foedselsdag, String mail, String brugernavn, String password) {
        super("coach", navn, medlemskab, foedselsdag, mail);
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
    public void menu() {

    }

    public void topFem(){}

    public void opdaterResultat(){}

}
