import java.io.FileWriter;
import java.io.IOException;

public class Betaling {

/*
Denne klasse bruges til at finde ud af om et medlem har betalt kontingentet.
Hvis de ikke har, beregnes prisen for medlemmet og derefter sættes det som true (medlem har betalt)
 */
    public Betaling(){}   //Deafult constructor

    private double kontingentPris; //Kontingentprisen for medlem (beregnes når medlem betaler)
    private double betaltBeloeb;    //Gemmer hvor meget medlem har betalt
    private boolean harBetalt;     //Variabel for om medlem har betalt (true/false)

    public boolean betaling() {   // Returnerer om medlemmet har betalt kontingent.Bruges fx når vi skal skrive betalingsstatus til filen.
        return harBetalt;
    }

    public void betalKontigent(int alder, boolean aktiv){
        if (harBetalt) {                                               //Hvis medlem har betalt
            System.out.println("Kontingent betalt");
        } else {                                                     //Hvis medlem IKKE har betalt
            Pris prisberegner = new Pris();                          //Beregner kontingent prisen for medlemmet
            int pris = prisberegner.beregnPris(alder, aktiv);        //Beregner prisen udfra alder og om man er aktiv

            kontingentPris = pris;                                  //gemmer kontigentprisen
            betaltBeloeb = pris;                                    //Gemmer beløbet medlem har betalt

            System.out.println("Kontigent pris: " + pris + "kr.");

            harBetalt = true;

            System.out.println("Du har betalt kontingent");
        }
    }


    public double beregnRestance() {
        if(harBetalt) {
            return 0.0;                                 //Hvis medlemmet har betalt det hele, så skylder de ikke noget
        } else {
            return kontingentPris - betaltBeloeb;       //Ellers beregnes hvor meget de skylder
        }
    }

    public void skrivBetalingTilFil (Medlem medlem, Betaling betaling) {
        try {
            FileWriter writer = new FileWriter("betaling.txt", true);   // Åbner filen i append-mode (= vi skriver længst ned i filen)

            int medlemsnummer = medlem.getMedlemsNummer();              //henter medlemsnr
            boolean harBetalt = betaling.betaling();                    //finder ud af om de har betalt
            double restance = betaling.beregnRestance();                //Beregner hvor meget medlemmet mangler

            String linje = medlemsnummer + "_" + harBetalt + "_" + restance + "kr.";

            writer.write(linje + "\n");                             // Skriver linjen til filen og laver linjeskift

            writer.close();                                              //Lukker writer

        } catch (IOException e) {
            System.out.println("Fejl ved skrivning til filen");
        }
    }
}


