
import java.awt.Image;
import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;
import java.text.ChoiceFormat;
import java.text.DateFormat;
import java.text.Format;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;

/*
 *  Klasa Okienko
 *
 *  Klasa pozwala na wybor menu klienta lub sprzedawcy oraz na wyjscie z aplikacji
 *  i zapisanie danych do pliku.
 *
 *  Autor: Adam Filipowicz
 *  Data: 27 lutego 2018 r.
 */
public class Okienko extends javax.swing.JFrame {

    private JOptionPaneUserDialog UI = new JOptionPaneUserDialog();
    
    private String nazwa;
    private String data;
    private float cena;
    private int liczba;
    
    private int ktoryFilm;
    
    private Locale loc;
    private ResourceBundle rb;
    private ResourceBundle cb;
    private MessageFormat mf;
    private ChoiceFormat cf;
    
    private double[] fileLimits = {0,1,2};
    private String[] fileStrings = new String[3];
    
    /**
     * Creates new form Okienko
     */
    public Okienko() {
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/ja.jpg")).getImage());
        
        ktoryFilm=1;
        loc = new Locale("pl", "PL");
        rb = ResourceBundle.getBundle("MessagesBundle", loc);
        cb = ResourceBundle.getBundle("ChoiceBundle", loc);
        
        fileStrings[0] = cb.getString("noMov");
        fileStrings[1] = cb.getString("oneMov");
        fileStrings[2] = cb.getString("moreMov");
        
        mf = new MessageFormat("");
        mf.setLocale(loc);
        cf = new ChoiceFormat(fileLimits, fileStrings);
        
        String pattern = cb.getString("liczba");
        mf.applyPattern(pattern);
        
        Format[] formats = {cf, NumberFormat.getInstance()};
        mf.setFormats(formats);
        
        initComponents();
        zaladujFilm(ktoryFilm);
    }

    
    
    public void zaladujFilm(int ktory){
        ImageIcon img = new ImageIcon(getClass().getResource("/img/Produkt"+ktoryFilm+"/zdj.jpg"));
        Image image = img.getImage(); // transform it 
        Image newimg = image.getScaledInstance(jLabel1.getWidth(), jLabel1.getHeight(), java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        img = new ImageIcon(newimg);  // transform it back
        jLabel1.setIcon(img);
        try {
            wczytajZPliku("C:/Adam/Wypozyczalnia/src/img/Produkt"+ktoryFilm+"/info.csv");
        } catch (Exception e) {
            UI.printErrorMessage(e.getMessage());
        }
        jLabel2.setText(rb.getString("nazwa")+" "+nazwa);
        try {
            jLabel4.setText(rb.getString("data")+" "+zmianaDaty(data));
        } catch (Exception e) {
            UI.printErrorMessage(e.getMessage());
        }
        jLabel5.setText(rb.getString("cena")+" "+zmianaKwoty(cena));
        String result = null;
        if(loc.getDisplayName().equals("polski (Polska)")){
            Object[] messageArguments = {null, zmianaLiczby(liczba)};
            if(liczba==1)
                messageArguments[0]=1;
            else if(liczba%10>=2 && liczba%10<=4 && (liczba%100>20 || liczba%100<10))
                messageArguments[0]=2;
            else
                messageArguments[0]=0;
            result = mf.format(messageArguments);
        }
        else{
            Object[] messageArguments = {null, zmianaLiczby(liczba)};
            if(liczba==0)
                messageArguments[0]=0;
            else if(liczba==1)
                messageArguments[0]=1;
            else
                messageArguments[0]=2;
            result = mf.format(messageArguments);
        }
        jLabel7.setText(result);
    }
    
    /**
     * Metoda wczytujaca z pliku .csv informacje o filmie.
     * @param fileName - nazwa pliku do wczytania informacji
     * @throws Exception - wyjatek przy wczytywaniu z pliku
     */
    public void wczytajZPliku(String fileName) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line = "";
        String cvsSplitBy = ";";
        String[] country=null;
        String dataa;
        if((line = br.readLine()) != null)
            country = line.split(cvsSplitBy);
        while ((line = br.readLine()) != null) {
            country = line.split(cvsSplitBy);
            nazwa=country[0];
            data=country[1];
            cena=Float.parseFloat(country[2]);
            liczba=Integer.parseInt(country[3]);
        }
    }
    
