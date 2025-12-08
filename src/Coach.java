/*
Klassen oprettet objektet Coach.
Funktionerne for hvad coachen gerne vil se, top 5 osv.
Samt hvordan Coach logger ind (interface)
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Coach extends Medlem implements Bruger {

    public Coach() {} //Default Constructor

    private String brugernavn;
    private String password;

    //Constructor for Coach
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
        Main.bruger = "coach";
    }

    @Override
    public void menu() {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Menu ==========================");
        System.out.println("Mulige kommandoer:");
        System.out.println("    opret ny kamp");
        System.out.println("    logud - Logger ud af bruger");

        String input = sc.nextLine();

        //Opret kamp
        if (input.equalsIgnoreCase("opret kamp")) {
            //opretKamp();

        } else if (input.equals("logud")) {
            logud();
        } else {
            System.out.println("Genkender ikke denne kommando. Prøv igen.");
        }
    }

    public void opretKamp(List<Turnering> turneringer, List<KonkurrenceSpillere> konkurrenceList, List<Kamp> kampe) {
        Scanner sc = new Scanner(System.in);

        //1. Tjekker om der er turneringer
        if (turneringer.isEmpty()) {
            System.out.println("Der findes ingen turneringer. Bed formanden om at oprette en turnering");
            return;
        }
        System.out.println("==== Vælg turnering ===");
        for (int i = 0; i < turneringer.size(); i++) {
            Turnering t = turneringer.get(i);
            System.out.println((i + 1) + ". " + t.getTurnering() + " (" + t.getDisciplinen() + ")");
        }

        System.out.println("Vælg turnerings rummer: ");
        int turneringsValg = Integer.parseInt(sc.nextLine());

        if (turneringsValg < 1 || turneringsValg > turneringer.size()) {
            System.out.println("Ugyldigt valg");
            return;
        }

        Turnering valgtTurnering = turneringer.get(turneringsValg - 1);

        //2. Væl spiller 1
        System.out.println("=== vælg spiller 1 ===");
        for (int i = 0; i < konkurrenceList.size(); i++) {
            System.out.println((i + 1) + ". " + konkurrenceList.get(i).getSpiller().getNavn());
        }

        System.out.println("Vælg spiller 1 nummer: ");
        int spiller1valg = Integer.parseInt(sc.nextLine());

        Spiller spiller1 = konkurrenceList.get(spiller1valg - 1).getSpiller();

        //3. Vælg spiller 2
        System.out.println("\n=== vælg spiller 2 ===");
        for (int i = 0; i < konkurrenceList.size(); i++) {
            System.out.println((i + 1) + ". " + konkurrenceList.get(i).getSpiller().getNavn());
        }

        System.out.println("Vælg spiller 2 nummer: ");
        int spiller2valg = Integer.parseInt(sc.nextLine());

        Spiller spiller2 = konkurrenceList.get(spiller2valg - 1).getSpiller();

        //4. Dato + disciplin
        System.out.println("Indtast kampdato: ");
        String dato = sc.nextLine();

        String disciplin = valgtTurnering.getDisciplinen(); //Turnering bestemmer disciplin

        //5. Opret kamp
        Kamp kamp = new Kamp(spiller1, spiller2, dato, disciplin, valgtTurnering);
        kampe.add(kamp);

        System.out.println("Kamp oprettet: " + spiller1.getNavn() + " VS. " + spiller2.getNavn() + " (" + disciplin + ")");
    }


/*
    Viser top 5 spillere for hver disciplin opdelt i junior og senior
    Listen "alleKonkurrenceSpillere" kommer fx fra klubbens datastruktur.
*/
    public void topFem(List<KonkurrenceSpillere> alleKonkurrenceSpillere) {
        topFemForDisciplinOgHold("single", "Junior", alleKonkurrenceSpillere);
        topFemForDisciplinOgHold("single", "Senior", alleKonkurrenceSpillere);

        topFemForDisciplinOgHold("double", "Junior", alleKonkurrenceSpillere);
        topFemForDisciplinOgHold("double", "Senior", alleKonkurrenceSpillere);

        topFemForDisciplinOgHold("mixed", "Junior", alleKonkurrenceSpillere);
        topFemForDisciplinOgHold("mixed", "Senior", alleKonkurrenceSpillere);
    }

    /*
    Hjælpemetode der:
    1) filtrerer alle konkurrencespillere efter disciplin + hold
    2) sorterer dem efter rangering (laveste tal = bedst)
    3) udskriver top 5
     */
    private void topFemForDisciplinOgHold(String disciplin, String hold, List<KonkurrenceSpillere> alle) {
        List<KonkurrenceSpillere> filtreret = new ArrayList<>(); //Filtrer spillere efter disciplin og hold
        for (KonkurrenceSpillere ks : alle) {
            if (ks.getDisciplin().equalsIgnoreCase(disciplin) && ks.getHold().equalsIgnoreCase(hold)) {
                filtreret.add(ks);
            }
        }

        //Sorter efter rangering (laveste først)
        filtreret.sort((k1, k2) -> Integer.compare(k1.getRangering(), k2.getRangering()));
        System.out.println("=== Top 5 - " + disciplin + " (" + hold + ") ===");

        //Print top 5
        for (int i = 0; i < Math.min(5, filtreret.size()); i++) {
            KonkurrenceSpillere ks = filtreret.get(i);
            System.out.println((i + 1) + ". " + ks.getSpiller().getNavn() +
                    " | Rangering: " + ks.getRangering() +
                    " | Bedste resultat: " + ks.getResultat());
        }
        if (filtreret.isEmpty()) {
            System.out.println("Ingen spillere i denne kategori endnu.");
        }
        System.out.println();
    }

    public void opdaterResultat(){}

}
