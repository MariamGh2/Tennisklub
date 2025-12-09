import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
Klassen opretter objektet Coach.
Funktionerne for Coach:
    - Logge ind og ud (via Bruger-interfacet)
    - Tilgå en menu med coach-specifikke funktioner
    - Oprette kampe i en valgt turnering
    - Se top 5 spillere fordelt på disciplin og junior/senior
*/

public class Coach extends Medlem implements Bruger {

    public Coach() {} //Default Constructor

    private String brugernavn;
    private String password;

    //Lister som coach arbejder med
    private List<Turnering> turneringer;
    private List<KonkurrenceSpillere> konkurrenceList;
    private List<Kamp> kampe;

    //Constructor for Coach
    public Coach(String navn, boolean medlemskab, String foedselsdag, String mail,
                 String brugernavn, String password) {
        super(navn, medlemskab, foedselsdag, mail);
        this.brugernavn = brugernavn;
        this.password = password;
    }

    ///Settere til at give coach adgang til listerne
    public void setTurneringer(List<Turnering> turneringer) {
        this.turneringer = turneringer;
    }

    public void setKonkurrenceList(List<KonkurrenceSpillere> konkurrenceList) {
        this.konkurrenceList = konkurrenceList;
    }

    public void setKampe(List<Kamp> kampe) {
        this.kampe = kampe;
    }

                                ///GETTERS
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
        Main.bruger = "";
    }

                                ///MENU
    @Override
    public void menu() {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Menu ==========================");
        System.out.println("Mulige kommandoer:");
        System.out.println("    opret kamp - Opetter en kamp i en turnering");
        System.out.println("    top 5 spillere - Viser top 5 inden for hver disciplin");
        System.out.println("    logud - Logger ud af bruger");

        String input = sc.nextLine();

        //Opret kamp
        if (input.equalsIgnoreCase("Opret kamp")) {
            opretKamp();

        //Se top 5 spillere
        } else if (input.equalsIgnoreCase("Top 5 spillere")) {
            topFem();

        //Logud
        } else if (input.equals("logud")) {
            logud();

        //Forkert input
        } else {
            System.out.println("Genkender ikke denne kommando. Prøv igen");
        }
    }

                                ///OPRET KAMP
    public void opretKamp() {
        Scanner sc = new Scanner(System.in);

        if (turneringer == null || konkurrenceList == null || kampe == null) {
            System.out.println("Systemet er ikke sat rigtigt op (lister mangler)");
            return;
        }

        //1. Tjekker om der er turneringer
        File turneringsFil = new File ("turneringer.txt");
        if (!turneringsFil.exists() || turneringsFil.length() == 0) {
            System.out.println("Der findes ingen turneringer. Bed formanden om at oprette en turnering.");
            return;
        }

        System.out.println("==== Vælg turnering ====");
        for (Turnering t : turneringer) {
            System.out.println(t.getTurneringsNummer() + ". " + t.getTurnering() + " (" +
                    t.getDisciplinen() + ", " + t.getDatoen() + ") ");
        }

        System.out.println("Vælg turnerings nummer: ");
        int turneringsValg = Integer.parseInt(sc.nextLine());

        if (turneringsValg < 1 || turneringsValg > turneringer.size()) {
            System.out.println("Ugyldigt valg");
            return;
        }

        Turnering valgtTurnering = turneringer.get(turneringsValg - 1);

        if (konkurrenceList.isEmpty()) {
            System.out.println("Der er ingen konkurrencespillere registreret.");
            return;
        }

        //2. Væl spiller 1
        System.out.println("=== Vælg spiller 1 ===");
        for (int i = 0; i < konkurrenceList.size(); i++) {
            System.out.println((i + 1) + ". " + konkurrenceList.get(i).getSpiller().getNavn());
        }

        System.out.println("Vælg spiller 1 nummer: ");
        int spiller1valg = Integer.parseInt(sc.nextLine());

        if (spiller1valg < 1 || spiller1valg > konkurrenceList.size()) {
            System.out.println("Ugyldigt valg");
            return;
        }
        Spiller spiller1 = konkurrenceList.get(spiller1valg - 1).getSpiller();

        //3. Vælg spiller 2
        System.out.println("\n=== Vælg spiller 2 ===");
        for (int i = 0; i < konkurrenceList.size(); i++) {
            System.out.println((i + 1) + ". " + konkurrenceList.get(i).getSpiller().getNavn());
        }

        System.out.println("Vælg spiller 2 nummer: ");
        int spiller2valg = Integer.parseInt(sc.nextLine());
        if (spiller2valg < 1 || spiller2valg > konkurrenceList.size()) {
            System.out.println("Ugyldigt valg");
            return;
        }
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


                                    ///TOP 5
/*
    Viser top 5 spillere for hver disciplin opdelt i junior og senior
    Listen "alleKonkurrenceSpillere" kommer fx fra klubbens datastruktur.
*/
    public void topFem() {
        if (konkurrenceList == null || konkurrenceList.isEmpty()) {
            System.out.println("Der er ingen konkurrencespillere registreret endnu.");
            return;
        }

        topFemForDisciplinOgHold("single", "Junior", konkurrenceList);
        topFemForDisciplinOgHold("single", "Senior", konkurrenceList);

        topFemForDisciplinOgHold("double", "Junior", konkurrenceList);
        topFemForDisciplinOgHold("double", "Senior", konkurrenceList);

        topFemForDisciplinOgHold("mixed", "Junior", konkurrenceList);
        topFemForDisciplinOgHold("mixed", "Senior", konkurrenceList);
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

        if (filtreret.isEmpty()) {
            System.out.println("Ingen spillere i denne kategori endnu. \n");
            return;
        }

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
