import java.io.File;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class WezelCentralny extends UnicastRemoteObject implements CenterInterface {
    
    private WezelCentralnyF frame;
    private ArrayList<WezelNode> wezlyPosrednie;
    private int iloscWezlow = 0;
    
    private CentralnySerwer serwer;
    private CentralnyKlient klient;
    
    public WezelCentralny(boolean isNeeded) throws RemoteException, MalformedURLException{
        super();
        wezlyPosrednie = new ArrayList<WezelNode>();
        if(isNeeded){
            Naming.rebind("//localhost/MyServer", new WezelCentralny(false));
            serwer = new CentralnySerwer();
            klient = new CentralnyKlient();
            frame = new WezelCentralnyF();
            frame.setResizable(false);
            frame.setVisible(true);
        }
    }

    @Override
    public void registerNode() throws RemoteException {
        WezelNode nowy;
        try {
            nowy = new WezelNode(iloscWezlow, true);
            iloscWezlow++;
            wezlyPosrednie.add(nowy);
        } catch (Exception e) {
            System.err.println("BLAD - Nieudane utworzenie serwera lub klienta" + e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public void uploadFile(int[] idPlikow, String nazwaPliku) throws RemoteException {
        if(nazwaPliku!=null && !nazwaPliku.equals("")){
            File file = new File("src/WezelGlowny/"+nazwaPliku+".txt");
            if(file.exists()){
                for(int i=0;i<3;i++){
                    wezlyPosrednie.get(idPlikow[i]).acceptFileChunk(nazwaPliku, i+1);
                }
            }
            else{
                System.err.println("Podany plik nie istnieje");
            }
        }
    }
    
    @Override
    public String downloadFile(int idPeera, String nazwaPliku) throws RemoteException {
        return wezlyPosrednie.get(idPeera).getFileChunk(nazwaPliku);
    }

    @Override
    public String getPeersForFile(String nazwaPliku) throws RemoteException {
        File folder = new File("src/");
        File[] listOfFiles = folder.listFiles();
        
        StringBuilder sb = new StringBuilder();
        
        int licznik = 0;
        int wezel;
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isDirectory()) {
                if(listOfFiles[i].toString().substring(listOfFiles[i].toString().length()-6, listOfFiles[i].toString().length()).equals("wywane")){
                    File folder2 = new File(listOfFiles[i].toString()+"/");
                    File[] listOfFiles2 = folder2.listFiles();
                    for (int j = 0; j < listOfFiles2.length; j++) {
                        if(listOfFiles2[j].toString().substring(24, listOfFiles2[j].toString().length()-5).equals(nazwaPliku)){
                            licznik++;
                            sb.append(Integer.parseInt(listOfFiles2[j].toString().substring(9,10)));
                            if(licznik!=3)
                                sb.append(",");
                        }
                    }
                }
            }
        }
        return sb.toString();
    }
}
