import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    public void setKampe(List<Kamp> kampe) {
        this.kampe = kampe;
    }


    //Hjælpemetode: find en Spiller ud fra medlemsnummer ved at læse medlem.txt
    private Spiller findSpillerVedMedlemsnummer(int medlemsNummer) {
        List<Medlem> medlemmer = FileUtil.laesMedlemFraFil();   //Læser alle medlemmer (Spiller-objekt)

        for (Medlem m : medlemmer) {
            if (m instanceof Spiller s) {
                if (s.getMedlemsNummer() == medlemsNummer) {
                    return s;
                }
            }
        }
        return null;   //Hvis ingen fundet
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
        System.out.println("    opret kamp - Opretter en kamp i en turnering");
        System.out.println("    top 5 spillere - Viser top 5 inden for hver disciplin");
        System.out.println("    opdater resultat - Opdatere resultatet for en spiller");
        System.out.println("    logud - Logger ud af bruger");

        String input = sc.nextLine();

        //Opret kamp
        if (input.equalsIgnoreCase("Opret kamp")) {
            opretKamp();

        //Se top 5 spillere
        } else if (input.equalsIgnoreCase("Top 5 spillere")) {
            topFem();

        //Opdatere resultat for en spiller
        } else if (input.equalsIgnoreCase("opdater resultat")) {
            opdaterResultat();

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

        if (turneringer == null || kampe == null) {
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

        //Find den valgte turnering ud fra dens nummer (ikke index)
        System.out.println("Vælg turnerings nummer: ");
        int turneringsValg = Integer.parseInt(sc.nextLine());

        Turnering valgtTurnering = null;
        for (Turnering t : turneringer) {
            if (t.getTurneringsNummer() == turneringsValg) {
                valgtTurnering = t;
                break;
            }
        }

        if (valgtTurnering == null) {
            System.out.println("Ingen turnering med det nummer.");
            return;
        }

        //2. Læs konkurrencespillere fra mappen "konkurrenceSpillere"
        File mappe = FileUtil.OpretKonkurrenceMappe();   //Sikrer at mappen findes
        File[] filer = mappe.listFiles((dir, name) -> name.endsWith(".txt"));

        if (filer == null || filer.length == 0) {
            System.out.println("Der er ingen konkurrencespillere registreret i 'konkurrenceSpillere'.");
            return;
        }

        //Byg en liste over kandidater (Spiller-objekter)
        List<Spiller> kandidater = new ArrayList<>();

        System.out.println("=== Vælg spiller blandt konkurrencespillere ===");
        for (File f : filer) {
            String filnavn = f.getName();   //Fx "12_MadsJensen.txt"
            String udenExt = filnavn.replace(".txt", "");
            String[] dele = udenExt.split("_");   //Fx ["12, "MadsJensen" ]

            if (dele.length >= 1) {
                try {
                    int medlemsNr = Integer.parseInt(dele[0].trim());

                    Spiller s = findSpillerVedMedlemsnummer(medlemsNr);
                    if (s != null) {
                        kandidater.add(s);
                    } else {
                        System.out.println("Advarsel: Kunne ikke finde spiller med medlemsnr. " + medlemsNr +
                                " i medlem.txt");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Advarsel: Kunne ikke læse medlemsnummer i fil: " + filnavn);
                }
            }
        }

        if (kandidater.isEmpty()) {
            System.out.println("Ingen gyldige konkurrencespillere fundet i mappen.");
            return;
        }


        //3. Væl spiller 1
        System.out.println("=== Vælg spiller 1 ===");
        for (int i = 0; i < kandidater.size(); i++) {
            Spiller s = kandidater.get(i);
            System.out.println((i + 1) + ". " + s.getNavn() + " (medlemsnr.: " + s.getMedlemsNummer() + ")");
        }

        System.out.println("Vælg spiller 1 nummer: ");
        int spiller1valg = Integer.parseInt(sc.nextLine());

        if (spiller1valg < 1 || spiller1valg > kandidater.size()) {
            System.out.println("Ugyldigt valg");
            return;
        }
        Spiller spiller1 = kandidater.get(spiller1valg - 1);

        //4. Vælg spiller 2
        System.out.println("\n=== Vælg spiller 2 ===");
        for (int i = 0; i < kandidater.size(); i++) {
            Spiller s = kandidater.get(i);
            System.out.println((i + 1) + ". " + s.getNavn() + " (medlemsnr.: " + s.getMedlemsNummer() + ")");
        }

        System.out.println("Vælg spiller 2 nummer: ");
        int spiller2valg = Integer.parseInt(sc.nextLine());

        if (spiller2valg < 1 || spiller2valg > kandidater.size()) {
            System.out.println("Ugyldigt valg");
            return;
        }
        Spiller spiller2 = kandidater.get(spiller2valg - 1);

        //For at være ekstra sikker
        if (spiller1.getMedlemsNummer() == spiller2.getMedlemsNummer()) {
            System.out.println("En spiller kan ikke spille mod sig selv. Vælg to forskellige spillere.");
            return;
        }

        //5. Dato + disciplin
        System.out.println("Indtast kampdato: ");
        String dato = sc.nextLine();

        String disciplin = valgtTurnering.getDisciplinen(); //Turnering bestemmer disciplin

        //6. Opret kamp
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
        //Læs konkurrenceSpillere ud fra filer i mappen "konkurrenceSpillere"
        List<KonkurrenceSpillere> konkurrenceList = hentKonkurrenceSpillereFraMappe();

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

    //Læser alle konkurrencespillere ud fra filer i mappen "konkurrenceSpillere"
    private List<KonkurrenceSpillere> hentKonkurrenceSpillereFraMappe() {
        List<KonkurrenceSpillere> liste = new ArrayList<>();

        File mappe = FileUtil.OpretKonkurrenceMappe();   //Sikrer at mappen eksistere
        File[] filer = mappe.listFiles((dir, name) -> name.endsWith(".txt"));

        if (filer == null) {
            return liste;  //Tom liste hvis ingen filer
        }

        for (File f : filer) {

            //1. Find medlemsnummer ud fra filnavn
            String filnavn = f.getName();
            String udenExt = filnavn.replace(".txt", "");
            String[] deleNavn = udenExt.split("_");

            int medlemsNr = -1;
            if (deleNavn.length >= 1) {
                try {
                    medlemsNr = Integer.parseInt(deleNavn[0].trim());
                } catch (NumberFormatException e) {
                    System.out.println("Advarsel: Kunne ikke læse medlemsnummer i filnavn: " + filnavn);
                }
            }

            String hold = null;
            String disciplin = null;
            int rangering = 0;
            int resultat = 0;
            String dato = null;

            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                String linje;
                while ((linje = br.readLine()) != null) {
                    linje = linje.trim();

                    if (linje.startsWith("Hold: ")) {
                        hold = linje.substring("Hold: ".length()).trim();

                    } else if (linje.startsWith("Disciplin: ")) {
                        disciplin = linje.substring("Disciplin: ".length()).trim();

                    } else if (linje.startsWith("Rangering: ")) {
                        String tal = linje.substring("Rangering: ".length()).trim();
                        rangering = Integer.parseInt(tal);

                    } else if (linje.startsWith("Seneste kampresultat: ")) {
                        String tal = linje.substring("Seneste kampresultat: ".length()).trim();
                        resultat = Integer.parseInt(tal);

                    } else if (linje.startsWith("Dato: ")) {
                        dato = linje.substring("Dato: ".length()).trim();
                    }
                }

                //Kun hvis vi har de vigtigste data
                if (hold != null && disciplin != null && medlemsNr != -1) {
                    Spiller spiller = findSpillerVedMedlemsnummer(medlemsNr);
                    if (spiller != null) {
                        KonkurrenceSpillere ks = new KonkurrenceSpillere(spiller, disciplin, hold, rangering, resultat, dato);
                        liste.add(ks);
                    } else {
                        System.out.println("Advarsel: Kunne ikke finde spiller med medlemsnr. " + medlemsNr);
                    }
                }
            } catch (IOException | NumberFormatException e) {
                System.out.println("Fejl ved læsning af fil: " + f.getName() + " -> " + e.getMessage());
            }
        }
        return liste;
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

    public void opdaterResultat(){
        Scanner sc = new Scanner(System.in);

        //1. Hent alle konkurrencespillere ud fra deres filer
        List<KonkurrenceSpillere> konkurrenceList = hentKonkurrenceSpillereFraMappe();

        if (konkurrenceList == null || konkurrenceList.isEmpty()) {
            System.out.println("Der er ingen konkurrencespillere registreret endnu.");
            return;
        }

        //2. Vis liste over spillere med nuværende data
        System.out.println("=== Vælg spiller der skal opdateres ===");
        for (int i = 0; i < konkurrenceList.size(); i++) {
            KonkurrenceSpillere ks = konkurrenceList.get(i);
            Spiller s = ks.getSpiller();

            System.out.println((i + 1) + ". " + s.getNavn() +
                    " (medlemsnr.: " + s.getMedlemsNummer() + ")" +
                    " | Hold: " + ks.getHold() +
                    " | Disciplin: " + ks.getDisciplin() +
                    " | Seneste resultat: " + ks.getResultat());
        }

        System.out.println("Vælg nummer på spiller: ");
        int valg = Integer.parseInt(sc.nextLine());

        if (valg < 1 || valg > konkurrenceList.size()) {
            System.out.println("Ugyldigt valg.");
            return;
        }

        KonkurrenceSpillere valgt = konkurrenceList.get(valg - 1);
        Spiller spiller = valgt.getSpiller();

        //3. Spørg efter nye værdier (tomt input = behold gammel værdi)
        System.out.println("Indtast nyt resultat for " + spiller.getNavn() + " (tal): ");
        int nytResultat = Integer.parseInt(sc.nextLine());

        //4. Spørg efter ny dato
        System.out.println("Indtast ny dato for resultat: ");
        String nyDato = sc.nextLine();

        //Valider formatet
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        try {
            LocalDate.parse(nyDato, fmt);   //Kaster fejl hvis format er forkert
        } catch (Exception e) {
            System.out.println("Ugyldigt format. Brug dd-MM-yyyy");
            return;
        }

        //5. Opdater i objekt (så top 5, der bruger ks.getResultat(), også ser det)
        valgt.setResultat(nytResultat);
        valgt.setDato(nyDato);

        //6. Skriv ændringen til spilleren konkurrencefil
        FileUtil.opdaterSpillerResultat(spiller, nytResultat, nyDato);

        System.out.println("Resultat opdateret for " + spiller.getNavn() + ".");
    }

}
