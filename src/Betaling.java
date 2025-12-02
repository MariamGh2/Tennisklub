public class Betaling {


    //Attribut der holder styr på om medlem har betalt
    public Betaling() {
        this.harBetalt = false;
    }

    //Gemmer om medlem har betalt
    private boolean harBetalt;

    public boolean betalKontigent(int alder, boolean aktiv){
       //Hvis medlem har betalt
        if (harBetalt) {
            return true;
        }

        //Hvis medlem IKKE har betalt
        Pris prisberegner = new Pris();
        int pris = prisberegner.beregnPris(alder, aktiv);

        System.out.println("Kontigent pris: " + pris + "kr.");

        harBetalt = true;

        return true;
    }
}

