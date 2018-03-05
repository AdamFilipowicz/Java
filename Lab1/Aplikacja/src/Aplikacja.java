import java.io.BufferedReader;
import java.io.FileReader;
/**
 * Klasa Aplikacja wykorzystuje bibliotekę klas Testownik.jar.
 * Pozwala ona na ustawienie liczby pytań i opcji dla analizowanego testu,
 * wczytanie klucza oraz kart odpowiedzi z podanych plików .csv, a także
 * sprawdzenia następujących statystyk dla podanych wcześniej kart odpowiedzi:
 *  - histogram zdobytych punktów,
 *  - liczba niezaliczonych prac,
 *  - srednia liczba punktow zdobytych przez studentow.
 * 
 * Data ostatniej modyfikacji: 26.02.2018
 * 
 * @author Adam Filipowicz
 */
public class Aplikacja {

    /**
     * Klasa ConsoleUserDialog implenentuje metody umozliwiajace
     * wyswietlanie komunikatow oraz wczytywanie danych bezposrednio
     * w konsoli, czyli wykorzystywane sa standardowe strumienie 
     * wejscia/wyjscia:  System.out,  System.in,   System.err.
     */
    private UserDialog UI = new ConsoleUserDialog();
    
    /**
     * Menu aplikacji - wyswietlane tuz po jej wlaczeniu.
     */
    private static final String MENU_APLIKACJA = 
			"M E N U   G L O W N E \n" +
			"1 - Ustaw liczbe pytan i opcji \n" +
			"2 - Wczytaj klucz odpowiedzi        \n" +
			"3 - Wczytaj karty odpowiedzi  \n" +
			"4 - Sprawdz statystyki testu     \n" + 
                        "0 - Zakoncz program          \n"; 
    
    /**
     * Odpowiedzi klucza danego analizowanego testu.
     */
    private int[] odpowiedziPoprawne;
    
    /**
     * Tablica wszystkich kart odpowiedzi danego analizowanego testu.
     */
    private int[][] odpowiedzi;
    
    /**
     * Liczba pytań danego testu.
     */
    private int liczbaPytan;
    
    /**
     * Liczba odpowiedzi do wyboru w teście.
     */
    private int liczbaOpcji;
    
    /**
     * Liczba kart egzaminacyjnych.
     */
    private int liczbaKart;
    
    /**
     * @param args - agrumenty linii komend
     */
    public static void main(String[] args) {
        new Aplikacja();
    }
    
    /**
     * Aplikacja pokazuje glowne menu oraz pozwala na ustawienie liczby pytań i opcji dla analizowanego testu,
     * wczytanie klucza oraz kart odpowiedzi z podanych plików .csv, a także
     * sprawdzenia następujących statystyk dla podanych wcześniej kart odpowiedzi:
     *  - histogram zdobytych punktów,
     *  - liczba niezaliczonych prac,
     *  - srednia liczba punktow zdobytych przez studentow.
     * 
     * @throws Exception - wyjatki lapane przy wczytywaniu oraz zapisywaniu pliku oraz w metodach menu.
     */
    public Aplikacja(){
        liczbaPytan=0;
        liczbaOpcji=0;
        while (true) {
            try {
                switch (UI.enterInt(MENU_APLIKACJA + "==>> ")) {
                case 1:
                        ustaw();
                        break;
                case 2:
                        wczytajKlucz();
                        break;
                case 3:
                        wczytajKarty();
                        break;
                case 4:
                        sprawdzStatystyki();
                        break;
                case 0:
                        UI.printInfoMessage("\nProgram zakonczyl dzialanie!");
                        System.exit(0);
                }
            } 
            catch (Exception e) {
                    UI.printErrorMessage(e.getMessage());
            }
        }
    }
    
    /**
     * Metoda pytająca użytkownika o liczbę pytań testu oraz liczbę możliwych odpowiedzi.
     */
    public void ustaw(){
        liczbaPytan=UI.enterInt("Podaj liczbe pytan testu: ");
        liczbaOpcji=UI.enterInt("Podaj liczbe opcji testu: ");
        odpowiedziPoprawne=new int[liczbaPytan];
    }
    
    /**
     * Metoda wczytująca klucz odpowiedzi, pod warunkiem, że podano wcześniej
     * liczbę pytań oraz liczbę możliwych odpowiedzi.
     */
    public void wczytajKlucz(){
        if(liczbaPytan==0 || liczbaOpcji==0){
            UI.printErrorMessage("Podaj najpierw liczbe pytan i opcji");
            return;
        }
        String sciezka = UI.enterString("Podaj sciezke do klucza odpowiedzi: ");
        try{
            wczytajZPlikuKlucz(sciezka);
        }
        catch(Exception e){
            UI.printErrorMessage(e.getMessage());
        }
    }
    
