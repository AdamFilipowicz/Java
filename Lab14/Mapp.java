import java.util.*;

public class Mapp {
    private LinkedHashMap<String, String> mapp = new LinkedHashMap<String, String>();
    
    private int size;
    
    public Mapp(int size){
        this.size = size;
    }
    
    public synchronized boolean putToMap(String filepath) throws InterruptedException{
        notifyAll();
        wait();
        //Jesli w mapie nie ma jeszcze danego pliku.
        if(!mapp.containsKey(filepath)){
            //2. Wątek sprawdza czy dane pliku są w pamięci podręcznej
            //Jesli nie ma juz miejsca w mapie, to usuwany jest losowy element mapy
            while(mapp.size()>=size){
                Random r = new Random();
                int rand = r.nextInt(mapp.size());
                mapp.remove((mapp.keySet().toArray())[rand]);
            }
            //Plik jest wczytany do pamięci podręcznej
            MapItem mapItem = new MapItem(filepath);
            String text = mapItem.getText();
            mapp.put(filepath, text);
            return true;
        }
        return false;
    }
    
    public LinkedHashMap<String, String> getMapp(){
        return mapp;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
