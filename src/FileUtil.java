import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    public FileUtil(){} //Default Constructor


    //Tilføj en linje til en angivet fil
    public static void appendTilFil(File file, String linje) {                                                             //Append en linje til dagens fil
        try (FileWriter myWriter = new FileWriter(file, true)) {
            myWriter.write(linje + System.lineSeparator());
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


    //Ændre i en angivet fil
    public static void SkrivIFil(File file, String type, String sogning, String aendring) {
        File filNavn = file;                //Hvilken File der skal ændres (fra parameter)
        String dataSogning = sogning;       //Det data den leder efter
        String aendringString = aendring;   //Det input den ønsker at ændre eller indsætte

        List<String> linjer = new ArrayList<>();

        //Læser alle linjerne i filen
        try (BufferedReader aflaeser = new BufferedReader(new FileReader(filNavn))) { //Læser fra angivet fil
            String linje;
            while ((linje = aflaeser.readLine()) != null) {     //Checker en bestemt linje
                if (linje.contains(dataSogning)) {              //Er søgningen på den linje?

                    String[] dele = linje.split("_");
                    //String[] hvem = dele[0];    ///SOMETHING FUNKY HERE (ikke angivet hvor mange dele)

                    if (type.equalsIgnoreCase("")) {
                        
                    } else if (type.equalsIgnoreCase("")) {

                    } else if (type.equalsIgnoreCase("")) {

                    }
                }
                //Tilføjer alle linjerne til arraylisten
                linjer.add(linje);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        //Skriver alle linjerne inde i arraylisten tilbage til filen
        try (BufferedWriter skriver = new BufferedWriter(new FileWriter(filNavn))) {
            for (String linje : linjer) {
                skriver.write(linje);
                skriver.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
