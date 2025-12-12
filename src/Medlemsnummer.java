import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
Klassen bruges til at:
    - Generere nye medlemsnumre til medlemmer
    - Læse alle eksisterende medlemsnumre fra medlem.txt
    - Finde det mindste ledige nummer (genbruge huller i rækken)
    - Sikre at medlemsnumre altid er unikke og i stigende orden uden unødige spring
*/

public class Medlemsnummer {

    public Medlemsnummer(){}   //Default constructor

    //Her defineres en sti (path) til filen, hvor vi gemmer de seneste medlemsnumre
    //Filen kommer til at ligge samme sted som programmet kører fra
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

    public static void sorterFilEfterMedlemsnummer(){
        try {
            if (!Files.exists(FIL)) {
                return; //intet at sortere
            }

            List<String> linjer = Files.readAllLines(FIL);   //Læser alle linjer

            linjer.removeIf(l -> l.trim().isEmpty());     //trim() fjerner mellemrum og skjulte tegn
            //isEmpthy() tjekker om den er tom, hvis ja, fjernes linjen

            linjer.sort((l1, l2) -> {          //Sorter listens linjer ved at sammenligne to linjer ad gangen, kaldet l1 og l2

                int n1 = hentMedlemsnummerFraLinje(l1);    //Sorteringen læser to linjer, finder medlemsnummeret i hver linje
                int n2 = hentMedlemsnummerFraLinje(l2);    // og bruger Integer.compare til at bestemme hvilken linje der skal stå først.
                return Integer.compare(n1,n2);             //På den måde sorteres hele filen korrekt efter medlemsnumre.
            });                                            /*Sorterer linjerne efter medlemsnummer.
                                                           Tuborg {} hører til lambdaens krop: (l1, l2) -> { ... }
                                                           Parentesen ) hører til funktionskaldet sort( ... )
                                                           Derfor slutter udtrykket med });*/

            Files.write(FIL, linjer);           //Efter sorteringen overskrives hele filen med den sorterede udgave af linjerne

        } catch (IOException e) {
            throw new RuntimeException("Fejl ved sortering af medlem.txt");
        }
    }

    private static int hentMedlemsnummerFraLinje(String linje){     //Hjælpemetode til at finde medlemsnr. i en linje
        String trimmed = linje.trim();
        if (trimmed.isEmpty()) return Integer.MAX_VALUE;

        String[] dele = trimmed.split("_");
        if (dele.length < 2) return Integer.MAX_VALUE;   //Hvis der ikke er noget felt nr. 2

        try {
            return Integer.parseInt(dele[1].trim());
        } catch (NumberFormatException e) {
            return Integer.MAX_VALUE;       //Hvis format er forkert, skub den linje ned i bunden
        }
    }
}