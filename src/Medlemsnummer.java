import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;


//Klassen har kun ansvaret for at finde næste medlemsnummer
//Den læser og skriver til en fil, så vi kan huske sidste nummer mellemhver gang programmet starter
public class Medlemsnummer {


    public Medlemsnummer() {
    } //Default constructor


    //Her defineres en sti (path) til filen, hvor vi gemmer de seneste medlemsnumre
    //Filen kommer til at ligge samme sted som programmet kører fra
    private static final Path FIL = Path.of("medlem.txt");

    /*Denne metode kaldes udefra fra Medlemsklassen
    Den sørger for at:
    1. Læse sidste nummer fra filen
    2. Lægge 1 til
    3. Skrive det nye nummer tilbage i filen
    4. Returnere det nye nummer
     */
    public static int hentNytMedlemsnummer() {
        int sidste = laesSidsteNummer();     //Læs sidste nummer
        int nyt = sidste + 1;
        System.out.println("Sidste medlemsnr: " + sidste + " Nyt: " + nyt);    //DEBUG
        return nyt;                          //Returnere tallet til den der kalder metoden
    }

    //Denne metode læser det sidste medlemsnummer fra filen
    private static int laesSidsteNummer() {
        try {
            //Hvis filen ikke findes, opret og returner 0
            if (!Files.exists(FIL)) {
                return 0;
            }

            //Læs alle linjer i filen
            List<String> linjer = Files.readAllLines(FIL);
            if (linjer.isEmpty()) {
                return 0;
            }

            //Find sidste ikke-tomme linje
            String sidsteLinje = "";
            for (int i = linjer.size() - 1; i >= 0; i--) {
                String linje = linjer.get(i).trim();
                if (!linjer.isEmpty()) {
                    sidsteLinje = linje;
                    break;
                }
            }
            if (sidsteLinje.isEmpty()) {
                return 0;
            }

            System.out.println("Sidste linje i medlem.txt: " + sidsteLinje); //DEBUG

            //Split linjen i dele
            String[] dele = sidsteLinje.split(";");

            if (dele.length < 2) {
                System.out.println("Kunne ikke finde medlemsnummer i linjen - format forkert?"); //DEBUG
                //linjen er ikke korrekt formateret -> start på 0
                return 0;
            }

            //andet felt = medlemsnummer
            String medlemnummerTekst = (dele[1].trim());
            return Integer.parseInt(medlemnummerTekst);

        } catch (IOException e) {
            throw new RuntimeException("Fejl ved læsning a medlem.txt");
        } catch (NumberFormatException e) {
            System.out.println("Kunne ikke parse medlemsnummer - format forkert? Starter fra 0"); //DEBUG
            return 0;
        }
    }

        //Denne metode skriver et nyt nummer nede i filen
        private static void tilfoejNummerTilFil(int nummer){
        try {
            //Tilføjer tallet som ny linje (append)
            Files.writeString(FIL, nummer + "\n", StandardOpenOption.APPEND);
        } catch (IOException e){
            //Hvis der sker en fejl, stopper vi programmet med en fejlbesked
            throw new RuntimeException ("Fejl ved skrivning til medlem.txt");
        }
    }
}