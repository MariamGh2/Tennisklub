import java.io.File;
import java.util.ArrayList;
import java.util.List;

/*
Klassen har ansvaret for Turnering og hvordan en turnering er stillet op
 */


public class Turnering {


    public Turnering(){} //Default Constructor


    private String turneringsNavnet;
    private String disciplinen;
    private String datoen;

    private List<Kamp> kampe = new ArrayList<>();    //Liste over kampe i denne turnering

    public Turnering(String turneringsNavn, String disciplin, String dato){
        this.turneringsNavnet = turneringsNavn;
        this.disciplinen = disciplin;
        this.datoen = dato;

        File fil = new File ("turneringer.txt");
        FileUtil.appendTilFil(fil, turneringsNavnet + "_" + disciplinen + "_" + datoen);  //Gemmer turneringen i en txt.fil(navn_disciplin_dato)
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
}
