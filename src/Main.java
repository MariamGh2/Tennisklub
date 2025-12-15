import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    /*
    Variabel der fortæller hvilken typer bruger der er logget ind
    */
    public static boolean online = true;
    public static String bruger = "";
    public static boolean betalingOpdateret = false;

        public static void main(String[] args) throws Exception {

        while(online) {
            //Login funktion der logger ind og opretter personalet
            Kontingent.nulstilBetalinger();
            Login.login();

            //Objekt arrayliste for personalet
            List<Object> personaleListe = new ArrayList<>();
            personaleListe = FileUtil.laesPersonaleFraFil();

            //Finder menuen for den bruger der er logget ind
            if ("coach".equals(bruger)) {
                for (Object o : personaleListe) {
                    if (o instanceof Coach c) {
                        while (bruger == "coach") {
                            c.opdaterResultat();
                            c.opdaterRangering();
                            c.menu();

                        }
                    }
                }
            } else if ("formand".equals(bruger)) {
                for (Object o : personaleListe) {
                    if (o instanceof Formand f) {
                        while (bruger == "formand") {
                            f.menu();
                        }
                    }
                }
            } else if ("kasserer".equals(bruger)) {
                for (Object o : personaleListe) {
                    if (o instanceof Kasserer k) {
                        while (bruger == "kasserer") {
                            k.menu();
                        }
                    }
                }
            }
        }
    }
}



