public class Kontingent {

//Klassen har ansvaret for at beregne kontingent for det enkelte medlem

    public Kontingent(){} //Default Constructor


                                                                             //double giver bedre mening at anvende end int, så vi kan returnere et decimaltal.
    public double beregnKontingent(Medlem medlem) {                 //Medlem medlem = klassens navn & parameteren der anvendes i metoder.
        if(!medlem.erAktivtMedlem()){   //Hvis medlem ikke er aktiv (passiv)
        return 250.0;                   //Passivt medlem takst
    }

    int alder = medlem.getBeregnAlder(); //getAlder() skal returnere medlemmets alder i hele år, så vi ved hvilken takt de hører indunder

    if (alder < 18) {   //Under 18
        return 800.0;  //Junior takst
    }

    if(alder < 60){     //Under 60
        return 1500.0;  //Senior takst
    }

    return 1500.0 * 0.75;  //Medlemmer fra over 60 år får 25% rabat af seniortaksten
    }








    public void prisBestemmelse(){}


    public void getKontingent(){}


    public void getRestance(){}


}
