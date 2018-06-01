
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 * Klasa indywidualizacji ziarnka wykresu umożliwiająca edycję wszystkich jego
 * właściwości w jednym oknie dialogowym z zakładkami.
 */
public class ZiarenkoTerminarzCustomizer extends JPanel implements Customizer {

    private static final long serialVersionUID = 1L;
    
    private ZiarenkoTerminarz bean;
    
    private JLabel l1;
    private JTextField titleField;
    private JButton changeTitle;
    
    private PropertyChangeSupport changes = new PropertyChangeSupport(this);

    public ZiarenkoTerminarzCustomizer() {
        
    }
 
    @Override
    public void setObject(Object obj) {
        bean = (ZiarenkoTerminarz) obj;

        l1 = new JLabel("Tytul panelu:");
        add(l1);
        titleField = new JTextField(String.valueOf(bean.getTytulPanelu()), 20);
        add(titleField);
        changeTitle = new JButton("Zmien tytul");
        add(changeTitle);
        
        changeTitle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeTitleButtonActionPerformed(evt);
            }
        });
        
        String tytulPanelu = bean.getTytulPanelu();
        int rozmiarPola = bean.getRozmiarPola();
        String[] ramyCzasowe = bean.getRamyCzasowe();
    }
    
    private void changeTitleButtonActionPerformed(java.awt.event.ActionEvent evt){
        bean.setTytulPanelu(titleField.getText());
    }
    
    @Override
    public void addPropertyChangeListener(PropertyChangeListener l) {
        try{
            changes.addPropertyChangeListener (l);
        }
        catch(Exception e){}
    }
    @Override
    public void removePropertyChangeListener(PropertyChangeListener l) {
        changes.removePropertyChangeListener(l);
    } 
}
