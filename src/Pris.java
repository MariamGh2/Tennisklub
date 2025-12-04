public class Pris {

    public Pris(){}

    public int beregnPris(int alder, boolean aktiv){
        int pris;

        //Passivt medlemskab. "!" betyder er det er det omvendte
        if (!aktiv) {
            return 250;
        }

        //Aktiv medlemskab for junior
        if (alder < 18) {
            pris = 800;
        }
        //Senior medlemskab
        else {
            pris = 1500;

            //Hvis medlem er 60 år eller ældre, så er det 25% billigere
            if (alder >= 60) {
                pris = (int)(pris * 0.75); // 25% trukket fra
            }
        }

        return pris;
    }
}
