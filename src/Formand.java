import java.io.File;
import java.util.Scanner;

public class Formand {
    public Formand(){} //Default Constructor


    public static void opretMedlem(){

        String navn;
        int medlemNummer;
        String foedselsdag;
        String mail;

        Scanner sc = new Scanner(System.in);

        System.out.println("Skriv navn");
        navn = sc.nextLine();
        medlemNummer = Medlemsnummer.hentNytMedlemsnummer();
        System.out.println("Skriv fødselsdag");
        foedselsdag = sc.nextLine();
        System.out.println("skriv mail");
        mail = sc.nextLine();

//        new Medlem(navn, "Aktiv", medlemNummer, foedselsdag, mail);

        File fil = new File("medlem.txt");

        FileUtil.appendTilFil(fil, navn + "_" + medlemNummer + "_" + foedselsdag + "_" + mail);
    }

    public void sletMedlem(){}



}
