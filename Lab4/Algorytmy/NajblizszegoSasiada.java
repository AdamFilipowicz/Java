
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class NajblizszegoSasiada implements Algorytmy {
    private int w;	//liczba wierzcholkow
    private int[] wynik;	//permutacja - wynik dzialania algorytmow
    private int[] permutacja;	//obecna permutacja
    private int[] macierz;      //macierz odleglosci miedzy miastami jako tablica jednowymiarowa
    
    @Override
    public void algorithm(){
        int poczatkowy = (int)Math.random()%w;
        boolean[] odwiedzone = new boolean[w];	//tablica odwiedzonych wierzcholkow
	long minimalnaodleglosc;	//minimalna odleglosc do nastepnego wierzcholka
	int ktorywierzcholek = 0;
	int wierzcholek = poczatkowy;
	for (int i = 0; i < w; i++)
            odwiedzone[i] = false;
	odwiedzone[wierzcholek] = true;
	wynik[0] = poczatkowy + 1;
	for (int i = 1; i < w; i++) {	//w-1 krawedzi
            minimalnaodleglosc = Integer.MAX_VALUE;
            for (int j = 0; j < w; j++) {	//szukamy najblizszego nieodwiedzonego wierzcholka
                if (!odwiedzone[j] && minimalnaodleglosc > macierz[wierzcholek*w+j]) {
                    minimalnaodleglosc = macierz[wierzcholek*w+j];
                    ktorywierzcholek = j;
                }
            }
            wynik[i] = ktorywierzcholek + 1;	//dodajemy wierzcholek do permutacji wyniku
            odwiedzone[ktorywierzcholek] = true;	//oznaczamy wierzcholek jako odwiedzony
            wierzcholek = ktorywierzcholek;	//i przechodzimy do niego
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
        System.out.print("Algorytm najblizszego sasiada - zaczynając od wybranego\nwierzcholka poczatkowego sprawdzane sa wszystkie nieodwiedzone do tej pory\nwierzcholki i wybierana jest najkrotsza sciezka.\nJest to algorytm zachlanny.\n");
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
