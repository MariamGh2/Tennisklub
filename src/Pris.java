public class Pris {

/*
Klassen har ansvaret for hvad prisen er for de forskellige kontingenter
 */

    public Pris(){}

    public int beregnPris(int alder, boolean aktiv){
        int pris;

        if (!aktiv) {      //Passivt medlemskab. "!" betyder er det er det omvendte
            return 250;
        }

        if (alder < 18) {   //Aktiv medlemskab for junior
            pris = 800;
        }

        else {           //Senior medlemskab
            pris = 1500;


            if (alder >= 60) {              //Hvis medlem er 60 år eller ældre, så er det 25% billigere
                pris = (int)(pris * 0.75); // 25% trukket fra
            }
        }

        return pris;   //Returnere den korrekte pris inden for hvilket medlemskab de er en del af
    }
}
