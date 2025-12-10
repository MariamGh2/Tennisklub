import javax.imageio.IIOException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/*
Klassen har ansvaret for:
    - At repræsentrere en turnering i klubben
    - At gemme turneringsoplysninger,
        - Turneringsnummer (ID)
        - Turneringsnavn
        - Disciplin
        - Dato for turnering
    - At gemme alle kamper, der hører til turneringen
    - At gemme turneringen i turneringer.txt samt indlæse turneringer fra fil.
 */


public class Turnering {

    public Turnering(){} //Default Constructor

    private static final File FIL = new File("turneringer.txt");

    private int turneringsNummer;
    private String turneringsNavnet;
    private String disciplinen;
    private String datoen;

    private List<Kamp> kampe = new ArrayList<>();    //Liste over kampe i denne turnering

    //OPRETTER ny turnering
    public Turnering(String turneringsNavn, String disciplin, String dato) {
        this.turneringsNummer = hentNytTurneringsNummer();
        this.turneringsNavnet = turneringsNavn;
        this.disciplinen = disciplin;
        this.datoen = dato;


        //Gem turneringen i fil: nummer_navn_disciplin_dato
        FileUtil.appendTilFil(FIL, turneringsNummer + "_" + turneringsNavn + "_" + disciplinen + "_" + datoen);
    }

    //LÆSER fra fil (fx i FileUtil.laesTurneringerFraFil)
    public Turnering(int turneringsNummer, String turneringsNavn, String disciplin, String dato){
        this.turneringsNummer = turneringsNummer;
        this.turneringsNavnet = turneringsNavn;
        this.disciplinen = disciplin;
        this.datoen = dato;
    }

    //Hjælpemetode til at finde næset turneringsnummer
    private static int hentNytTurneringsNummer() {
        if (!FIL.exists() || FIL.length() == 0) {
            return 1;
        }

        int max = 0;
        try {
            List<String> linjer = Files.readAllLines(FIL.toPath());
            for (String linje : linjer) {
                String trimmed = linje.trim();
                if (trimmed.isEmpty()) continue;

                String[] dele = trimmed.split("_");
                if (dele.length < 1) continue;

                try {
                    int nr = Integer.parseInt(dele[0].trim());   //Første felt = nummer
                    if (nr > max) {
                        max = nr;
                    }
                } catch (NumberFormatException e) {  //Ignorer linjer med fejl
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Kunne ikke læse turneringer.txt", e);
        }
        return max + 1;
    }

                                /// GETTERS
    public int getTurneringsNummer(){
        return turneringsNummer;
    }

    public String getTurnering(){
        return turneringsNavnet;
    }

    public String getDisciplinen(){
        return disciplinen;
    }

    public String getDatoen(){
        return datoen;
    }

    public void tilfoejKamp(Kamp kamp){   //Tilføj en eksisterende kamp til denne turnering
        kampe.add(kamp);
    }

    public Kamp opretKamp(Spiller s1, Spiller s2){  //Opret og tilføj en ny kamp til denne turnering
        Kamp kamp = new Kamp(s1, s2, datoen, disciplinen, this);  //Her bruges turneringens dato og disciplin, som default
        kampe.add(kamp);
        return kamp;
    }

    public void visKampe(){
        System.out.println("=== Kampe i turnering: " + turneringsNavnet + " ===");
        for (Kamp k : kampe){
            System.out.println(k);
        }
    }

    public List<Kamp> getKampe(){
        return kampe;
    }

    @Override
    public String toString(){
        return turneringsNummer + ": " + turneringsNavnet + " (" + disciplinen + ", " + datoen + " )";
    }
}
