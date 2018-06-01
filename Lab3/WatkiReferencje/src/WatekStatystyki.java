import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.WeakHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WatekStatystyki extends Thread{

    private WeakHashMapa whm;
    
    private int ziarno;
    
    private boolean zakoncz=false;
    
    private int numerWatku;
    
    private Random rand;
    
    public WatekStatystyki(WeakHashMapa whm, int numerWatku){
        this.whm = whm;
        this.numerWatku = numerWatku;
        rand = new Random();
    }
    
    public void run() {
        synchronized(whm){
            while(true){
                try{
                    Thread.sleep(400+10*numerWatku);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                ziarno = rand.nextInt(10001);
                StringBuilder sb = new StringBuilder();
                try {
                    if(whm.putWHM(ziarno)){
                        Date nowTime = new Date();
                        sb.append(nowTime.toString()+": Watek nr. "+numerWatku+" Nowa kolekcja umieszczona w pamieci podrecznej. Ziarno="+ziarno);
                    }
                    else{
                        Date nowTime = new Date();
                        sb.append(nowTime.toString()+": Watek nr. "+numerWatku+" Kolekcja o ziarnie="+ziarno+" istnieje juz w pamieci podrecznej");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    logWrite(sb.toString());
                    System.out.println(statystyki(whm.getValuesFromKey(ziarno)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(sb.toString());
                if(zakoncz)
                    return;
            }
        }
    }
    
    public StringBuilder statystyki(double[] wartosci) throws IOException{
        StringBuilder sb = new StringBuilder();
        double suma=0;
        double srednia=0;
        for(int i=0;i<wartosci.length;i++){
            suma+=wartosci[i];
        }
        srednia=suma/(double)wartosci.length;
        Date nowTime = new Date();
        sb.append(nowTime.toString()+": Watek nr. "+numerWatku+" STATYSTYKI: Srednia: "+srednia);
        logWrite(sb.toString());
        return sb;
    }
    
    public void logWrite(String message) throws IOException{
        List<String> line = Arrays.asList(message);
        File file = new File("Watek"+numerWatku+".txt");
        String absolutePath = file.getAbsolutePath();
        Path path = Paths.get(absolutePath);
        Files.write(path, line, StandardOpenOption.APPEND);
    }
    
    public void setZakoncz(boolean zakoncz) {
        this.zakoncz = zakoncz;
    }
    
}