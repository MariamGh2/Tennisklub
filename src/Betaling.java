public class Betaling {

/*
Denne klasse bruges til at finde ud af om et medlem har betalt kontingentet.
Hvis de ikke har, beregnes prisen for medlemmet og derefter sættes det som true (medlem har betalt)
 */
    public Betaling(){}   //Deafult constructor



    private boolean harBetalt;     //Variabel for om medlem har betalt

    public boolean betaling() {   //Finder ud af om medlem har betalt
        return harBetalt;
    }

    public void betalKontigent(int alder, boolean aktiv){
        if (harBetalt) {     //Hvis medlem har betalt
            System.out.println("Kontingent betalt");
        } else {            //Hvis medlem IKKE har betalt
            Pris prisberegner = new Pris();                          //Beregner kontingent prisen for medlemmet
            int pris = prisberegner.beregnPris(alder, aktiv);        //Beregner prisen udfra alder og om man er aktiv

            System.out.println("Kontigent pris: " + pris + "kr.");

            harBetalt = true;

            System.out.println("Du har betale kontingent");
        }
    }
}

