import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Medlemsnummer {                                               //Klassen har kun ansvaret for at finde næste medlemsnummer
                                                                           //Den læser og skriver til en fil, så vi kan huske sidste nummer mellemhver gang programmet starter


    public Medlemsnummer(){} //Default constructor

    //Her difineres en sti (path) til filen, hvor vi gemmer de seneste medlemsnumre
    //Filen kommer til at ligge samme sted som programmet kører fra
    private static final Path FIL = Path.of("medlem.txt");

    /*Denne metode kaldes udefra fra Medlemsklassen
    Den sørger for at:
    1. Læse sidste nummer fra filen
    2. Lægge 1 til
    3. Skrive det nye nummer tilbage i filen
    4. Returnere det nye nummer
     */
    public static int hentNytMedlemsnummer(){
        int sidste = laesSidsteNummer();     //Læs sidste nummer
        int nyt = sidste + 1;                //Læg 1 til
        skrivTilFil(nyt);                    //Gemmer tallet i filen
        return nyt;                          //Returnere tallet til den der kalder metoden
    }

    //Denne metode læser det sidste medlemsnummer fra filen
    private static int laesSidsteNummer(){
        try {
            //Først tjekker vi, om filen findes
            if (!Files.exists(FIL)){
                //Hvis den ikke findes, opretter vi den og skriver 0 i den
                //Det betyder at første medlemsnummer, vi laver, bliver 1
                Files.writeString(FIL, "0");
                return 0;
            }

            //Hvis filen findes, læser vi dens indhold som tekst
            String tekst = Files.readString(FIL).trim();    //trim() fjerner evt. mellemrum/linjeskift
            int tekst2 = Integer.parseInt(tekst);
            return tekst2;                 //Laver teksten om til et helt tal (int)

        } catch (IOException e) {
            //Hvis der sker en fejl ved fil-læsning, stopper vi programmet med en fejlbesked
            throw new RuntimeException("Fejl ved læsning af medlemsnummer.txt");
        }
    }

    //Denne metode skriver et nyt nummer nede i filen
    private static void skrivTilFil(int nummer){
        try {
            //Vi overskriver bare hele filens indhold med det nye tal
            Files.writeString(FIL, String.valueOf(nummer));
        } catch (IOException e){
            //Hvis der sker en fejl, stopper vi programmet med en fejlbesked
            throw new RuntimeException ("Fejl ved skrivning til medlemsnummer.txt");
        }
    }
}