    private String zmianaLiczby(int liczba) {
        NumberFormat numberFormatter = NumberFormat.getNumberInstance(loc);
        return numberFormatter.format(liczba);
    }
    
    private String zmianaKwoty(float kwota){
        Currency currentCurrency = Currency.getInstance(loc);
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(loc);
        if(currentCurrency.getDisplayName().equals("US Dollar"))
            kwota*=0.29324;
        if(currentCurrency.getDisplayName().equals("British Pound Sterling"))
            kwota*=0.210630657;
        return currencyFormatter.format(kwota);
    }
    
    private String zmianaDaty(String dataa) throws ParseException{
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, loc);
        dataa = dataa.replace(".", "/");
        DateFormat df2 = new SimpleDateFormat("dd/MM/yy", loc);
        return df.format(df2.parse(dataa));
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
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setText("jLabel2");

        jLabel4.setText("jLabel2");

        jButton1.setText("Poprzedni film");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Nastepny film");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel5.setText("jLabel2");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "pl/PL", "en/US", "en/GB" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel6.setText("Zmiana jezyka");

        jLabel7.setText("jLabel2");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel7)))
                            .addComponent(jButton2)
                            .addComponent(jButton1))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 163, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(175, 175, 175))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel7)
                                .addGap(56, 56, 56))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)
                        .addGap(12, 12, 12)))
                .addContainerGap(38, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(ktoryFilm>1){
            zaladujFilm(--ktoryFilm);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try{
            zaladujFilm(++ktoryFilm);
        }catch(Exception e){
            ktoryFilm--;
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        JComboBox cbox = (JComboBox)evt.getSource();
        String name = (String)cbox.getSelectedItem();
        String cvsSplitBy = "/";
        String[] country=name.split(cvsSplitBy);
        loc = new Locale(country[0], country[1]);
        rb = ResourceBundle.getBundle("MessagesBundle", loc);
        cb = ResourceBundle.getBundle("ChoiceBundle", loc);
        
        fileStrings[0] = cb.getString("noMov");
        fileStrings[1] = cb.getString("oneMov");
        fileStrings[2] = cb.getString("moreMov");
        
        mf = new MessageFormat("");
        mf.setLocale(loc);
        cf = new ChoiceFormat(fileLimits, fileStrings);
        
        String pattern = cb.getString("liczba");
        mf.applyPattern(pattern);
        
        Format[] formats = {cf, NumberFormat.getInstance()};
        mf.setFormats(formats);
        zmienJezyk();
    }//GEN-LAST:event_jComboBox1ActionPerformed

    
    private void zmienJezyk(){
        zaladujFilm(ktoryFilm);
        jLabel2.setText(rb.getString("nazwa")+" "+nazwa);
        try {
            jLabel4.setText(rb.getString("data")+" "+zmianaDaty(data));
        } catch (Exception e) {
            UI.printErrorMessage(e.getMessage());
        }
        jLabel5.setText(rb.getString("cena")+" "+zmianaKwoty(cena));
        String result = null;
        if(loc.getDisplayName().equals("polski (Polska)")){
            Object[] messageArguments = {null, zmianaLiczby(liczba)};
            if(liczba==1)
                messageArguments[0]=1;
            else if(liczba%10>=2 && liczba%10<=4 && (liczba%100>20 || liczba%100<10))
                messageArguments[0]=2;
            else
                messageArguments[0]=0;
            result = mf.format(messageArguments);
        }
        else{
            Object[] messageArguments = {null, zmianaLiczby(liczba)};
            if(liczba==0)
                messageArguments[0]=0;
            else if(liczba==1)
                messageArguments[0]=1;
            else
                messageArguments[0]=2;
            result = mf.format(messageArguments);
        }
        jLabel7.setText(result);
        jLabel6.setText(rb.getString("zmiana"));
        jButton1.setText(rb.getString("poprzedni"));
        jButton2.setText(rb.getString("nastepny"));
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Okienko().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    // End of variables declaration//GEN-END:variables
}
