/*
Klassen bruges til at beskrive en spillers profil som konkurrencespiller.
Den indeholder:
    - Spillerens disciplin (single, double, mixed)
    - Hvilket hold spilleren tilhører (junior eller senior)
    - Spillerens rangering i klubben
    - Bedste træningsresultat og dato for dette

Klassen har ansvaret for:
    - At gemme og håndtere konkurrence-relaterede oplysninger for hver spiller
    - At give adgang til disciplin, hold, rangering og træningsresultater
*/

public class KonkurrenceSpillere {

    public KonkurrenceSpillere() {} //Default Constructor

        private Spiller spiller;  //Hvilken spiller det drejer sig om
        private String disciplin;  //Single, Double eller Mixed Double
        private String hold;       //Junior eller Senior
        private int resultat;      //Fx bedste træningsresultat
        private String dato;       //Dato for resultat, dd-MM-yyyy
        private int rangering;    //Lavere tal = bedre

    //Bruges når vi bygger objekt ud fra kamp- eller spiller-data
    public KonkurrenceSpillere(Spiller spiller, String disciplin, String hold, int rangering, int resultat, String dato) {
        this.spiller = spiller;
        this.disciplin = disciplin;
        this.hold = hold;
        this.rangering = rangering;
        this.resultat = resultat;
        this.dato = dato;
    }


                        /// GETTERS
    public Spiller getSpiller(){
        return spiller;
    }

    public String getDisciplin() {
        return disciplin;
    }

    public String getHold() {
        return hold;
    }

    public int getResultat() {
        return resultat;
    }

    public String getDato() {
        return dato;
    }

    public int getRangering() {
        return rangering;
    }


                        /// SETTERS
    public void setResultat(int resultat) {
        this.resultat = resultat;
    }

    public void setDato(String dato) {
        this.dato = dato;
    }

}






