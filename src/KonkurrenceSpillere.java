/*
Beskriver spillerens "profil" som konkurrencespiller: disciplin, hold, rangering, bedste træningsresultat

Klassen har ansvaret for de forskellige attributer mht. hvad der indgår i en konkurrence for spillerne
Samt viser funktionerne for disciplin, hold osv.
*/

 public class KonkurrenceSpillere {
    public KonkurrenceSpillere() {} //Default Constructor

        private Spiller spiller;  //Hvilken spiller det drejer sig om
        private String disciplin;  //Single, Double eller Mixed Double
        private String hold;       //Junior eller Senior
        private int resultat;      //Fx bedste træningsresultat
        private int dato;
        private int rangering;    //Lavere tal = bedre

    public KonkurrenceSpillere(Spiller spiller, String disciplin, String hold, int rangering, int resultat, int dato) {
        this.spiller = spiller;
        this.disciplin = disciplin;
        this.hold = hold;
        this.rangering = rangering;
        this.resultat = resultat;
        this.dato = dato;
    }


    public Spiller getSpiller(){
        return spiller;
    }

    public String getDisciplin () {
        return disciplin;
    }

    public String getHold () {
        return hold;
    }

    public int getResultat () {
        return resultat;
    }

    public int getDato () {
        return dato;
    }


    public int getRangering () {
        return rangering;
    }

    public void setResultat () {

    }

 }






