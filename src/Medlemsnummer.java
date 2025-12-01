import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Medlemsnummer {


    public Medlemsnummer(){} //Default constructor


   private static Path filSti = Paths.get("KonkurrenceSpillereNavne/medlemsnummer.txt");

    public static int getNextMedlemsnummer() {                               //Hent næste medlemsnummer og gem det
        try {
            //Hvis filen ikke findes, opret den og start på 1
            if( !Files.exists(filSti)) {
                Files.createDirectories(filSti.getParent());
                Files.writeString(filSti, "1");
                return 1;
            }
            int nr = Integer.parseInt(Files.readString(filSti).trim());     //Læs nuværende nummer fra filen

            int next = nr + 1;                                               //Udregn næste nummer
            Files.writeString(filSti, String.valueOf(next));           //Gem næste nummer i filen

            return nr;                                                        //Returnere det gamle nr.

        } catch (IOException e){
        System.out.println("Fejl i medlemsnummer: " + e.getMessage());
        return 4;
        }
    }

}
