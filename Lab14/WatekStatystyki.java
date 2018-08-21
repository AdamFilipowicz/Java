import java.util.*;

public class WatekStatystyki extends Thread{

    private Mapp mapp;
    
    private boolean zakoncz=false;
    
    private int numerWatku;
    
    private Random rand;
    
    private String wynik;
    private long help;
    private String metoda;
    
    private final static char[] samogloski = {'a','e','i','o','u','y'}; 
    
    private ArrayList<String> filepaths = new ArrayList<String>();
    
    private ApplicationManagement am;
    
    public WatekStatystyki(Mapp mapp, int numerWatku, ArrayList<String> filepaths, ApplicationManagement am){
        this.mapp = mapp;
        this.numerWatku = numerWatku;
        this.filepaths = filepaths;
        this.am = am;
        rand = new Random();
    }
    
    public void run() {
        synchronized(mapp){
            while(true){
                wynik="";
                metoda="";
                StringBuilder sb = new StringBuilder();
                try {
                    //1. Wątek losuje plik do analizy
                    String filepath = filepaths.get(rand.nextInt(filepaths.size()));
                    if(mapp.putToMap(filepath)){
                        this.sleep(500);
                        am.addLiczbaWczytan();
                    }
                    am.addLiczbaAnaliz();
                    //3. Wylosowanie metody analizy pliku.
                    int ra = rand.nextInt(3);
                    //4. Analiza pliku
                    LinkedHashMap<String, String> mappp = mapp.getMapp();
                    if(ra==0){
                        metoda="Sprawdzenie spoglosek i samoglosek";
                        help = gloski(mapp.getMapp().get(filepath));
                        if(help>0)
                            wynik = "Spolglosek jest wiecej niz samoglosek";
                        else if(help==0)
                            wynik = "Spolglosek jest tyle samo co samoglosek";
                        else
                            wynik = "Samoglosek jest wiecej niz spolglosek";
                    }
                    else if(ra==1){
                        metoda="Histogram wystepowania samoglosek";
                        wynik = histogram(mapp.getMapp().get(filepath));
                    }
                    else{
                        metoda="Najczesciej wystepujace slowo";
                        wynik = "Najczestsze wystepujace slowo: "+najczestsze(mapp.getMapp().get(filepath)).getWord()+"\nIlosc wystapien: "+najczestsze(mapp.getMapp().get(filepath)).getCzestosc();
                    }
                    //5. Wypisaniu wyniku na ekran
                    Date nowTime = new Date();
                    sb.append(nowTime.toString()+"\nWatek nr. "+numerWatku+"\nNazwa pliku: "+filepath+"\nMetoda: "+metoda+"\nWynik metody: "+wynik);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(sb.toString());
                if(zakoncz)
                    return;
            }
        }
    }
    
    public void setZakoncz(boolean zakoncz) {
        this.zakoncz = zakoncz;
    }
    
    private long gloski(String memoryText){
        int liczbaSamoglosek=0;
        int liczbaSpolglosek=0;
        for(char gloska : memoryText.toLowerCase().toCharArray()){
            if(gloska=='a'||gloska=='e'||gloska=='y'||gloska=='o'||gloska=='i'||gloska=='u')
                liczbaSamoglosek++;
            else if(gloska=='q'||gloska=='w'||gloska=='r'||gloska=='t'||gloska=='p'||gloska=='s'||gloska=='d'||gloska=='f'||gloska=='g'||gloska=='h'||gloska=='j'||gloska=='k'||gloska=='l'||gloska=='z'||gloska=='x'||gloska=='c'||gloska=='v'||gloska=='b'||gloska=='n'||gloska=='m')
                liczbaSpolglosek++;
        }
        return liczbaSpolglosek-liczbaSamoglosek;
    }
    
    public String histogram(String memoryText){
        int[] samogloskiTab={0,0,0,0,0,0};
        for(char gloska : memoryText.toLowerCase().toCharArray()){
            for(int i=0;i<6;i++){
                if(gloska==samogloski[i]){
                    samogloskiTab[i]++;
                    break;
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        for(int i=0;i<6;i++){
            sb.append(samogloski[i]);
            for(int j=0;j<samogloskiTab[i];j++){
                sb.append('*');
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    
    private Najczestszy najczestsze(String memoryText){
        HashMap<String,Integer> mapa = new HashMap<String,Integer>();
        String[] splited = memoryText.toLowerCase().split(" ");
        char substr;
        int max=0;
        String wordd="";
        if(splited.length!=0){
            max = 1;
            substr = splited[0].charAt(splited[0].length()-1);
            if(!Character.isLowerCase(substr))
                splited[0]=splited[0].substring(0, splited[0].length()-1);
            wordd = splited[0];
        }
        for(String word : splited){
            if(word.length()==0)
                continue;
            substr = word.charAt(word.length()-1);
            if(!Character.isLowerCase(substr))
                word=word.substring(0, word.length()-1);
            if(mapa.containsKey(word))
                mapa.put(word, mapa.get(word)+1);
            else
                mapa.put(word, 1);
            if(mapa.get(word)>max){
                max = mapa.get(word);
                wordd = word;
            }
        }
        Najczestszy n = new Najczestszy(wordd, max);
        
        return n;
    }
}