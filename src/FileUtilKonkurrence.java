import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtilKonkurrence {

    public FileUtilKonkurrence() {
    }

    //opretter mappe
    public static File OpretKonkurrenceMappe() {
        File mappe = new File("konkurrenceSpillere");

        //tjekker om mappen allerede findes
        if (!mappe.exists()) {
            //forsøg at oprette mappen
            boolean oprettet = mappe.mkdirs();

            //håndterer oprettelsen af mappen
            if (oprettet) {
                System.out.println("Mappen " + mappe.getPath() + " er oprettet");
            } else {
                System.out.println("kunne ikke oprette mappen" + mappe.getPath());
            }

        } else {
            //hvis mappen allerede findes
            System.out.println("mappen " + mappe.getPath() + " eksisterer allerede");
        }
        //returnerer mappen uanset om den er blevet oprettet eller ej
        return mappe;
    }

    /*public static File opretSpillerFil(KonkurrenceSpillere spiller) {
        File mappe = OpretKonkurrenceMappe();

        String filnavn = spiller.getNavn().replace(" ", "") + "txt";

        File spillerFil = new File(mappe, filnavn);

        try {
            if (!spillerFil.exists()) ;
            {
                spillerFil.createNewFile();
                System.out.println("Filen " + spillerFil.getPath() + " er oprettet");

                startDataTilFil(spillerFil, spiller);
            }

        } catch (IOException e) {
            System.out.println("Kunne ikke oprette spillerfil: " + e.getMessage());
        }

        return spillerFil;
    }

    private static void StartDataTilFil(File fil, KonkurrenceSpillere spiller) {
        try (FileWriter fw = new FileWriter(fil, false)) {
            fw.write("Navn: " + spiller.getNavn() + "\n");
            fw.write("Hold: " + spiller.getHold() + "\n");              // junior/senior
            fw.write("Disciplin: " + spiller.getDisciplin() + "\n");
            fw.write("Rangering: " + spiller.getRangering() + "\n");
            fw.write("Seneste kampresultat: " + spiller.getResultat() + "\n");

        } catch (IOException e) {
            System.out.println("Fejl under skrivning til fil: " + e.getMessage());
        }
    }

     */
}


