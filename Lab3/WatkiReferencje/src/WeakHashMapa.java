import java.lang.ref.SoftReference;
import java.util.WeakHashMap;

public class WeakHashMapa {
    private WeakHashMap<Integer, double[]> whm = new WeakHashMap<Integer, double[]>();
    
    private int howMuchDeleted = 0;
    
    private int howMuchCollections = 0;
    
    public synchronized boolean putWHM(int klucz) throws InterruptedException{
        notifyAll();
        wait();
        if(!whm.containsKey(klucz)){
            SoftReference sr = new SoftReference(new Kolekcja(klucz));

            Kolekcja kol = (Kolekcja)sr.get();
            double[] kolekcja=kol.getKolekcja();
            whm.put(klucz, kolekcja);
            howMuchCollections++;
            if(howMuchCollections!=whm.size()){
                howMuchDeleted+=howMuchCollections-whm.size();
                howMuchCollections=whm.size();
            }
            return true;
        }
        return false;
    }
    
    public WeakHashMap<Integer, double[]> getWHM(){
        return whm;
    }
    
    public int getHowMuchDeleted() {
        return howMuchDeleted;
    }
    
    public double[] getValuesFromKey(int klucz){
        return whm.get(klucz);
    }
}
