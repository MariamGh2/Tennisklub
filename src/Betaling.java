public class Betaling {


    public Betaling(){}   //Deafult constructor



    private boolean harBetalt;     //Variabel for om medlem har betalt

    public boolean betaling() {   //Finder ud af om medlem har betalt
        return harBetalt;
    }

    public void betalKontigent(int alder, boolean aktiv){
       //Hvis medlem har betalt
        if (harBetalt) {
            System.out.println("Kontingent betalt");
        } else {       //Hvis medlem IKKE har betalt
            Pris prisberegner = new Pris();                          //Beregner kontingent prisen for medlemmet
            int pris = prisberegner.beregnPris(alder, aktiv);        //Beregner prisen udfra alder og om man er aktiv

            System.out.println("Kontigent pris: " + pris + "kr.");

            harBetalt = true;

            System.out.println("Du har betale kontingent");
        }
    }
}

