import java.io.*;

/*
Denne klasse styrer betaling af kontingent.
Den har ansvaret for:
    - Registrere om et medlem har betalt kontingengent
    - Beregne restancen ud fra medlemskabstypen,
        - Aktov
        - Passiv
        - Junior (<18 år)
        - Senior (18+ år)
        - Over 60 år
    - Opdatere betalingsstatua i betaling.txt
    - Læse betalingaoplysninger fra fil
    - Sikre at nye medlemmer oprettes uden betaling (betaling = false)
*/

public class Betaling {

    private double kontingentPris;  //Kontingentprisen for medlem (beregnes når medlem betaler)
    private double betaltBeloeb;    //Hvor meget medlemmet faktisk har betalt
    private boolean harBetalt;      //Variabel for om medlem har betalt (true/false)
    private int medlemsNummer;
    private int restance;

    public Betaling() {         // Returnerer om medlemmet har betalt kontingent.Bruges fx når vi skal skrive betalingsstatus til filen.
        this.harBetalt = false;
        this.betaltBeloeb = 0;
        this.kontingentPris = 0;
    }

    public Betaling(int medlemsNummer, boolean harBetalt, int restance) {
        this.medlemsNummer = medlemsNummer;
        this.harBetalt = harBetalt;
        this.restance = restance;
    }

    public boolean betaling() {
        return harBetalt;
    }

    public void betalKontigent(int alder, boolean aktiv) {

        Pris prisberegner = new Pris();                          //Beregner kontingent prisen for medlemmet
        double pris = prisberegner.beregnPris(alder, aktiv);        //Beregner prisen udfra alder og om man er aktiv

        kontingentPris = pris;                                  //gemmer kontigentprisen

        if (harBetalt) {
            System.out.println("Kontingent er allerede betalt.");
            return;
        }

        harBetalt = true;
        betaltBeloeb = pris;

        System.out.println("Kontingent betalt");
    }


    public double beregnRestance() {
        return kontingentPris - betaltBeloeb;
    }

    public void skrivBetalingTilFil(int medlemsnummer) {
        try {
            FileWriter writer = new FileWriter("betaling.txt", true);   // Åbner filen i append-mode (= vi skriver længst ned i filen)

            boolean harBetalt = this.harBetalt;                    //finder ud af om de har betalt
            double restance = beregnRestance();                //Beregner hvor meget medlemmet mangler

            String linje = medlemsnummer + "_" + harBetalt + "_" + restance + "kr.";

            writer.write(linje + "\n");                             // Skriver linjen til filen og laver linjeskift

            writer.close();                                              //Lukker writer

        } catch (IOException e) {
            System.out.println("Fejl ved skrivning til filen");
        }
    }


    public void modtagBetaling(double beloeb) {
        betaltBeloeb += beloeb;

        if (betaltBeloeb >= kontingentPris) {
            harBetalt = true;
        }
    }

    public int getMedlemsNummer() {
        return medlemsNummer;
    }

    public boolean getHarBetalt() {
        return harBetalt;
    }

    public int getRestance() {
        return restance;
    }
}