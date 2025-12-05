import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/*FileUtil har ansvaret for at læse medlemmer fra filen og bygge Medlem-objekt, andre ændringer til txt filerne samt
at oprette mappe og filer til konkurrencespillere
 */

public class FileUtil {

    public FileUtil() {
    } //Default Constructor


    //Tilføj en linje til en angivet fil --- (Filens path, String der skal tilføjes)
    public static void appendTilFil(File file, String linje) {                      //Append en linje til dagens fil
        try (FileWriter myWriter = new FileWriter(file, true)) {
            myWriter.write(linje + System.lineSeparator());

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


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
                String navn = data[1];
                int medlemsNummer = Integer.parseInt(data[2]);
                boolean medlemskab = Boolean.parseBoolean(data[3]);
                String foedselsdag = data[4];
                String mail = data[5];
                String spillerType = data[6];

                Spiller medlem = new Spiller(navn, medlemsNummer, medlemskab, foedselsdag, mail, spillerType);
                medlemsListe.add(medlem);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return medlemsListe;
    }

/*
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

    public static File opretSpillerFil(Spiller spiller //Medlem medlem, KonkurrenceSpillere ks) {
        File mappe = OpretKonkurrenceMappe();                                               //Sikrer at mappen eksisterer

        String filnavn = spiller.getNavn().replace(" ", "") + ".txt";       //Henter spillernavn og tilføjer ".txt" til filnavnvet

        File spillerFil = new File(mappe, filnavn);                                         //Opretter fil objektet som peger på filen i mappen

        try {                                                                               //en try-blok fordi filoperationer kan kaste IOException.
            if (!spillerFil.exists()) {                                                     //Tjekker om filen allerede eksisterer
                spillerFil.createNewFile();                                                 //Forsøger at oprette filen
                //startDataTilFil(spillerFil, ks);
                System.out.println("Filen " + spillerFil.getPath() + " er oprettet");       //Printer i consollen når den er oprettet

        } catch (IOException e) {
            System.out.println("Kunne ikke oprette spillerfil: " + e.getMessage());        //Hvis der er fejl så printer den sætningen ud
        }

        return spillerFil;
    }

    private static void StartDataTilFil(File fil, KonkurrenceSpillere spiller //ks) {    //Åbner en FileWriter i en try-with-resources blok. false betyder overskriv filens eksisterende indhold (ikke append). Try-with-resources sikrer at fw automatisk lukkes efter blokken, også hvis en undtagelse sker.
        try (FileWriter fw = new FileWriter(fil, false)) {
            fw.write("Hold: " + spiller.getHold() + "\n");                               // junior/senior
            fw.write("Disciplin: " + spiller.getDisciplin() + "\n");
            fw.write("Rangering: " + spiller.getRangering() + "\n");
            fw.write("Seneste kampresultat: " + spiller.getResultat() + "\n");
            fw.write("Dato: " + ks.getDato() + "\n");

        } catch (IOException e) {
            System.out.println("Fejl under skrivning til fil: " + e.getMessage());
        }
    }



 */


}



