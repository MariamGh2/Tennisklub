import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

//FileUtil har snavret for at læse medlemmer fra filen og bygge Medlem-objekt
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

/*
    public static List<Object> laesMedlemFraFil(String objekt) {

        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String deler = "_";
        String linje = "";
        String medlemFil = "medlem.txt";
        List<Spiller> spillerListe = new ArrayList<>();
        List<Medlem> medlemsListe = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(medlemFil))){
            while((linje = reader.readLine()) != null) {
                String[] data = linje.split(deler);
                if (objekt.equals("medlem")) {
                    String
                } else if (objekt.equals("konkurrencespiller")) {

                }

                Spiller medlem = new Spiller(navn, medlemsNummer, medlemskab, foedselsdag, mail, spillerType);
                medlemsListe.add(medlem);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

 */
}

