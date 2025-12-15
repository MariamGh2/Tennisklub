/*
Henter brugernavn og password.
Interface for Coach, formand og kasserer
Interface er tom funktion - super klasse som har tomme funktioner
 */

/*
Dette interface definerer fælles funktionalitet for alle brugertyper.
Det gør det muligt at:
    - Logge ind vha. brugernavn og kodeord
    - Hente brugerens brugernavn
    - Hente brugerens kodeord
    - Logge ud af systemet
    - Tilgå en menu, der afhænger af brugerens rolle
        - Coach
        - Formand
        - Kasserer
*/

public interface Bruger {

    //Funktion for at hente brugernavnet
    String getBrugernavn();

    //Funktion for at hente passwordet
    String getPassword();

    //Funktion for at logge ud af systemet
    void logud();

    //Funktion der giver menuen for brugeren
    void menu() throws Exception;

    void sluk();
}
