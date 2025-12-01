import java.io.File;

public class Coach implements Bruger {

    public Coach(){} //Default Constructor



    public void topFem(){}


    public void opdaterResultat(){}


    //Funktion til at få password fra password filen
    public static String getBrugernavn(){

        File file = new File("kontoer.txt");

        String brugernavn = "";
        brugernavn = FileUtil.laesDataFraFil(file, "coach", 2);

        return brugernavn;
    }


    //Funktion til at få password fra password filen
    public static String getPassword(){

        File file = new File("kontoer.txt");

        String password = "";
        password = FileUtil.laesDataFraFil(file, "coach", 3);

        return password;
    }

    @Override
    public void logud() {

    }

    @Override
    public void menu() {

    }
}
