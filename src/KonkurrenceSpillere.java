public class KonkurrenceSpillere {

    /*
    Klassen har ansvaret for de forskellige attributer mht. hvad der indgår i en konkurrence for spillerne
    Samt viser funktionerne for disciplin, hold osv.
     */
    public KonkurrenceSpillere() {} //Default Constructor

        private String disciplin;
        private String hold;
        private int resultat;
        private int dato;
        private int rangering;

    public KonkurrenceSpillere(String disciplin, String hold, int rangering, int resultat, int dato) {
        this.disciplin = disciplin;
        this.hold = hold;
        this.rangering = rangering;
        this.resultat = resultat;
        this.dato = dato;
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






