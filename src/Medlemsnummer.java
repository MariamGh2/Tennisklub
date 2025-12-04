import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* Klassen har ansvaret for at finde næste medlemsnummer
Den læser alle eksisterende medlemmer fra medlem.txt
og finder det mindste ledige tal (genbruger huller)
 */
public class Medlemsnummer {

    public Medlemsnummer(){}   //Default constructor


    private static final Path FIL = Path.of("medlem.txt");     //Sti til filen hvor ALLE medlemmerne står

    public static int hentNytMedlemsnummer() {                       //Denne metode kaldes fra Medlem-klassen
        return findMindsteLedigeNummer();                           //Den finder næste ledige medlemsnummer og returnere det
    }

    private static int findMindsteLedigeNummer() {                  //Finder det mindste ledige medlemsnummer, fx hvis numrene er 1,2,4,5 -> returnere 3
        try {
            if (!Files.exists(FIL)) {                                    //Hvis filen ikke findes -> ingen medlemmer endnu -> start på 1
                return 1;
            }
            List<String> linjer = Files.readAllLines(FIL);

            if (linjer.isEmpty()) {                                       //Hvis filen er tom -> start på 1
                return 1;
            }

            Set<Integer> brugteNumre = new HashSet<>();   //HashSet til at gemme alle medlemsnumre
                                                          //HashSet indeholder unikke værdier (ingen dubletter)
                                                          //Søger hurtigere end et Arraylist, derfor perfekt ift. at finde huller

            for (String linje : linjer) {                  //Gennemgå ALLE linjer i medlem.txt
                String trimmed = linje.trim();             //trimmed og trim er en metode som fjerner mellemrum, linjeskift og tabs
                if (trimmed.isEmpty()) continue;          //Tom linje (skip denne linje, den indeholder ikke data)

                String[] dele = trimmed.split("_");    //Format: navn_medlemsnr._fødelsdag_mail
                if (dele.length < 2) continue;

                try {
                    int nummer = Integer.parseInt(dele[1].trim());
                    brugteNumre.add(nummer);
                } catch (NumberFormatException e) {             //Linje med fejl ignoreres
                }
            }
            for (int kandidat = 1; kandidat <= brugteNumre.size() + 1; kandidat++){   //Find mindste ledige tal (kandidat) -> loop fra 1 og op
                if (!brugteNumre.contains(kandidat)){                                  //Kandidat er forslag -> kandidat til at være næste ledige nummer
                    return kandidat;
                }
            }
            return brugteNumre.size() + 1;    //fallback (burde aldrig bruges)
        } catch (IOException e){
            throw new RuntimeException("Fejl ved læsning af medlem.txt");    //RuntimeException bruges til at vise hvis der er en fejl
                                                                             //Stopper programmet, så fejlen ikke skjules
        }
    }
}