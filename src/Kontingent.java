/*
Klassen har ansvaret for at håndtere kontingentpriser.
Den definerer prisen for:
    - Aktivt medlemskab
    - Passivt medlemskab
    - Junior (under 18 år)
    - Senior (18 til 59 år)
    - Over 60 år

Klassen bruges til at beregne, hvilken pris et medlem skal betale
ud fra alder og medlemskabstype.
*/

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Kontingent {

    public Kontingent(){}   //Default constructor

    public static int beregnKontingent(int alder, boolean aktiv){
        double pris;

        if (!aktiv) {      //Passivt medlemskab. "!" betyder at er det er det omvendte
            return 250;
        }

        if (alder < 18) {   //Aktiv medlemskab for junior
            pris = 800;
        }

        else {           //Senior medlemskab
            pris = 1500;


            if (alder >= 60) {              //Hvis medlem er 60 år eller ældre, så er det 25% billigere
                pris = (int)(pris * 0.75); // 25% trukket fra
            }
        }

        return (int) pris;   //Returnere den korrekte pris inden for hvilket medlemskab de er en del af
    }

    public static void betalKontingent() throws Exception {
        List<Betaling> betalingsListe = FileUtil.laesBetalingFraFil();

        Scanner sc = new Scanner(System.in);

        // 1. Hent medlemsnummer
        System.out.println("Indtast medlemsnummer:");
        int medlemsnummer = sc.nextInt();

        // 2. Find medlem i fil
        for (Betaling b: betalingsListe) {
            if (b.getMedlemsNummer() == medlemsnummer) {
                // 5. Indtast betaling
                int nyRestance;

                System.out.println("Hvor meget har medlemmet betalt?");
                int beloeb = sc.nextInt();

                nyRestance = b.getRestance() - beloeb;

                FileUtil.opdaterBetalingTilFil(b.getMedlemsNummer(), nyRestance);
            }
        }
    }

    public static void nulstilBetalinger() throws IOException {

        LocalDate dagsDato = LocalDate.now();
        LocalDate nulstillingsDato = LocalDate.of(LocalDate.now().getYear(), 1, 1);
        if (dagsDato.isEqual(nulstillingsDato) && !Main.betalingOpdateret) {

            List<Betaling> betalingsListe = FileUtil.laesBetalingFraFil();
            List<Medlem>  medlemsListe = FileUtil.laesMedlemFraFil();

            List<String> linjer = new ArrayList<>();

            for (Betaling b: betalingsListe) {
                for (Medlem m: medlemsListe) {
                    if (b.getMedlemsNummer() == m.getMedlemsNummer()) {
                        String nyLinje = m.getMedlemsNummer() + "_false_" + beregnKontingent(m.getBeregnAlder(),m.getMedlemskab());
                        linjer.add(nyLinje);
                    }
                }
            }

            Path fil = Paths.get("betaling.txt");

            Files.write(fil, linjer);

            Main.betalingOpdateret = true;
        }

        if (dagsDato.isAfter(nulstillingsDato)) {Main.betalingOpdateret = false;}

    }
}
