import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.LocalTime;
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

    private KonkurrenceSpillere p;

    public Coach() {} //Default Constructor

    private String brugernavn;
    private String password;


    //Constructor for Coach
    public Coach(String navn, boolean medlemskab, String foedselsdag, String mail,
                 String brugernavn, String password) {
        super(navn, medlemskab, foedselsdag, mail);
        this.brugernavn = brugernavn;
        this.password = password;
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

    @Override
    public void sluk() {
        System.exit(0);
    }

                                ///MENU
    @Override
    public void menu() {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Menu ==========================");
        System.out.println("Mulige kommandoer:");
        System.out.println("    opret k - Opretter en kamp i en turnering");
        System.out.println("    top5 - Viser top 5 inden for hver disciplin");
        System.out.println("    logud - Logger ud af bruger");
        System.out.println("    sluk - Slukker for systemet");

        String input = sc.nextLine();

        //Opret kamp
        if (input.equalsIgnoreCase("Opret k") || input.equalsIgnoreCase("pret k") || input.equalsIgnoreCase("Oret k") || input.equalsIgnoreCase("Opet k") || input.equalsIgnoreCase("Oprt k") || input.equalsIgnoreCase("Opre k") || input.equalsIgnoreCase("Opretk") || input.equalsIgnoreCase("Opret kamp")|| input.equalsIgnoreCase("Opret kam") || input.equalsIgnoreCase("Opret kmp") || input.equalsIgnoreCase("Opret kap") || input.equalsIgnoreCase("Opret kam")) {
            opretKamp();

        //Se top 5 spillere
        } else if (input.equalsIgnoreCase("Top5") || input.equalsIgnoreCase("op5") || input.equalsIgnoreCase("Tp5") || input.equalsIgnoreCase("To5") || input.equalsIgnoreCase("Top") || input.equalsIgnoreCase("Top 5") || input.equalsIgnoreCase("op 5") || input.equalsIgnoreCase("Tp 5") || input.equalsIgnoreCase("To 5")) {
            topFem();

        //Logud
        } else if (input.equalsIgnoreCase("logud") || input.equalsIgnoreCase("ogud") || input.equalsIgnoreCase("lgud") || input.equalsIgnoreCase("loud") || input.equalsIgnoreCase("logd") || input.equalsIgnoreCase("logu")) {
            logud();

        } else if (input.equalsIgnoreCase("sluk") || input.equalsIgnoreCase("luk") || input.equalsIgnoreCase("suk") || input.equalsIgnoreCase("slk") || input.equalsIgnoreCase("slu")) {
            sluk();

        //Forkert input
        } else {
            System.out.println("Genkender ikke denne kommando. Prøv igen");
        }
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

                                ///OPRET KAMP
    public void opretKamp() {
        Scanner sc = new Scanner(System.in);

        List<Turnering> turneringer = FileUtil.laesTurneringerFraFil();

        if (turneringer == null) {
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
            System.out.println(t.getTurneringsNummer() + ". " + t.getTurneringsNavn() + " (" +
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

        System.out.println("Indtast vinder: (1)" + spiller1.getNavn() + " or (2)" + spiller2.getNavn());
        int vinderValg = sc.nextInt();
        Spiller vinderNr = null;
        if (vinderValg == 1) {
            vinderNr = spiller1;
        } else if (vinderValg == 2) {
            vinderNr = spiller2;
        } else {
            System.out.println("Ikke gyldig vinder.");
        }

        String disciplin = valgtTurnering.getDisciplinen(); //Turnering bestemmer disciplin

        //6. Opret kamp
        Kamp kamp = new Kamp(spiller1, spiller2, dato, disciplin, valgtTurnering, vinderNr.getNavn());
        File fil = new File("kamp.txt");
        String kampTekst = spiller1.getMedlemsNummer() + "_" + spiller2.getMedlemsNummer() + "_" + dato + "_" + disciplin + "_" + valgtTurnering.getTurneringsNavn() + "_" + vinderNr.getNavn();
        FileUtil.appendTilFil(fil, kampTekst);

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

        topFemForDisciplinOgHold("singles", "Junior", konkurrenceList);
        topFemForDisciplinOgHold("singles", "Senior", konkurrenceList);

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

                    } else if (linje.startsWith("Antal kampe vundet: ")) {
                        String tal = linje.substring("Antal kampe vundet: ".length()).trim();
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
        List<KonkurrenceSpillere> filter = new ArrayList<>(); //Filtrer spillere efter disciplin og hold
        for (KonkurrenceSpillere ks : alle) {
            if (ks.getDisciplin().equalsIgnoreCase(disciplin) && ks.getHold().equalsIgnoreCase(hold)) {
                filter.add(ks);
            }
        }

        //Sorter efter rangering (laveste først)
        filter.sort((k1, k2) -> Integer.compare(k1.getRangering(), k2.getRangering()));
        System.out.println("=== Top 5 - " + disciplin + " (" + hold + ") ===");

        if (filter.isEmpty()) {
            System.out.println("Ingen spillere i denne kategori endnu. \n");
            return;
        }

        //Print top 5
        for (int i = 0; i < Math.min(5, filter.size()); i++) {
            KonkurrenceSpillere ks = filter.get(i);
            System.out.println((i + 1) + ". " + ks.getSpiller().getNavn() +
                    " | Rangering: " + ks.getRangering() +
                    " | Seneste resultat: " + ks.getResultat());
        }
        if (filter.isEmpty()) {
            System.out.println("Ingen spillere i denne kategori endnu.");
        }
        System.out.println();
    }

    public void opdaterResultat(){
        //1. Hent alle konkurrencespillere ud fra deres filer
        List<KonkurrenceSpillere> konkurrenceList = hentKonkurrenceSpillereFraMappe();
        List<Kamp> kampList = FileUtil.laesKampFraFil();

        if (konkurrenceList == null || konkurrenceList.isEmpty()) {
            System.out.println("Der er ingen konkurrencespillere registreret endnu.");
            return;
        }

        for (KonkurrenceSpillere ks : konkurrenceList) {
            int nytResultat = 0;
            for (Kamp k : kampList) {
                if (k.getResultat().equals(ks.getSpiller().getNavn())) {
                    nytResultat++;
                }
            }
            FileUtil.opdaterSpillerResultat(ks.getSpiller(),nytResultat, String.valueOf(LocalDate.now()));
        }
    }

    public void opdaterRangering() throws IOException {
        List<KonkurrenceSpillere> konkurrenceList = hentKonkurrenceSpillereFraMappe();

        if (konkurrenceList == null || konkurrenceList.isEmpty()) {
            System.out.println("Der er ingen konkurrencespillere registreret endnu.");
            return;
        }

        konkurrenceList.sort((a, b) -> Integer.compare(b.getResultat(), a.getResultat()));

        for (int i = 0; i < konkurrenceList.size(); i++) {
            KonkurrenceSpillere ks = konkurrenceList.get(i);
            int nyRangering = i + 1;

            File mappe = FileUtil.OpretKonkurrenceMappe();

            String filnavn =
                    ks.getSpiller().getMedlemsNummer() + "_" +
                            ks.getSpiller().getNavn().replace(" ", "") + ".txt";


            File spillerFil = new File(mappe, filnavn);

            List<String> linjer = Files.readAllLines(spillerFil.toPath());
            List<String> opdateret = new ArrayList<>();

            for (String linje : linjer) {
                if (linje.startsWith("Rangering: ")) {
                    opdateret.add("Rangering: " + nyRangering);
                } else {
                    opdateret.add(linje);
                }
            }

            Files.write(spillerFil.toPath(), opdateret);
        }
    }
}
