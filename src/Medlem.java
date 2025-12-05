import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;


/*
Klassen repræsentere ét medlem i klubben
Hvert medlem har: navn, medlemskabstype, medlemsnummer, mail og fødselsdag
 */

public abstract class Medlem {


    public Medlem() {} //Default Constructor

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private String position;
    private String navn;
    private int medlemsNummer;
    private boolean medlemskab;  //true = aktiv, false = passiv
    private String mail;
    private LocalDate foedselsdag;
    private boolean betaling;


    private static final Path FIL = Path.of("medlem.txt");   //Sti til filen hvor ALT gemmes

    public Medlem(String position, String navn,boolean aktivPassiv, String foedselsdag, String mail) {
        this.position = position;
        this.navn = navn;
        this.medlemskab = aktivPassiv;
        this.mail = mail;
        this.foedselsdag = LocalDate.parse(foedselsdag, formatter);
        this.betaling = true;

        //ALTID nyt nummer her
        this.medlemsNummer = Medlemsnummer.hentNytMedlemsnummer();

        //skrivMedlemTilFil();              //Skriv medlemmet i filen
        //sorterFilEfterMedlemsnummer();    //Sorter filen så numrene altid står i rækkefølge
    }

    public Medlem(String position, String navn, int medlemsNummer, boolean aktivPassiv, String foedselsdag, String mail, boolean betaling){
        this.position = position;
        this.navn = navn;
        this.medlemskab = aktivPassiv;
        this.mail = mail;
        this.foedselsdag = LocalDate.parse(foedselsdag, formatter);
        this.betaling = betaling;

        //Brug det nummer vi læser fra fil - INGEN ny generering
        this.medlemsNummer = medlemsNummer;
    }

    /// Skriver et medlem i melem.txt i formatet: navn_medlemsnummer_fødselsdato_mail
    private void skrivMedlemTilFil() {
        try {
            if (!Files.exists(FIL)) {         //Sørg for at filen findes (ellers opret tom fil)
                Files.writeString(FIL, "");
            }
            String linje = navn + "_" + medlemsNummer + "_" + foedselsdag + "_" + mail + "_" + medlemskab + "\n";    //Format på linjen der skrives

            Files.writeString(FIL, linje, StandardOpenOption.APPEND);     //Tilføj til linjen nederst i filen

        } catch (IOException e) {
            throw new RuntimeException("Kunne ikke skrive til medlem.txt");
        }
    }


    ///Sortere edlem.txt efter medlemsnummer
    public static void sorterFilEfterMedlemsnummer(){
        try {

            if (!Files.exists(FIL)) {
                return; //intet at sortere
        }

            List<String> linjer = Files.readAllLines(FIL);   //Læser alle linjer


            linjer.removeIf(l -> l.trim().isEmpty());     //trim() fjerner mellemrum og skjulte tegn
                                                                 //isEmpthy() tjekker om den er tom, hvis ja, fjernes linjen

            linjer.sort((l1, l2) -> {      //For hver to linjer (l1 og l2), sammenlign dem - og bestem hvilken der skal stå først
                String[] d1 = l1.split("_");      //Tag linjen l1. split den ved hver _ og gem delene i et array
                String[] d2 = l2.split("_");      //Tag linjen l2. split den ved hver _ og gem delene i et array

                int n1 = Integer.parseInt(d1[2].trim());  //Find medlemsnr. i linje 1 (felt 2 -> d1[1], fjern mellemrum (.trim()) og lav teksten om til et tal med parseInt
                int n2 = Integer.parseInt(d2[2].trim());   //Find medlemsnr. i linje 2 (felt 2 -> d1[1], fjern mellemrum (.trim()) og lav teksten om til et tal med parseInt

                return Integer.compare(n1,n2);   //Selve sammenligningen: Hvis n1 < n2 -> l1 skal stå før l2
                                                 //Hvis n1 > n2 -> l1 skal stå efter l2
                                                  //Hvis de er ens -> rækkefølgen betyder ikke noget
                                                 //Listen bliver sorteret fra laveste medlemsnr. til højeste
            });


            Files.write(FIL, linjer);           //Efter sorteringen overskrives hele filen med den sorterede udgave af linjerne

        } catch (IOException e) {
            throw new RuntimeException("Fejl ved sortering af edlem.txt");
        }
    }

    private static int hentMedlemsnummerFraLinje(String linje){     //Hjælpemetode til at finde medlemsnr. i en linje
        String trimmed = linje.trim();
        if (trimmed.isEmpty()) return Integer.MAX_VALUE;

        String[] dele = trimmed.split("_");
        if (dele.length < 2) return Integer.MAX_VALUE;   //Hvis der ikke er noget felt nr. 2

        try {
            return Integer.parseInt(dele[1].trim());
        } catch (NumberFormatException e) {
            return Integer.MAX_VALUE;   //Hvis format er forkert, skub den linje ned i bunden
        }
    }


    /// GETTERS

    public String getPosition() {return position;}

    public String getNavn() {
        return navn;
    }

    public boolean getMedlemskab() {
        return medlemskab;
    }

    public String getMail() {
        return mail;
    }

    public void alder() {
    }

    public int getMedlemsNummer() {
        return medlemsNummer;
    }

    public int getBeregnAlder() {
        return Period.between(foedselsdag, LocalDate.now()).getYears();
    }

    public boolean erAktivtMedlem(){
        return medlemskab;
    }



    @Override
    public String toString() {
        return navn + " (Medlemsnr.: " + medlemsNummer + ")";
    }
}