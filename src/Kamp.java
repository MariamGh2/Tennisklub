import java.time.LocalDate;
/*
Beskriver konkrete kampe (som coach kan opdatere med opdaterResultat()).
 */

public class Kamp {


    public Kamp(){}   //Default constructor
        
    //Attributter
    private Spiller spiller1;    //Første spiller
    private Spiller spiller2;    //Anden spiller
    private String dato;        //Kampdato
    private String disciplin;    //Single, double eller mixed double
    private String resultat;     //Fx "6-3, 4-6, 7-5"
    private boolean erAfviklet;  //true hvis kampen er spillet, false hvis den kun er planlagt

    private Turnering turnering; //Hvilken turnering kampen hører til

    //Constructor til en NY planlagt kamp uden resultat endnu
    public Kamp(Spiller spiller1, Spiller spiller2, String dato, String disciplin, Turnering turnering){
        this.spiller1 = spiller1;
        this.spiller2 = spiller2;
        this.dato = dato;
        this.disciplin = disciplin;
        this.turnering = turnering;
        this.resultat = ";";       //Ingen resultat endnu
        this.erAfviklet = false;   //Kamp endnu ikke spillet
    }

    //Metode til at registrere resultatet EFTER kampen er spillet
    public void registrerResultat(String resultat){
        this.resultat = resultat;
        this.erAfviklet = true;
    }

                         ///GETTERS
    public Spiller getSpiller1(){
        return spiller1;
    }

    public Spiller getSpiller2(){
        return spiller2;
    }

    public String getDato(){
        return dato;
    }

    public String getDisciplin(){
        return disciplin;
    }

    public String getResultat(){
        return resultat;
    }

    public boolean isErAfviklet(){
        return erAfviklet;
    }

    public Turnering getTurnering(){    //Samler kampe og kan fx give coachen overblik over kampe i en bestemt turnering
        return turnering;
    }

    @Override
    public String toString(){
        String status = erAfviklet ? "Afviklet" : "Planlagt";    //Hvis kampen er afviklet (erAfviklet == true), så vis teksten "Afviklet" ellers vis "Planlagt"
        String resTekst = erAfviklet ? resultat : "Intet resultat endnu";   //Hvis kampen er afviklet (erAfviklet == true),så vis resultat eller vis "INtet resultat endnu"

        return "Turnering: " + turnering +
                " | " + spiller1.getNavn() + " VS. " + spiller2.getNavn() +
                " | Dato: " + dato +
                " |  Disciplin: " + disciplin +
                " | Status: " + status +
                " | Resultat: " + resTekst;
    }





















}
