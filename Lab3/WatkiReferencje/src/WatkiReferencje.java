import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Data ostatniej modyfikacji: 06.03.2018
 * 
 * @author Adam Filipowicz
 */
public class WatkiReferencje {

    private UserDialog UI = new ConsoleUserDialog();
    
    private int ziarno;
    
    private WeakHashMapa whm;
    
    /**
     * Menu aplikacji - wyswietlane tuz po jej wlaczeniu.
     */
    private static final String MENU_APLIKACJA = 
			"M E N U   G L O W N E \n" +
			"1 - Podaj nowe ziarno \n" +
                        "2 - Test weak hash map \n" +
                        "3 - Uruchom watki \n" +
                        "0 - Zakoncz program          \n"; 
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new WatkiReferencje();
    }
    
    public WatkiReferencje(){
        whm = new WeakHashMapa();
        while (true) {
            try {
                switch (UI.enterInt(MENU_APLIKACJA + "==>> ")) {
                case 1:
                        podajZiarno();
                        break;
                case 2:
                        testWHM();
                        break;
                case 3:
                        uruchomWatki();
                        break;
                case 0:
                        UI.printInfoMessage("\nProgram zakonczyl dzialanie!");
                        System.out.println("Nastąpiło "+whm.getHowMuchDeleted()+" usunięć wpisów z pamięci podręcznej");
                        System.exit(0);
                }
            } 
            catch (Exception e) {
                    UI.printErrorMessage(e.getMessage());
            }
        }
    }
    
    private void podajZiarno() throws InterruptedException{
        
        do{
            ziarno=UI.enterInt("Podaj ziarno od 1 do 10000: ");
        }while(ziarno<1 || ziarno>10000);
        
        whm.putWHM(ziarno);
        
        
        
        /*for(int i=0;i<kolekcjaa.length;i++){
            System.out.println(kolekcjaa[i]);
        }*/
    }
    
    private void testWHM() throws InterruptedException{
        for(int j=0;j<10;j++){
            for(int i=1;i<10000;i++){
                Kolekcja kol = new Kolekcja(i);
                double[] kolekcja=kol.getKolekcja();

                whm.putWHM(ziarno);
                System.out.println("Ok"+i+" "+j);
                System.gc();
            }
        }
    }
    
    private void uruchomWatki() throws InterruptedException{
        WatekStatystyki[] ws = new WatekStatystyki[5];
        for(int i=0;i<5;i++){
            ws[i] = new WatekStatystyki(whm, i);
            ws[i].start();
        }
        try{ 
            Thread.sleep(50000); 
        }
        catch(InterruptedException e) {
            e.printStackTrace();
        }
        for(int i=0;i<5;i++){
            ws[i].setZakoncz(true);
        }
    }
    
}