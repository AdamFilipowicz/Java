
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.xml.soap.*;

public class ClientAppFrame extends JFrame {
    
    private int portSerwera;
    private int[] porty;
    private String[] nazwy;
    private String nazwa;
    private String host = "127.0.0.1";
    
    private List<String> nadawc = new ArrayList<String>();
    private List<String> wiadomosc = new ArrayList<String>();
    
    private MessageFactory factory;
    private SOAPConnection con;
    
    private Serwer server;
    
    public ClientAppFrame(){
        
    }
    
    public ClientAppFrame(int portSerwera, String nazwa, int[] porty, String[] nazwy) throws IOException, SOAPException, InterruptedException {
        initComponents();
        factory = MessageFactory.newInstance();
        con = SOAPConnectionFactory.newInstance().createConnection();
        this.portSerwera = portSerwera;
        this.nazwa = nazwa;
        this.porty = porty;
        this.nazwy = nazwy;
        
        portLabel.setText("Port" + portSerwera);
        nameLabel.setText("Nazwa: " + nazwa);
        load();
        
        server = new Serwer(portSerwera, this);
        server.start();
    }
    
    public void connect(int portt, String msg, String odbiorca, String nadawca, String adresacja) throws IOException, SOAPException{
        Socket sock = new Socket(host,portt);
        System.out.println("Klient "+nazwa+" polaczyl sie do portu " + portt);
        System.out.println(adresType.getItemAt(adresType.getSelectedIndex()));
        SOAPMessage myMsg = factory.createMessage();
        SOAPPart myPart = myMsg.getSOAPPart();
        SOAPEnvelope myEnv = myPart.getEnvelope();
        SOAPBody myBody = myEnv.getBody();
        SOAPHeader myHeader = myEnv.getHeader();
        SOAPHeaderElement hostN = myHeader.addHeaderElement(myEnv.createName("HostNadawca", "", nadawca));
        SOAPHeaderElement hostO = myHeader.addHeaderElement(myEnv.createName("HostOdbiorca", "", odbiorca));
        SOAPHeaderElement adres = myHeader.addHeaderElement(myEnv.createName("Adres", "", adresacja));
        SOAPBodyElement element = myBody.addBodyElement(myEnv.createName("Message", "", msg));
        PrintStream out = new PrintStream(sock.getOutputStream(), true);
        myMsg.writeTo(out);
        System.out.println("Message sent to server");
        sock.close();
    }
    
    public void sendInfo(String hostN, String hostO, String adres, String messagee) throws Exception{
        if(!wiadomosc.isEmpty() && !nadawc.isEmpty()){
            if(wiadomosc.get(wiadomosc.size() - 1).equals(messagee) && (nadawc.get(nadawc.size() - 1).equals(hostN) || nadawc.get(nadawc.size() - 1).equals("["+hostN+"]"))){}
            else{
                if(nazwa.equals(hostO)){
                    zapiszDoPliku("C:/Adam/Studia/Programowanie/Java/ProgramowanieWJavie/Lab8/KomunikacjaSieciowa/src/info"+portSerwera+".csv",hostN,messagee);
                }
                else{
                    if(adres.equals("Broadcast") || (adres.equals("Layer broadcast") && nazwa.substring(0,1).equals(hostO.substring(0,1))) ){
                        zapiszDoPliku("C:/Adam/Studia/Programowanie/Java/ProgramowanieWJavie/Lab8/KomunikacjaSieciowa/src/info"+portSerwera+".csv",hostN,messagee);
                    }
                    else{
                        zapiszDoPliku("C:/Adam/Studia/Programowanie/Java/ProgramowanieWJavie/Lab8/KomunikacjaSieciowa/src/info"+portSerwera+".csv","["+hostN+"]",messagee);
                    }
                    sendMessage(messagee, hostO, hostN, adres);
                }
            }
        }
        else{
            if(nazwa.equals(hostO)){
                zapiszDoPliku("C:/Adam/Studia/Programowanie/Java/ProgramowanieWJavie/Lab8/KomunikacjaSieciowa/src/info"+portSerwera+".csv",hostN,messagee);
            }
            else{
                if(adres.equals("Broadcast") || (adres.equals("Layer broadcast") && nazwa.substring(0,1).equals(hostO.substring(0,1))) ){
                    zapiszDoPliku("C:/Adam/Studia/Programowanie/Java/ProgramowanieWJavie/Lab8/KomunikacjaSieciowa/src/info"+portSerwera+".csv",hostN,messagee);
                }
                else{
                    zapiszDoPliku("C:/Adam/Studia/Programowanie/Java/ProgramowanieWJavie/Lab8/KomunikacjaSieciowa/src/info"+portSerwera+".csv","["+hostN+"]","Wiadomosc przeslana dalej - nie przeznaczona dla danego hosta");
                }
                sendMessage(messagee, hostO, hostN, adres);
            }
        }
    }
    
