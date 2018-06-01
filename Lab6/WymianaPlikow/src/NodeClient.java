import java.rmi.Naming;
import java.rmi.registry.*;
import javax.swing.JOptionPane;

public class NodeClient {
    
    private static ExchangeInterface look_up;

    public NodeClient(int IDWezla){
        try {
            look_up = (ExchangeInterface) Naming.lookup("//localhost/MyServer"+IDWezla);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
