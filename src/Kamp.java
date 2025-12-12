import java.time.LocalDate;

/*
Klassen bruges til at definere en kamp.
Den indeholder:
    - Spiller 1 og spiller 2 (deltagerne)
    - Dato for kampen
    - Disciplinen kampen spilles i (single, double, mixed)
    - Kampens resultat
    - En markering af om kampen er afviklet eller ej
*/

public class Kamp {


    public Kamp(){}   //Default constructor
        
    //Attributter
    private Spiller spiller1;    //Første spiller
    private Spiller spiller2;    //Anden spiller
    private String dato;        //Kampdato
    private String disciplin;    //Single, double eller mixed double
    private String resultat;     //medlem der vandt

    private Turnering turnering; //Hvilken turnering kampen hører til

    //Constructor til en NY planlagt kamp uden resultat endnu
    public Kamp(Spiller spiller1, Spiller spiller2, String dato, String disciplin, Turnering turnering, String resultat){
        this.spiller1 = spiller1;
        this.spiller2 = spiller2;
        this.dato = dato;
        this.disciplin = disciplin;
        this.turnering = turnering;
        this.resultat = resultat;       //Ingen resultat endnu
    }

                         ///GETTERS
    public String getDato(){
        return dato;
    }

    public String getDisciplin(){
        return disciplin;
    }

    public Spiller getSpiller1() {
        return spiller1;
    }

    public Spiller getSpiller2() {
        return spiller2;
    }

    public String getResultat() {
        return resultat;
    }


}