    /**
     * Wetoda wczytująca karty odpowiedzi, pod warunkiem, że podano wcześniej
     * liczbę pytań, liczbę możliwych odpowiedzi oraz podano prawidłową ścieżkę do
     * klucza odpowiedzi.
     */
    public void wczytajKarty(){
        if(liczbaPytan==0 || liczbaOpcji==0){
            UI.printErrorMessage("Podaj najpierw liczbe pytan i opcji");
            return;
        }
        if(odpowiedziPoprawne==null || odpowiedziPoprawne[0]==0){
            UI.printErrorMessage("Wczytaj najpierw klucz odpowiedzi");
            return;
        }
        String sciezka = UI.enterString("Podaj sciezke do kart odpowiedzi: ");
        try{
            wczytajZPlikuKarty(sciezka);
        }
        catch(Exception e){
            UI.printErrorMessage(e.getMessage());
        }
    }
    
    /**
     * Metoda wypisująca następujące statystyki: 
     *  - histogram zdobytych punktów,
     *  - liczba niezaliczonych prac,
     *  - srednia liczba punktow zdobytych przez studentow,
     * 
     * pod warunkiem że podano wcześniej liczbę pytań, liczbę możliwych 
     * odpowiedzi oraz podano prawidłową ścieżkę do klucza odpowiedzi, a także
     * do kart odpowiedzi.
     */
    public void sprawdzStatystyki(){
        if(liczbaPytan==0 || liczbaOpcji==0){
            UI.printErrorMessage("Podaj najpierw liczbe pytan i opcji");
            return;
        }
        if(odpowiedziPoprawne==null || odpowiedziPoprawne[0]==0){
            UI.printErrorMessage("Wczytaj najpierw klucz odpowiedzi");
            return;
        }
        if(odpowiedzi==null || odpowiedzi[0][0]==0){
            UI.printErrorMessage("Wczytaj najpierw karty odpowiedzi");
            return;
        }
        
        ZestawKartOdpowiedzi zestaw = new ZestawKartOdpowiedzi(liczbaPytan, liczbaOpcji, odpowiedziPoprawne);
        int[] odpowiedziKarty;
        KartaOdpowiedzi karta;
        for(int i=0;i<liczbaKart;i++){
            odpowiedziKarty = new int[liczbaPytan];
            karta = new KartaOdpowiedzi(liczbaPytan, liczbaOpcji);
            for(int j=0;j<liczbaPytan;j++){
                odpowiedziKarty[j]=odpowiedzi[j][i];
            }
            karta.setOdpowiedzi(odpowiedziKarty);
            zestaw.dodajKarte(karta);
        }
        
        Statystyki stat = new Statystyki(zestaw);
        System.out.print(stat.Histogram());
        System.out.println(stat.LiczbaNiezaliczonych());
        System.out.println(stat.SredniaPunktow());
    }
    
    /**
     * Metoda wczytujaca z pliku .csv odpowiedzi na kolejne zadania testu.
     * @param fileName - nazwa pliku do wczytania informacji
     * @throws Exception - wyjatek przy wczytywaniu z pliku
     */
    public void wczytajZPlikuKlucz(String fileName) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line = "";
        String cvsSplitBy = ";";
        int i=0;
        String[] country=null;
        if((line = br.readLine()) != null)
            country = line.split(cvsSplitBy);
        while ((line = br.readLine()) != null) {
            country = line.split(cvsSplitBy);
            odpowiedziPoprawne[i] = Integer.parseInt(country[0]);
            i++;
        }
    }
    
    /**
     * Metoda wczytujaca z pliku .csv wszystkie karty odpowiedzi.
     * @param fileName - nazwa pliku do wczytania informacji
     * @throws Exception - wyjatek przy wczytywaniu z pliku
     */
    public void wczytajZPlikuKarty(String fileName) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line = "";
        String cvsSplitBy = ";";
        int i=0;
        if((line = br.readLine()) != null){
            String[] country = line.split(cvsSplitBy);
            liczbaKart=country.length;
            odpowiedzi=new int[liczbaPytan][liczbaKart];
        }
        while ((line = br.readLine()) != null) {
            String[] country = line.split(cvsSplitBy);
            for(int j=0;j<country.length;j++){
                odpowiedzi[i][j] = Integer.parseInt(country[j]);
            }
            i++;
        }
    }
    
}
