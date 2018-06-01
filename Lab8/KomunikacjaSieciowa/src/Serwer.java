
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import javax.xml.soap.*;
import org.w3c.dom.Element;

public class Serwer extends Thread {
    private int port;
    
    private ClientAppFrame klient;
    
    private MessageFactory factory;
    private SOAPConnection con;
    
    public Serwer(int port, ClientAppFrame klient){
        this.port = port;
        this.klient = klient;
    }
    
    public void run(){
        try {
            System.out.println("Otwarty serwer na porcie " + port);
            
            factory = MessageFactory.newInstance();
            con = SOAPConnectionFactory.newInstance().createConnection();
            
            ServerSocket serv = new ServerSocket(port);
            Socket sock;
            while(true){
                sock = serv.accept();
                System.out.println("Polaczono z serwerem " + port);
                
                BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream())); 
                String inputLine = in.readLine(); 
                InputStream is = new ByteArrayInputStream(inputLine.getBytes());
                SOAPMessage myMsg = factory.createMessage(null, is);
                SOAPPart myPart = myMsg.getSOAPPart();
                SOAPEnvelope myEnv = myPart.getEnvelope();
                
                SOAPHeader myHeader = myEnv.getHeader();
                Iterator itr = myHeader.getChildElements();
                SOAPHeaderElement headerElement = (SOAPHeaderElement)itr.next();
                String hostN = headerElement.getNamespaceURI();
                headerElement = (SOAPHeaderElement)itr.next();
                String hostO = headerElement.getNamespaceURI();
                headerElement = (SOAPHeaderElement)itr.next();
                String adres = headerElement.getNamespaceURI();
                //System.out.println(hostN+" "+hostO+" "+adres);
                
                SOAPBody myBody = myEnv.getBody();
                Iterator itr2 = myBody.getChildElements();
                SOAPBodyElement bodyElement = (SOAPBodyElement)itr2.next();
                String msg = bodyElement.getNamespaceURI();
                //System.out.println("Message received at port "+port+" is "+msg);
                
                klient.sendInfo(hostN, hostO, adres, msg);
            }
        } catch (Exception ex) {
            System.err.print(ex.toString());
        }
    }
}