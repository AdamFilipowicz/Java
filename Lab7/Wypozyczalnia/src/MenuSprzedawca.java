import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

import javax.swing.JOptionPane;

/*
 *  Klasa MenuSprzedawca
 *
 *  Klasa pozwala na stworzenie konta sprzedawcy, zalogowanie sie 
 *  oraz powrot do glownego menu.
 *
 *  Autor: Adam Filipowicz
 *  Data: 31 maja 2017 r.
 */
public class MenuSprzedawca extends javax.swing.JFrame {
    protected static OpcjeS opcje;
    protected static MenuGlowne glowneMenu;
    private String nazwaKonta;
    protected Wypozyczalnia wypozyczalnia;
    protected static JOptionUserDialog UI = new JOptionUserDialog();
    
    private MenuSprzedawca() {}
    
    public MenuSprzedawca(Wypozyczalnia wypozyczalnia){
        initComponents();
        this.wypozyczalnia = wypozyczalnia;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        wrocSButton = new javax.swing.JButton();
        jTextField9 = new javax.swing.JTextField();
        loginButton = new javax.swing.JButton();
        nazwaText1 = new javax.swing.JTextField();
        hasloText1 = new javax.swing.JPasswordField();
        jTextField10 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(800, 600));

        jTextField1.setEditable(false);
        jTextField1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField1.setText("Menu sprzedawcy");

        wrocSButton.setText("Wroc do menu glownego");
        wrocSButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wrocSButtonActionPerformed(evt);
            }
        });

        jTextField9.setEditable(false);
        jTextField9.setText("Haslo");

        loginButton.setText("Zaloguj sie");
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });

        jTextField10.setEditable(false);
        jTextField10.setText("Nazwa konta");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(284, 284, 284))
            .addGroup(layout.createSequentialGroup()
                .addGap(295, 295, 295)
                .addComponent(wrocSButton)
                .addContainerGap(294, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(hasloText1)
                            .addComponent(nazwaText1, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addComponent(loginButton)))
                .addGap(265, 265, 265))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(108, 108, 108)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nazwaText1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(hasloText1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addComponent(loginButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 112, Short.MAX_VALUE)
                .addComponent(wrocSButton, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Metoda po nacisnieciu guzika wraca do glownego menu.
     * @param evt - nacisniecie guzika powoduje powrot do glownego menu
     */
    private void wrocSButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wrocSButtonActionPerformed
        glowneMenu = new MenuGlowne(wypozyczalnia);
        glowneMenu.setResizable(false);
        glowneMenu.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        glowneMenu.setSize(800,500);
        glowneMenu.setLocation(500,250);
        glowneMenu.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_wrocSButtonActionPerformed

    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginButtonActionPerformed
        try {
            nazwaKonta = wypozyczalnia.znajdzKonto(nazwaText1.getText(),hasloText1.getText());
        } catch (Exception e) {
            UI.printErrorMessage(e.getMessage());
            return;
        }
        if (nazwaKonta == null) {
            UI.printErrorMessage("Bledne dane");
            return;
        }
        try {
            if(!wypozyczalnia.sprawdzCzyKontoSprzedawcy(nazwaKonta)){
                UI.printErrorMessage("Probujesz zalogowac sie na konto inne niż sprzedawcy");
                return;
            }
        } catch (Exception e) {
            UI.printErrorMessage(e.getMessage());
            return;
        }

        try {
            opcje = new OpcjeS(nazwaKonta, wypozyczalnia);
        } catch (Exception e) {
            UI.printErrorMessage(e.getMessage());
            return;
        }
        opcje.setResizable(false);
        opcje.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        opcje.setSize(800,500);
        opcje.setLocation(500,250);
        opcje.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_loginButtonActionPerformed

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
            java.util.logging.Logger.getLogger(MenuSprzedawca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MenuSprzedawca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MenuSprzedawca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MenuSprzedawca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MenuSprzedawca().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPasswordField hasloText1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JButton loginButton;
    private javax.swing.JTextField nazwaText1;
    private javax.swing.JButton wrocSButton;
    // End of variables declaration//GEN-END:variables
}
