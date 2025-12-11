import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/*
FileUtil har ansvaret for at:
    - Læse og skrive data til tekstfiler (medlem.txt, personale.txt, turneringer.txt m.fl.)
    - Opbygge Medlem-, Spiller-, Turnering- og Personale-objekter ud fra filernes indhold
    - Ændre eksisterende data i filer (fx betaling, medlemsoplysninger, personaleoplysninger)
    - Oprette mapper og filer til konkurrencespillere ved behov
    - Sikre korrekt formatering og struktur af alle data, der gemmes i systemet
*/

public class FileUtil {

    public FileUtil() {} //Default Constructor


    //Tilføj en linje til en angivet fil --- (Filens path, String der skal tilføjes)
    public static void appendTilFil(File file, String linje) {                      //Append en linje til dagens fil
        try (FileWriter myWriter = new FileWriter(file, true)) {
            myWriter.write(linje + System.lineSeparator());

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void opdaterBetalingTilFil (int medlemsNummer, int nyRestance) {
        try {
            File fil = new File ("betaling.txt");

            BufferedReader reader = new BufferedReader(new FileReader(fil));

            String harBetalt = "";

            if (nyRestance <= 0) {
                harBetalt = "true";
            } else if (nyRestance > 0 ) {
                harBetalt = "false";
            }

            ArrayList<String> linjer = new ArrayList<>();
            String nyLinje = medlemsNummer + "_" + harBetalt + "_" + nyRestance;
            String linje = "sdf";


            while ((linje = reader.readLine()) != null) {
                String[] dele = linje.split("_");
                if (String.valueOf(medlemsNummer).equals(dele[0])) {
                    linjer.add(nyLinje);
                    System.out.println("opdaterer restance");
                    continue;
                }
                linjer.add(linje);
            }
            reader.close();

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fil))) {
                for (String l : linjer) {
                    writer.write(l);
                    writer.newLine();
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }}

    public static void aendreDataIFil(File file, String sogning, int dataPunkt, String aendring) {  //Ændre i en angivet fil --- (Filens path, linjen vi søger, dataets position, ændringen til dataet)

        String aendringString = aendring;                                                           //Det input den ønsker at ændre eller indsætte
        int dataIndex = dataPunkt - 1;                                                              //Laver data punkt om til et index

        List<String> linjer = new ArrayList<>();                                                    //Arrayliste vi gæmmer linjerne på da vi har ændret dem, og senere skriver tilbage til filen

        try (BufferedReader aflaeser = new BufferedReader(new FileReader(file))) {               //Læser fra angivet fil, //Læser alle linjerne i filen
            String linje;
            while ((linje = aflaeser.readLine()) != null) {                                       //Checker en bestemt linje
                if (linje.contains(sogning)) {                                                  //Er søgningen på den linje?

                    String[] dele = linje.split("_");                                         //Splitter linjen i dele med "_" som deler
                    dele[dataIndex] = aendringString;                                               //Ændrer dataet
                    linje = String.join("_", dele);                                         //Sætter linjen sammen igen med ændring
                }
                linjer.add(linje);                                                                  //Tilføjer alle linjerne til arraylisten
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        try (BufferedWriter skriver = new BufferedWriter(new FileWriter(file))) {    //Skriver alle linjerne der er inde i arraylisten tilbage til filen
            for (String linje : linjer) {
                skriver.write(linje);
                skriver.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String laesDataFraFil(File file, String sogning, int dataPunkt) {     //Returner et specifikt data fra en søgt linje som string --- (Filens path, linjen vi søger, dataets position)
        int dataIndex = dataPunkt - 1;                                                              //Laver data punkt om til et index
        String data = "";

        try (BufferedReader aflaeser = new BufferedReader(new FileReader(file))) {
            String linje;
            while ((linje = aflaeser.readLine()) != null) {
                if (linje.contains(sogning)) {                                                  //Er søgningen på den linje?

                    String[] dele = linje.split("_");                                         //Splitter linjen i dele med "_" som deler
                    data = dele[dataIndex];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }


    public static List<Object> laesPersonaleFraFil() {

        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String deler = "_";
        String linje = "";
        String personaleFil = "personale.txt";
        List<Object> personaleListe = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(personaleFil))) {
            while ((linje = reader.readLine()) != null) {
                int lineNumber = 0;
                lineNumber++;
                String[] data = linje.split(deler);
                String position = data[0];

                if (position.equals("formand")) {
                    String navn = data[1];
                    boolean medlemskab = Boolean.parseBoolean(data[2]);
                    String foedselsdag = data[3];
                    String mail = data[4];
                    String brugernavn = data[5];
                    String password = data[6];

                    Formand personale = new Formand(navn, medlemskab, foedselsdag, mail, brugernavn, password);
                    personaleListe.add(personale);

                } else if (position.equals("kasserer")) {
                    String navn = data[1];
                    boolean medlemskab = Boolean.parseBoolean(data[2]);
                    String foedselsdag = data[3];
                    String mail = data[4];
                    String brugernavn = data[5];
                    String password = data[6];

                    Kasserer personale = new Kasserer(navn, medlemskab, foedselsdag, mail, brugernavn, password);
                    personaleListe.add(personale);

                } else if (position.equals("coach")) {
                    String navn = data[1];
                    boolean medlemskab = Boolean.parseBoolean(data[2]);
                    String foedselsdag = data[3];
                    String mail = data[4];
                    String brugernavn = data[5];
                    String password = data[6];

                    Coach personale = new Coach(navn, medlemskab, foedselsdag, mail, brugernavn, password);
                    personaleListe.add(personale);

                } else {
                    System.out.println("Error: Kunne ikke genkende personale på linje" + lineNumber + " i fil personale.txt");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return personaleListe;
    }

    public static List<Betaling> laesBetalingFraFil() {
        List<Betaling> betalingsListe = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("betaling.txt"))) {
            String linje;


            while ((linje = br.readLine()) != null) {
                String[] dele = linje.split("_");

                int medlemsNummer = Integer.parseInt(dele[0]);
                boolean betalt = Boolean.parseBoolean(dele[1]);
                int restance = Integer.parseInt(dele[2]);

                Betaling betaling = new Betaling(medlemsNummer, betalt, restance);
                betalingsListe.add(betaling);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return betalingsListe;
    }

    public static List<Medlem> laesMedlemFraFil() {

        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String deler = "_";
        String linje = "";
        String medlemFil = "medlem.txt";
        List<Spiller> spillerListe = new ArrayList<>();
        List<Medlem> medlemsListe = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(medlemFil))) {
            while ((linje = reader.readLine()) != null) {
                String[] data = linje.split(deler);
                String navn = data[0];
                int medlemsNummer = Integer.parseInt(data[1]);
                boolean medlemskab = Boolean.parseBoolean(data[2]);
                String foedselsdag = data[3];
                String mail = data[4];
                String spillerType = data[5];
                boolean betaling = Boolean.parseBoolean(data[6]);

                String hold = "Ukendt";
                if (data.length > 7) {
                    hold = data[7];
                }

                Spiller medlem = new Spiller(navn, medlemsNummer, medlemskab, foedselsdag, mail, spillerType, betaling);
                medlem.setHold(hold);
                medlemsListe.add(medlem);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return medlemsListe;
    }


    public static File OpretKonkurrenceMappe() {                                    //Opretter mappe objekt
        File mappe = new File("konkurrenceSpillere");

        if (!mappe.exists()) {                                                               //Tjekker om mappen allerede findes
            boolean oprettet = mappe.mkdirs();                                               //Forsøg at oprette mappen
            if (oprettet) {                                                                 //Håndterer oprettelsen af mappen
                System.out.println("Mappen " + mappe.getPath() + " er oprettet");
            } else {
                System.out.println("kunne ikke oprette mappen" + mappe.getPath());
            }
        } else {                                                                            //Hvis mappen allerede findes
            System.out.println("mappen " + mappe.getPath() + " eksisterer allerede");
        }
        return mappe;                                                                       //Returnerer mappen uanset om den er blevet oprettet eller ej
    }

    public static File opretSpillerFil(Spiller spiller, KonkurrenceSpillere ks) {    //Medlem medlem, KonkurrenceSpillere ks) {
        File mappe = OpretKonkurrenceMappe();                                               //Sikrer at mappen eksisterer

        String filnavn = spiller.getMedlemsNummer() + "_" + spiller.getNavn().replace(" ", "") + ".txt";       //Henter spillernavn og tilføjer ".txt" til filnavnvet

        File spillerFil = new File(mappe, filnavn);                                         //Opretter fil objektet som peger på filen i mappen

        try {                                                                               //en try-blok fordi filoperationer kan kaste IOException.
            if (!spillerFil.exists()) {                                                     //Tjekker om filen allerede eksisterer
                spillerFil.createNewFile();                                                 //Forsøger at oprette filen
                StartDataTilFil(spillerFil, ks);
                System.out.println("Filen " + spillerFil.getPath() + " er oprettet");
            }  //Printer i consollen når den er oprettet

        } catch (IOException e) {
            System.out.println("Kunne ikke oprette spillerfil: " + e.getMessage());        //Hvis der er fejl så printer den sætningen ud
        }

        return spillerFil;
    }


    private static void StartDataTilFil(File fil, KonkurrenceSpillere ks) { //ks) {    //Åbner en FileWriter i en try-with-resources blok. false betyder overskriv filens eksisterende indhold (ikke append). Try-with-resources sikrer at fw automatisk lukkes efter blokken, også hvis en undtagelse sker.
        try (FileWriter fw = new FileWriter(fil, false)) {
            fw.write("Hold: " + ks.getHold() + "\n");                               // junior/senior
            fw.write("Disciplin: " + ks.getDisciplin() + "\n");
            fw.write("Rangering: " + ks.getRangering() + "\n");
            fw.write("Seneste kampresultat: " + ks.getResultat() + "\n");
            fw.write("Dato: " + ks.getDato() + "\n");

        } catch (IOException e) {
            System.out.println("Fejl under skrivning til fil: " + e.getMessage());
        }
    }


    public static List<Turnering> laesTurneringerFraFil() {

        String filnavn = "turneringer.txt";
        List<Turnering> turneringsListe = new ArrayList<>();
        File fil = new File(filnavn);

        if (!fil.exists() || fil.length() == 0) {
            return turneringsListe; //Tom liste hvis ingen turneringer
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(fil))) {
            String linje;
            String deler = "_";

            while ((linje = reader.readLine()) !=null) {
                String trimmed = linje.trim();
                if (trimmed.isEmpty()) continue;

                String[] data = trimmed.split(deler);
                if (data.length < 4) {
                    System.out.println("Advarsel: forkert format i turneringer.txt: " + trimmed);
                    continue;
                }

                int nummer = Integer.parseInt(data[0].trim());
                String navn = data[1].trim();
                String disciplin = data[2].trim();
                String dato = data[3].trim();

                Turnering t = new Turnering(nummer, navn, disciplin, dato);
                turneringsListe.add(t);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return turneringsListe;
    }

    public static void opdaterSpillerResultat(Spiller spiller, int nytResultat, String nyDato) {
        File mappe = OpretKonkurrenceMappe();   //Sikrer at mappen findes

        String filnavn = spiller.getMedlemsNummer() + "_" +
                spiller.getNavn().replace(" ","") + ".txt";

        File spillerFil = new File(mappe, filnavn);

        if (!spillerFil.exists()) {
            System.out.println("Kunne ikke finde konkurrencefil for spiller: " + spiller.getNavn());
            return;
        }

        List<String> linjer = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(spillerFil))) {
            String linje;
            while ((linje = br.readLine()) != null) {
                String trimmed = linje.trim();

                if (trimmed.startsWith("Seneste kampresultat:")) {
                    linje = "Seneste kampresultat: " + nytResultat;
                } else if (trimmed.startsWith("Dato:")) {
                    linje = "Dato: " + nyDato;
                }
                linjer.add(linje);
            }
        } catch (IOException e) {
                System.out.println("Fejl ved læsning af spillerfil: " + e.getMessage());
                return;
        }

            //Skriv de opdaterede linjer tilbage
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(spillerFil, false))) {
                for (String l : linjer) {
                    bw.write(l);
                    bw.newLine();
                }
        } catch (IOException e) {
            System.out.println("Fejl ved skrivning til spillerfil: " + e.getMessage());
            return;
        }

        System.out.println("Resultat + dato opdateret for " + spiller.getNavn());
    }
}







