
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class PrzegladZupelny implements Algorytmy {
    private int w;	//liczba wierzcholkow
    private int[] wynik;	//permutacja - wynik dzialania algorytmow
    private int[] permutacja;	//obecna permutacja
    private int[] macierz;      //macierz odleglosci miedzy miastami jako tablica jednowymiarowa
    
    @Override
    public void algorithm(){
        long mintrasa = 0;	//po iteracji wybieramy minimalna trase
	long trasaaktualna;	//trasa ktorej uzywamy przy kazdej iteracji i porownujemy z mintrasa
	int k;	//najwieksza liczba taka, ze permutacja[k]<permutacja[k+1]
	int l;	//najwieksza liczba taka, ze l>k i permutacja[k]<permutacja[l]
	int waktualny;	//aktualny wierzcholek
	int pomoc;
	for (int i = 0; i < w; i++) {
            permutacja[i] = i + 1;	//pierwsza permutacja
            mintrasa += macierz[i*w+((i + 1) % w)];	//pierwsze rozwiazanie
            wynik[i] = permutacja[i];
	}
	k = 0;
        l = 0;
	while (k != -1) {
            trasaaktualna = 0;
            //szukamy nastepnej permutacji
            k = -1;
            for (int i = w - 2; i >= 0; i--) {	//1. znajdujemy k
                if (permutacja[i] < permutacja[i + 1]) {
                    k = i;
                    break;
                }
            }
            if (k == -1)
                break;
            for (int i = w - 1; i > k; i--) {	//2. znajdujemy l
                if (permutacja[k] < permutacja[i]) {
                    l = i;
                    break;
                }
            }
            //3. zamieniamy ich wartosci
            pomoc = permutacja[k];
            permutacja[k] = permutacja[l];
            permutacja[l] = pomoc;
            //4. odwracamy wszystkie wartosci od k+1 do w
            for (int i = 0; i < (w - (k + 1)) / 2; i++) {
                pomoc = permutacja[k + 1 + i];
                permutacja[k + 1 + i] = permutacja[w - 1 - i];
                permutacja[w - 1 - i] = pomoc;
            }

            //znalezlismy permutacje: szukamy czy wynik jest na razie najlepszy
            waktualny = permutacja[0] - 1;
            for (int i = 1; i < w; i++) {
                trasaaktualna += macierz[waktualny*w+(permutacja[i] - 1)];
                waktualny = permutacja[i] - 1;
            }
            trasaaktualna += macierz[waktualny*w+(permutacja[0] - 1)];
            if (trasaaktualna < mintrasa) {	//jesli znalezlismy mniejsza trase
                mintrasa = trasaaktualna;	//aktualizujemy minimalna trase
                for (int i = 0; i < w; i++) {
                    wynik[i] = permutacja[i];	//i aktualizujemy wynik
                }
            }
	}
    }
    
    @Override
    public void displaySolution(){
        int sumasciezka = 0;
	int waktualny = wynik[0] - 1;
	System.out.println("Rozwiazanie: ");
	System.out.print(wynik[0] - 1+"->");
	for (int i = 1; i < w; i++) {
            System.out.print(wynik[i] - 1+"->");
            sumasciezka += macierz[waktualny*w+(wynik[i] - 1)];
            waktualny = wynik[i] - 1;
	}
	sumasciezka += macierz[waktualny*w+(wynik[0] - 1)];
	System.out.print(wynik[0] - 1+"\n");
	System.out.println("Suma sciezki: "+sumasciezka);
    }
    
    @Override
    public void loadFromFile(String fileName) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        int n = Integer.parseInt(br.readLine());
        setVars(n);
        
        String line = "";
        String cvsSplitBy = " ";
        String[] country = null;
        
        for(int i=0;i<w;i++){
            line = br.readLine();
            country = line.split(cvsSplitBy);
            for(int j=0;j<w;j++){
                macierz[i*w+j] = Integer.parseInt(country[j]);
            }
        }
        StringBuilder sb;
        for(int i=0;i<w;i++){
            sb = new StringBuilder();
            for(int j=0;j<w;j++){
                sb.append(macierz[i*w+j]+" ");
            }
            System.out.println(sb);
        }
    }
    
    @Override
    public void setVars(int n){
        w = n;
        macierz = new int[w*w];
	permutacja = new int[w];
	wynik = new int[w];
    }
    
    @Override
    public void algorithmInfo(){
        System.out.print("Przeglad zupelny - algorytm przeglada wszystkie\nmozliwe rozwiazania i wybiera rozwiazanie o najkrotszej\ndrodze.\n");
    }
    
    @Override
    public int[] getWynik(){
        return wynik;
    }
    
    @Override
    public int[] getMacierz(){
        return macierz;
    }
    
    @Override
    public int getW(){
        return w;
    }
}
