import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ExchangeInterface extends Remote {
    void acceptFileChunk(String nazwaPliku, int ktoraCzesc) throws RemoteException;
    String getFileChunk(String nazwaPliku) throws RemoteException;
}
