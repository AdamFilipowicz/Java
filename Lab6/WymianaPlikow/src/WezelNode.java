import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

public class WezelNode extends UnicastRemoteObject implements ExchangeInterface{
    
    private int IDWezla;
    
    private WezelNodeF frame;
    private NodeSerwer serwer;
    private NodeClient klient;
    
    private Map<String, Integer> pliki;
    
    public WezelNode(int IDWezla, boolean isNeeded) throws RemoteException, MalformedURLException{
        super();
        if(isNeeded){
            pliki = new HashMap<String, Integer>();
            frame = new WezelNodeF(IDWezla);
            frame.setResizable(false);
            frame.setVisible(true);
            Naming.rebind("//localhost/MyServer"+IDWezla, new WezelNode(IDWezla, false));
            new File("src/Wezel"+IDWezla+"Pobrane").mkdirs();
            new File("src/Wezel"+IDWezla+"Przechowywane").mkdirs();
            this.IDWezla = IDWezla;
            serwer = new NodeSerwer(IDWezla);
            klient = new NodeClient(IDWezla);
            
            wczytajPliki();
        }
    }
    
    @Override
    public void acceptFileChunk(String nazwaPliku, int ktoraCzesc) throws RemoteException {
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/WezelGlowny/"+nazwaPliku+".txt"));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            String tekst = sb.toString();
            int litery = tekst.length()/3;
            String czastka = tekst.substring((ktoraCzesc-1)*litery, ktoraCzesc*litery);

            PrintWriter out = new PrintWriter("src/Wezel"+IDWezla+"Przechowywane/"+nazwaPliku+ktoraCzesc+".txt");
            out.print(czastka);
            out.close();
            wczytajPliki();
        } catch (Exception e) {
            System.err.println("Blad wczytywania pliku");
            return;
        }
    }

    @Override
    public String getFileChunk(String nazwaPliku) throws RemoteException {
        File folder = new File("src/Wezel"+IDWezla+"Przechowywane");
        File[] listOfFiles = folder.listFiles();
        
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                if(listOfFiles[i].toString().substring(24, listOfFiles[i].toString().length()-5).equals(nazwaPliku)){
                    try {
                        byte[] encoded = Files.readAllBytes(Paths.get(listOfFiles[i].toString()));
                        return new String(encoded, StandardCharsets.UTF_8);
                    } catch (Exception e) {
                        System.err.println("Blad wczytywania pliku");
                        return null;
                    }
                }
            }
        }
        return null;
    }
    
    private void wczytajPliki(){
        File folder = new File("src/Wezel"+IDWezla+"Przechowywane");
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                pliki.put(listOfFiles[i].toString().substring(24, listOfFiles[i].toString().length()-5), Integer.parseInt(listOfFiles[i].toString().substring(listOfFiles[i].toString().length()-5, listOfFiles[i].toString().length()-4)));
            }
        }
        
        frame.setList(pliki);
    }
    
}