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

    private boolean harBetalt;      //Variabel for om medlem har betalt (true/false)
    private int medlemsNummer;
    private int restance;

    public Betaling(int medlemsNummer, boolean harBetalt, int restance) {
        this.medlemsNummer = medlemsNummer;
        this.harBetalt = harBetalt;
        this.restance = restance;
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