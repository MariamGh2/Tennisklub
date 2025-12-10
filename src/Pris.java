/*
Klassen har ansvaret for at håndtere kontingentpriser.
Den definerer prisen for:
    - Aktivt medlemskab
    - Passivt medlemskab
    - Junior (under 18 år)
    - Senior (18 til 59 år)
    - Over 60 år

Klassen bruges til at beregne, hvilken pris et medlem skal betale
ud fra alder og medlemskabstype.
*/

public class Pris {

    public Pris(){}   //Default constructor

    public double beregnPris(int alder, boolean aktiv){
        double pris;

        if (!aktiv) {      //Passivt medlemskab. "!" betyder at er det er det omvendte
            return 250.0;
        }

        if (alder < 18) {   //Aktiv medlemskab for junior
            pris = 800.0;
        }

        else {           //Senior medlemskab
            pris = 1500.0;


            if (alder >= 60) {              //Hvis medlem er 60 år eller ældre, så er det 25% billigere
                pris = (int)(pris * 0.75); // 25% trukket fra
            }
        }

        return pris;   //Returnere den korrekte pris inden for hvilket medlemskab de er en del af
    }
}
