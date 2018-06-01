import java.rmi.Naming;
import java.rmi.registry.*;
import javax.swing.JOptionPane;

public class CentralnyKlient {
    
    private static CenterInterface look_up;

    public CentralnyKlient(){
        try {
            look_up = (CenterInterface) Naming.lookup("//localhost/MyServer");
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