    /**
     * Metoda wczytujaca z pliku .csv informacje o notatkach.
     * @param fileName - nazwa pliku do wczytania informacji
     * @throws Exception - wyjatek przy wczytywaniu z pliku
     */
    public void wczytajZPliku(String fileName) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line = "";
        String cvsSplitBy = ";";
        String[] country = new String[2];
        nadawc = new ArrayList<String>();
        wiadomosc = new ArrayList<String>();
        while ((line = br.readLine()) != null && !line.equals("")) {
            country = line.split(cvsSplitBy);
            nadawc.add(country[0]);
            wiadomosc.add(country[1]);
        }
    }
    
    void load(){
        try {
            wczytajZPliku("C:/Adam/Studia/Programowanie/Java/ProgramowanieWJavie/Lab8/KomunikacjaSieciowa/src/info"+portSerwera+".csv");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.toString(), "", JOptionPane.ERROR_MESSAGE);
        }
        list.setListData(new String[0]);
        String[] listData = new String[nadawc.size()];
        for(int i=0; i<nadawc.size();i++){
            listData[i] = nadawc.get(i);
        }
        list.setListData(listData);
    }
    
    /**
     * Metoda wczytujaca z pliku .csv informacje o notatkach.
     * @param fileName - nazwa pliku do wczytania informacji
     * @throws Exception - wyjatek przy wczytywaniu z pliku
     */
    void zapiszDoPliku(String fileName, String nadawca, String tresc) throws Exception {
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)));
        tresc = tresc.replace("\n", "\\ENDL13531");
        pw.println(nadawca+";"+tresc);
        pw.close();
        load();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        nameText = new javax.swing.JTextField();
        adresType = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        sendMessageText = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        textArea = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        list = new javax.swing.JList<>();
        jLabel3 = new javax.swing.JLabel();
        portLabel = new javax.swing.JLabel();
        nameLabel = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Nazwa odbiorcy:");

        jLabel2.setText("Tryb adresacji");

        adresType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Unicast", "Layer broadcast", "Broadcast" }));

        sendMessageText.setColumns(20);
        sendMessageText.setRows(5);
        jScrollPane1.setViewportView(sendMessageText);

        jButton1.setText("Wyslij wiadomosc");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jScrollPane2.setEnabled(false);

        textArea.setEditable(false);
        textArea.setColumns(20);
        textArea.setRows(5);
        jScrollPane2.setViewportView(textArea);

        jScrollPane3.setViewportView(list);

        jLabel3.setText("Odebrane wiadomosci");

        portLabel.setText("Port");

        nameLabel.setText("Nazwa:");

        jButton2.setText("Pokaz tresc");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(adresType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(nameText))))
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(portLabel)
                            .addComponent(nameLabel))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(67, 67, 67))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane2)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addGap(76, 76, 76))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel1)
                                    .addComponent(nameText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(adresType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(portLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(nameLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton2)
                                .addGap(72, 72, 72)))
                        .addGap(0, 92, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(adresType.getItemAt(adresType.getSelectedIndex()).equals("Layer broadcast")){
            if(nazwa.substring(0,1).equals(nameText.getText().substring(0,1))){
                if(nazwa.substring(1,2).equals("1")){
                    sendMessage(sendMessageText.getText(),nameText.getText().substring(0,1)+"5",nazwa,adresType.getItemAt(adresType.getSelectedIndex()));
                }
                else{
                    int poprzedni = Integer.parseInt(nazwa.substring(1,2)) - 1;
                    sendMessage(sendMessageText.getText(),nameText.getText().substring(0,1)+poprzedni,nazwa,adresType.getItemAt(adresType.getSelectedIndex()));
                }
            }
            else{
                sendMessage(sendMessageText.getText(),nameText.getText().substring(0,1)+"5",nazwa,adresType.getItemAt(adresType.getSelectedIndex()));
            }
        }
        else if(adresType.getItemAt(adresType.getSelectedIndex()).equals("Broadcast")){
            sendMessage(sendMessageText.getText(),"D1",nazwa,adresType.getItemAt(adresType.getSelectedIndex()));
        }
        else{
            sendMessage(sendMessageText.getText(),nameText.getText(),nazwa,adresType.getItemAt(adresType.getSelectedIndex()));
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    public void sendMessage(String message, String odbiorca, String nadawca, String adresacja){
        if(!message.equals("") && message!=null){
            for(int i=0;i<nazwy.length;i++){
                try {
                    connect(porty[i],message,odbiorca,nadawca,adresacja);
                } catch (Exception ex) {
                    System.err.print(ex.toString());
                }
            }
        }
    }
    
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int id = list.getSelectedIndex();
        if(id!=-1){
            textArea.setText(wiadomosc.get(id).replace("\\ENDL13531", "\n"));
        }
        else{
            JOptionPane.showMessageDialog(null, "Wybierz wiadomosc");
        }
    }//GEN-LAST:event_jButton2ActionPerformed
  
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ClientAppFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClientAppFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClientAppFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientAppFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClientAppFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> adresType;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JList<String> list;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JTextField nameText;
    private javax.swing.JLabel portLabel;
    private javax.swing.JTextArea sendMessageText;
    private javax.swing.JTextArea textArea;
    // End of variables declaration//GEN-END:variables
}
