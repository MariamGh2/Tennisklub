public class Kontingent {
// Junior (<18 år): 800 kr
//Senior (≥18 år): 1500 kr
//Senior ≥60 år: 25% rabat
//Passivt medlemskab: 250 kr

    // double giver bedre mening at anvende end int, så vi kan returnere et decimaltal.
    // Medlem medlem = klassens navn & parameteren der anvendes i metoder.
    public double beregnKontigent(Medlem medlem){

        //passivt medlemskab
        if (medlem.erAktivtMedlem() == false){
            return 250;
        }

        int alder = medlem.getAlder();

        // Junior < 18 år
        if (alder < 18){
            return 800;
        }

        // Senior: 18-59 år
        if (alder < 60){
            return
        }
        // Senior
    }

     */
    public Kontingent(){} //Default Constructor


    public void prisBestemmelse(){}


    public void getKontingent(){}


    public void getRestance(){}
}
