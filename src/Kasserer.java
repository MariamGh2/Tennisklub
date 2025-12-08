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
    public void oversigt() throws Exception {
        //Udskriver medlemmer i restance og hvor meget restancen er (Kun medlemmer der har en aktiv restance) fra betaling.txt
        File file = new File("betaling.txt.");
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String linje = scanner.nextLine();
            String[] data = linje.split("_");

            boolean betalt = Boolean.parseBoolean(data[1]);

            if (!betalt) {
                System.out.println(data[0] + "skylder" + data[2] + " kr.");
            }

        }

    }}

//scanner.close();
//
//} catch (Exception e) {
//    System.out.println("kunne ikke læse betaling.txt");
//        }









