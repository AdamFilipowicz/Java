import javax.swing.JFrame;

/*
 *  Klasa WypozyczalniaAplikacja
 *
 *  Klasa zawiera kilka pól protected ktore sa dziedziczone przez inne klasy okienkowe.
 *  Klasa wczytuje dane z pliku oraz inicjalizuje menu glowne.
 *
 *  Autor: Adam Filipowicz
 *  Data: 27 lutego 2018 r.
 */
public class WypozyczalniaAplikacja extends javax.swing.JFrame {
    protected static Okienko okienko;

    public static void main(String[] args){
        okienko = new Okienko();
        okienko.setResizable(false);
        okienko.setSize(800,500);
        okienko.setLocation(500,250);
        okienko.setVisible(true);
    }
    
}