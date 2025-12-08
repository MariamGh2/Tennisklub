/*
Klassen oprettet objektet Kasserer.
Funktionerne for oversigt over betaling og rykkere
Samt hvordan Kasserer logger ind (interface)
 */


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Scanner;

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

    //Sender rykker for en manglende betaling
    /*public void rykker(){
        Scanner sc = new Scanner(System.in);

        LocalDate dagsDato = Dato.getDato();
        LocalDate frist = Dato.fristDato();

        if (dagsDato.isBefore(frist)) {
            System.out.println("Det er den " + dagsDato.getDayOfMonth() + "-" + dagsDato.getMonth() + ". Er du sikker på at du vil sende en rykker?");
            String input = sc.nextLine();

            if (input.equalsIgnoreCase("ja")) {
                try (BufferedReader br = new BufferedReader(new FileReader("betaling.txt"))) {
                    String linje;

                    while ((linje = br.readLine()) != null) {
                        String[] dele = linje.split("_");

                        String nummer = dele[0];
                        boolean betalt = Boolean.parseBoolean(dele [1]);
                        String restance = dele[2];

                        if (!betalt) {

                        }
                    }
                }
            }
        }


     */



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









