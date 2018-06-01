
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class BestOf implements Algorytmy{
    
    private int w;
    private int[] wynik1;	
    private int[] macierz1; 
    private int[] wynik2;
    private int[] macierz2; 
    
    private PrzegladZupelny pz;
    private NajblizszegoSasiada ns;
    
    @Override
    public void algorithm(){
        pz.algorithm();
        ns.algorithm();
        wynik1=pz.getWynik();
        macierz1=pz.getMacierz();
        wynik2=ns.getWynik();
        macierz2=ns.getMacierz();
        w=ns.getW();
    }
    
    @Override
    public void displaySolution(){
        int sumasciezka1 = 0;
	int waktualny = wynik1[0] - 1;
	for (int i = 1; i < w; i++) {
            sumasciezka1 += macierz1[waktualny*w+(wynik1[i] - 1)];
            waktualny = wynik1[i] - 1;
	}
	sumasciezka1 += macierz1[waktualny*w+(wynik1[0] - 1)];
        
        int sumasciezka2 = 0;
	waktualny = wynik1[0] - 1;
	for (int i = 1; i < w; i++) {
            sumasciezka2 += macierz2[waktualny*w+(wynik2[i] - 1)];
            waktualny = wynik2[i] - 1;
	}
	sumasciezka2 += macierz2[waktualny*w+(wynik2[0] - 1)];
        
        if(sumasciezka2<sumasciezka1){
            System.out.println("Lepszy jest wynik algorytmu najblizszego sasiada:");
            ns.displaySolution();
        }
        else if(sumasciezka2>sumasciezka1){
            System.out.println("Lepszy jest wynik przegladu zupelnego:");
            pz.displaySolution();
        }
        else{
            System.out.println("Oba algorytmy zwracaja identyczny wynik:");
            pz.displaySolution();
        }
    }
    
    @Override
    public void loadFromFile(String fileName) throws FileNotFoundException, IOException {
        pz.loadFromFile(fileName);
        ns.loadFromFile(fileName);
    }
    
    @Override
    public void setVars(int n){}
    
    @Override
    public void algorithmInfo(){
        System.out.print("Algorytm best of - porownuje wyniki algorytmow\nnajblizszego sasiada i przegladu zupelnego.\n");
    }

    @Override
    public int[] getWynik() {return null;}

    @Override
    public int[] getMacierz() {return null;}

    @Override
    public int getW() {return 0;}
}
