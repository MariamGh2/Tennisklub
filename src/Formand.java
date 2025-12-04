import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;

public class Formand {
    public Formand(){} //Default Constructor


    public static void opretMedlem(){

        String navn;
        int medlemNummer;
        String foedselsdag;
        String mail;
        String type;


        Scanner sc = new Scanner(System.in);

        System.out.println("Skriv navn");
        navn = sc.nextLine();
        medlemNummer = Medlemsnummer.hentNytMedlemsnummer();
        System.out.println("Skriv fødselsdag");
        foedselsdag = sc.nextLine();

        System.out.println("skriv mail");
        mail = sc.nextLine();

        System.out.println("er du motionist eller konkurrence spiller");
        type = sc.nextLine();


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate foedselsdato = LocalDate.parse(foedselsdag, formatter);

        Medlem m = new Medlem(navn, type, 0, mail, foedselsdato);  //opret medlem (Medlem klarer medlemsnummer + filskrivning + sortering)




//        if statement skal skrives her.



//        new Medlem(navn, "Aktiv", medlemNummer, foedselsdag, mail);

        File fil = new File("medlem.txt");

        FileUtil.appendTilFil(fil, navn + "_" + medlemNummer + "_" + foedselsdag + "_" + mail);
    }

    public void sletMedlem(){}
}
