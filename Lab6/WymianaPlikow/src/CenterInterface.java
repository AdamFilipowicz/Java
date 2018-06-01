import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CenterInterface extends Remote {
    void registerNode() throws RemoteException;
    void uploadFile(int[] idPlikow, String nazwaPliku) throws RemoteException;
    String downloadFile(int idPeera, String nazwaPliku) throws RemoteException;
    String getPeersForFile(String nazwaPliku) throws RemoteException;
}
