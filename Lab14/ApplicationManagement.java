import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.ObjectOutputStream;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.swing.JOptionPane;

public class ApplicationManagement {
    private static final int DEFAULT_NO_THREADS=3;
    private static final int DEFAULT_MEMORY_SIZE=5;
    
    private int takenMemory;
    private long memoryTaken;
    private float errorMemoryPercentage;
    
    private int NO_THREADS;
    private int MEMORY_SIZE;
    
    private long liczbaWczytan = 0;
    private long liczbaAnaliz = 0;
    
    private MBeanServer mbs;
    private Application mBean;
    private ObjectName name;
    
    private boolean zakoncz = false;
    
    private UserDialog UI = new ConsoleUserDialog();
    
    private Mapp mapp;
    
    private ArrayList<WatekStatystyki> ws = new ArrayList <WatekStatystyki>();
    private ArrayList<String> filepaths = new ArrayList<String>();
    
    private static final String MENU_APLIKACJA = 
			"M E N U   G L O W N E \n" +
                        "1 - Uruchom watki \n" +
                        "0 - Zakoncz program          \n"; 
    
    public ApplicationManagement() throws Exception{
        initializeList();
        NO_THREADS = DEFAULT_NO_THREADS;
        MEMORY_SIZE = DEFAULT_MEMORY_SIZE;
        
        mbs = ManagementFactory.getPlatformMBeanServer();
        mBean = new Application(DEFAULT_NO_THREADS, DEFAULT_MEMORY_SIZE);
        name = new ObjectName("jmx:type=Application");
        mbs.registerMBean(mBean, name);
        
        mapp = new Mapp(DEFAULT_MEMORY_SIZE);
        while (true) {
            try {
                switch (UI.enterInt(MENU_APLIKACJA + "==>> ")) {
                case 1:
                    uruchomWatki();
                    break;
                case 0:
                    zakonczWatki();
                    UI.printInfoMessage("\nProgram zakonczyl dzialanie!");
                    System.exit(0);
                }
            } 
            catch (Exception e) {
                UI.printErrorMessage(e.getMessage());
            }
        }
    }
    
    private void uruchomWatki() throws Exception{
        for(int i=0;i<DEFAULT_NO_THREADS;i++){
            ws.add(new WatekStatystyki(mapp, i, filepaths, this));
            ws.get(i).start();
        }
        idle();
    }
    
    private void idle() throws Exception{
        do{
            if(zakoncz)
                break;
            takenMemory = mapp.getMapp().size();
            mBean.setTakenMemory(takenMemory);
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            ObjectOutputStream oos=new ObjectOutputStream(baos);
            oos.writeObject(mapp.getMapp());
            oos.close();
            memoryTaken = baos.size();
            mBean.setMemoryTaken(memoryTaken);
            if(liczbaAnaliz!=0)
                errorMemoryPercentage = ((float)liczbaWczytan/(float)liczbaAnaliz)*(float)100.0;
            else
                errorMemoryPercentage = 0;
            mBean.setErrorMemoryPercentage(errorMemoryPercentage);
            Thread.sleep(1000);
            if(mBean.getThreadNumber()>NO_THREADS){
                ArrayList<WatekStatystyki> ws2 = new ArrayList <WatekStatystyki>();
                for(int j=0;j<ws.size();j++){
                    ws2.add(ws.get(j));
                }
                for(int i=0;i<mBean.getThreadNumber() - NO_THREADS;i++){
                    ws2.add(new WatekStatystyki(mapp, ws.size()+i, filepaths, this));
                    ws2.get(ws.size()+i).start();
                }
                ws = new ArrayList <WatekStatystyki>();
                for(int j=0;j<ws2.size();j++){
                    ws.add(ws2.get(j));
                }
            }
            else if(mBean.getThreadNumber()<NO_THREADS){
                for(int i=0;i<NO_THREADS - mBean.getThreadNumber();i++){
                    ws.get(NO_THREADS-1-i).setZakoncz(true);
                    ws.remove(NO_THREADS-1-i);
                }
            }
            NO_THREADS = mBean.getThreadNumber();
            MEMORY_SIZE = mBean.getMemorySize();
            mapp.setSize(MEMORY_SIZE);
        }while(mBean.getThreadNumber()!=0);
    }
    
    private void zakonczWatki(){
        for(int i=0;i<ws.size();i++){
            ws.get(i).setZakoncz(true);
        }
        zakoncz = true;
    }
    
    private void initializeList(){
        File dir = new File("src/Files");
        File[] directoryListing = dir.listFiles();
        String filename;
        if (directoryListing != null) {
            for (File file : directoryListing) {
                filename = file.getName();
                if(filename.substring(filename.length()-4).equals(".txt"))
                    filepaths.add(filename);
            }
        }
    }
    
    public void addLiczbaWczytan(){
        liczbaWczytan++;
    }
    
    public void addLiczbaAnaliz(){
        liczbaAnaliz++;
    }
    
    public static void main(String[] args) throws Exception {
        new ApplicationManagement();
    }
}