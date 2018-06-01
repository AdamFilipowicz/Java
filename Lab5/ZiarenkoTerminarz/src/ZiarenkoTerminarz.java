import java.beans.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.*;

public class ZiarenkoTerminarz extends JPanel {

    private String tytulPanelu = "Terminarz";
    private int rozmiarPola = 160;
    private String[] ramyCzasowe = new String[2];
    
    private List<String> data = new ArrayList<String>();
    private List<String> tytul = new ArrayList<String>();
    private List<String> tresc = new ArrayList<String>();
    
    private PropertyChangeSupport changes = new PropertyChangeSupport(this);
    private VetoableChangeSupport vetoes = new VetoableChangeSupport(this);
    
    public ZiarenkoTerminarz() {
        Date data1 = new Date(System.currentTimeMillis());
        long DAY_IN_MS = 1000 * 60 * 60 * 24;
        Date data2 = new Date(data1.getTime() - (7 * DAY_IN_MS));
        DateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
        ramyCzasowe[1] = df2.format(data1);
        ramyCzasowe[0] = df2.format(data2);
        
        initComponents();
        jLabel1.setText(tytulPanelu);
        date1Text.setText(ramyCzasowe[0].toString());
        date2Text.setText(ramyCzasowe[1].toString());
        load();
        
        setRozmiarPola(70);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        list = new javax.swing.JList<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        date1Text = new javax.swing.JTextField();
        date2Text = new javax.swing.JTextField();
        searchButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        noteText = new javax.swing.JTextArea();
        addButton = new javax.swing.JButton();
        titleText = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        jLabel1.setText("nazwaPanelu");

        list.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(list);

        jLabel2.setText("Data poczatkowa");

        jLabel3.setText("Data koncowa");

        searchButton.setText("Wyszukaj");
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });

        noteText.setColumns(20);
        noteText.setRows(5);
        jScrollPane2.setViewportView(noteText);

        addButton.setText("Dodaj");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        jLabel4.setText("Tytul notatki");

        jLabel5.setText("Tresc notatki");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(228, 228, 228)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(addButton)
                        .addGap(124, 124, 124))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(date1Text, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(date2Text, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(searchButton)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(titleText)))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(date1Text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(date2Text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)
                        .addComponent(searchButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(titleText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(55, 55, 55)
                                .addComponent(jLabel5))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addButton)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1)
                        .addContainerGap())))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        if(date1Text.getText().equals("") || date1Text.getText() == null)
            JOptionPane.showMessageDialog(null, "Data poczatkowa nie moze byc pusta");
        else if(date2Text.getText().equals("") || date2Text.getText() == null)
            JOptionPane.showMessageDialog(null, "Data koncowa nie moze byc pusta");
        else{
            String[] str = {date1Text.getText(), date2Text.getText()};
            try {
                setRamyCzasowe(str);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.toString(), "", JOptionPane.ERROR_MESSAGE);
            }
            load();
        }
    }//GEN-LAST:event_searchButtonActionPerformed

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        if(titleText.getText().equals("") || titleText.getText() == null)
            JOptionPane.showMessageDialog(null, "Tytul notatki nie moze byc pusty");
        else if(noteText.getText().equals("") || noteText.getText() == null)
            JOptionPane.showMessageDialog(null, "Tresc notatki nie moze byc pusta");
        else{
            try {
                zapiszDoPliku("C:/Adam/Studia/Programowanie/Java/ProgramowanieWJavie/Lab5/UsingBeans/src/info.csv", titleText.getText(), noteText.getText());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.toString(), "", JOptionPane.ERROR_MESSAGE);
            }
            load();
        }
    }//GEN-LAST:event_addButtonActionPerformed

    private void listMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listMouseClicked
        int id = list.getSelectedIndex();
        titleText.setText(tytul.get(id));
        noteText.setText(tresc.get(id).replace("\\ENDL13531", "\n"));
    }//GEN-LAST:event_listMouseClicked

    @Override
    public void addPropertyChangeListener (PropertyChangeListener p) {
        try{
            changes.addPropertyChangeListener (p);
        }
        catch(Exception e){}
    }

    @Override
    public void removePropertyChangeListener (PropertyChangeListener p) {
        changes.removePropertyChangeListener (p);
    }
    
    @Override
    public void addVetoableChangeListener (VetoableChangeListener v) {
        vetoes.addVetoableChangeListener (v);
    }
    
    @Override
    public void removeVetoableChangeListener (VetoableChangeListener v) {
        vetoes.removeVetoableChangeListener (v);
    }
    
    public String getTytulPanelu() {
        return tytulPanelu;
    }

    public void setTytulPanelu(String tytulPanelu) {
        this.tytulPanelu = tytulPanelu;
        jLabel1.setText(this.tytulPanelu);
    }
    
    public int getRozmiarPola() {
        return rozmiarPola;
    }

    public void setRozmiarPola(int rozmiarPola) {
        if(rozmiarPola>50 && rozmiarPola<160){
            int staryRozmiar = this.rozmiarPola;
            this.rozmiarPola = rozmiarPola;
            changes.firePropertyChange ("rozmiarPola", (Integer)staryRozmiar, (Integer)this.rozmiarPola);
        }
        else{
            JOptionPane.showMessageDialog(null, "Podaj rozmiar pola pomiedzy 50 i 160", "", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String[] getRamyCzasowe() {
        return ramyCzasowe;
    }

    public void setRamyCzasowe(String[] ramyCzasowe) throws PropertyVetoException {
        String[] stareRamy = this.ramyCzasowe;
        vetoes.fireVetoableChange ("ramyCzasowe", stareRamy, ramyCzasowe);
        this.ramyCzasowe = ramyCzasowe;
        changes.firePropertyChange ("ramyCzasowe", stareRamy, this.ramyCzasowe);  
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
        String[] country = new String[3];
        int i=0;
        data = new ArrayList<String>();
        tytul = new ArrayList<String>();
        tresc = new ArrayList<String>();
        Date dataPor, dataPocz, dataKon;
        DateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
        dataPocz = df2.parse(date1Text.getText());
        dataKon = df2.parse(date2Text.getText());
        while ((line = br.readLine()) != null && !line.equals("")) {
            country = line.split(cvsSplitBy);
            dataPor = df2.parse(country[0]);
            if(dataPor.compareTo(dataPocz)>=0 && dataPor.compareTo(dataKon)<=0){
                data.add(country[0]);
                tytul.add(country[1]);
                tresc.add(country[2]);
            }
            i++;
        }
    }
    
    /**
     * Metoda wczytujaca z pliku .csv informacje o notatkach.
     * @param fileName - nazwa pliku do wczytania informacji
     * @throws Exception - wyjatek przy wczytywaniu z pliku
     */
    void zapiszDoPliku(String fileName, String tytul, String tresc) throws Exception {
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)));
        Date data = new Date();
        DateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
        tresc = tresc.replace("\n", "\\ENDL13531");
        pw.println(df2.format(data)+";"+tytul+";"+tresc);
        pw.close();
    }
    
    void load(){
        try {
            wczytajZPliku("C:/Adam/Studia/Programowanie/Java/ProgramowanieWJavie/Lab5/UsingBeans/src/info.csv");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.toString(), "", JOptionPane.ERROR_MESSAGE);
        }
        list.setListData(new String[0]);
        String[] listData = new String[tytul.size()];
        for(int i=0; i<tytul.size();i++){
            listData[i] = tytul.get(i);
        }
        list.setListData(listData);
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JTextField date1Text;
    private javax.swing.JTextField date2Text;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList<String> list;
    private javax.swing.JTextArea noteText;
    private javax.swing.JButton searchButton;
    private javax.swing.JTextField titleText;
    // End of variables declaration//GEN-END:variables
}
