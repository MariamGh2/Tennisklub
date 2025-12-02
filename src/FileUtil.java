import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    public FileUtil(){} //Default Constructor


    //Tilføj en linje til en angivet fil --- (Filens path, String der skal tilføjes)
    public static void appendTilFil(File file, String linje) {                                                             //Append en linje til dagens fil
        try (FileWriter myWriter = new FileWriter(file, true)) {
            myWriter.write(linje + System.lineSeparator());

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


    //Ændre i en angivet fil --- (Filens path, linjen vi søger, dataets position, ændringen til dataet)
    public static void aendreDataIFil(File file, String sogning, int dataPunkt, String aendring) {

        String aendringString = aendring;                                                           //Det input den ønsker at ændre eller indsætte
        int dataIndex = dataPunkt - 1;                                                              //Laver data punkt om til et index

        List<String> linjer = new ArrayList<>();                                                    //Arrayliste vi gæmmer linjerne på da vi har ændret dem, og senere skriver tilbage til filen

        //Læser alle linjerne i filen
        try (BufferedReader aflaeser = new BufferedReader(new FileReader(file))) {               //Læser fra angivet fil
            String linje;
            while ((linje = aflaeser.readLine()) != null) {                                         //Checker en bestemt linje
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

        //Skriver alle linjerne der er inde i arraylisten tilbage til filen
        try (BufferedWriter skriver = new BufferedWriter(new FileWriter(file))) {
            for (String linje : linjer) {
                skriver.write(linje);
                skriver.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //Returner et specifikt data fra en søgt linje som string --- (Filens path, linjen vi søger, dataets position)
    public static String laesDataFraFil(File file, String sogning, int dataPunkt) {

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





    /// Opretter konkurrence spillerens personlige fil i KonkurrenceSpillereNavne mappen
    public static void opretSpillerFil(String spillerNavn) {

        int medlemsnummer = Medlemsnummer.getNextMedlemsnummer();             //Henter medlemsnummer

        Path mappe = Paths.get("KonkurrenceSpillereNavne");              //Stien til mappen "KonkurrenceSpillereNavne" til txt filerne

        String filNavn = spillerNavn + "_" + medlemsnummer + ".txt";          //Opretter en filsti med navnet: spillerNavn.txt
        Path filSti = mappe.resolve(filNavn);

        //Opretter tom fil
        try {
            Files.createFile(filSti);
            System.out.println("Tom fil oprettet: " + filSti);
        } catch (IOException e) {
            System.out.println("Kunne ikke oprette fil: " + e.getMessage());
        }
    }



    public void updateToFile(){}

    public void readFromFile(){}

    public void appendToFile(){}
}
