
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import javax.xml.soap.SOAPException;


public class KomunikacjaSieciowa {

    public static void main(String[] args) throws UnknownHostException, MalformedURLException, IOException, SOAPException {
        ClientAppFrame[] k = new ClientAppFrame[15];
        int[] porty = {9001,9002,9003,9004,9005,9006,9007,9008,9009,9010,9011,9012,9013,9014,9015};
        String[] nazwy = {"A1","A2","A3","A4","A5","B1","B2","B3","B4","B5","C1","C2","C3","C4","C5"};
        int[][] portyK = new int[][]{{9002,9006},{9003},{9004},{9005},{9001},{9001,9007,9011},{9008},{9009},{9010},{9001},{9012,9006},{9013},{9014},{9015},{9011}};
        String[][] nazwyK = {{"A2","B1"},{"A3"},{"A4"},{"A5"},{"A1"},{"A1","B2","C1"},{"B3"},{"B4"},{"B5"},{"B1"},{"C2","B1"},{"C3"},{"C4"},{"C5"},{"C1"}};
        
        for(int i=0;i<15;i++){
            try {
                k[i] = new ClientAppFrame(porty[i], nazwy[i],portyK[i],nazwyK[i]);
            } catch (Exception ex) {
                System.err.print(ex.toString());
                continue;
            }
            k[i].setResizable(false);
            k[i].setVisible(true);
        }
    }
    
}
