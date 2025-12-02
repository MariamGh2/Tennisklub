import java.io.File;

public class Turnering {


    public Turnering(){} //Default Constructor


    private String turneringsNavnet;
    private String disciplinen;
    private String datoen;

    public Turnering(String turneringsNavn, String disciplin, String dato){
        this.turneringsNavnet = turneringsNavn;
        this.disciplinen = disciplin;
        this.datoen = dato;

        File fil = new File ("turneringer.txt");
        FileUtil.appendTilFil(fil, turneringsNavnet + "_" + disciplinen + "_" + datoen);
    }


    public String getTurnering(){
        return turneringsNavnet;
    }

    public String getDisciplinen(){ return disciplinen;
    }

    public String getDatoen(){
        return datoen;
    }
}
