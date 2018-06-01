
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLClassLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LadowanieKlasKomiwojazer {
    
    private Object algObj;
    
    private Method[] methods;

    private static final String MENU_APLIKACJA = 
			"M E N U   G L O W N E \n" +
                        "Wybierz algorytm: \n" +
			"1 - Przeglad zupelny \n" +
                        "2 - Najblizszego sasiada \n" +
                        "3 - Best of \n" +
                        "0 - Zakoncz program          \n";    

    private static final String MENU_ALGORYTMU = 
			"M E N U   A L G O R Y T M U \n" +
			"1 - Wczytaj z pliku \n" +
			"2 - Uzyj algorytmu        \n" +
			"3 - Wyswielt rozwiazanie algorytmu  \n" +
                        "4 - Wyswielt informacje o algorytmie  \n" +
                        "0 - Powrot do wyboru algorytmu          \n";  
    
    private UserDialog UI = new ConsoleUserDialog();
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            new LadowanieKlasKomiwojazer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public LadowanieKlasKomiwojazer() throws Exception{ 
        bestOf();
        menuGlowne();
    }
    
    public void menuGlowne(){
        while (true) {
            try {
                switch (UI.enterInt(MENU_APLIKACJA + "==>> ")) {
                case 1:
                        przegladZupelny();
                        menuAlgorytmu();
                        break;
                case 2:
                        najblizszegoSasiada();
                        menuAlgorytmu();
                        break;
                case 3:
                        bestOf();
                        menuAlgorytmu();
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
    
    public void menuAlgorytmu(){
        String graf;
        while (true) {
            try {
                switch (UI.enterInt(MENU_ALGORYTMU + "==>> ")) {
                case 1:
                        for(int i=0;i<methods.length;i++){
                            if(methods[i].getName().equals("loadFromFile")){
                                graf = UI.enterString("Podaj sciezke do pliku z grafem: ");
                                methods[i].invoke(algObj, graf);
                                break;
                            }
                        }
                        break;
                case 2:
                        for(int i=0;i<methods.length;i++){
                            if(methods[i].getName().equals("algorithm")){
                                methods[i].invoke(algObj);
                                break;
                            }
                        }
                        break;
                case 3:
                        for(int i=0;i<methods.length;i++){
                            if(methods[i].getName().equals("displaySolution")){
                                methods[i].invoke(algObj);
                                break;
                            }
                        }
                        break;
                case 4:
                        for(int i=0;i<methods.length;i++){
                            if(methods[i].getName().equals("algorithmInfo")){
                                methods[i].invoke(algObj);
                                break;
                            }
                        }
                        break;
                case 0:
                        menuGlowne();
                        return;
                }
            } 
            catch (Exception e) {
                    UI.printErrorMessage(e.getMessage());
            }
        }
    }
    
    public Object load(String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
        Class classL = Class.forName(className);
        return classL.newInstance();
    }
    
    public void przegladZupelny() throws ClassNotFoundException, InstantiationException, IllegalAccessException{
        algObj = load("PrzegladZupelny");
        Class alg = algObj.getClass();
        methods = alg.getMethods();
    }
    
    public void najblizszegoSasiada() throws ClassNotFoundException, InstantiationException, IllegalAccessException{
        algObj = load("NajblizszegoSasiada");
        Class alg = algObj.getClass();
        methods = alg.getMethods();
    }
    
    public void bestOf() throws ClassNotFoundException, InstantiationException, IllegalAccessException{
        algObj = load("BestOf");
        Class alg = algObj.getClass();
        methods = alg.getMethods();
        Field[] fields = alg.getDeclaredFields();
        
        Object algObj2 = load("PrzegladZupelny");
        Object algObj3 = load("NajblizszegoSasiada");
        
        for(int i=0;i<fields.length;i++){
            if(fields[i].getName().equals("pz")){
                fields[i].setAccessible(true);
                fields[i].set(algObj, algObj2);
            }
            else if(fields[i].getName().equals("ns")){
                fields[i].setAccessible(true);
                fields[i].set(algObj, algObj3);
            }
        }
    }
}
