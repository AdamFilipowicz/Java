/*
 *  Demonstracja dzialania nastepujacych kolekcji:
 *  vector, arrayList, linkedList, hashSet, treeSet.
 *
 *  Autor: Adam Filipowicz
 *  Data: 02 12 2016 r.
 */

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PorownajKolekcje extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	Vector<String> vector = new Vector<String>();
	ArrayList<String> arrayList = new ArrayList<String>();
	LinkedList<String> linkedList = new LinkedList<String>();
	HashSet<String> hashSet = new HashSet<String>();
	TreeSet<String> treeSet = new TreeSet<String>();

    WidokKolekcji widokVector;
    WidokKolekcji widokArrayList;
    WidokKolekcji widokLinkedList;
    WidokKolekcji widokHashSet;
    WidokKolekcji widokTreeSet;
    
	JLabel etykieta_tytulu   = new JLabel("Tytul:");
	JTextField pole_tytul    = new JTextField(10);
	JButton przycisk_dodaj   = new JButton("Dodaj");
	JButton przycisk_usun    = new JButton("Usun");
	JButton przycisk_wyczysc = new JButton("Wyczysc");
	JButton przycisk_sortuj  = new JButton("Sortuj listy");
	JButton przycisk_autor   = new JButton("Autor");


	public PorownajKolekcje() {
		super("Porownanie dzialania kolekcji");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(600, 500);

		JPanel panel = new JPanel();
		panel.add(etykieta_tytulu);
		panel.add(pole_tytul);
		przycisk_dodaj.addActionListener(this);
		panel.add(przycisk_dodaj);

		przycisk_usun.addActionListener(this);
		panel.add(przycisk_usun);

		przycisk_wyczysc.addActionListener(this);
		panel.add(przycisk_wyczysc);

		przycisk_sortuj.addActionListener(this);
		panel.add(przycisk_sortuj);
		
		przycisk_autor.addActionListener(this);
		panel.add(przycisk_autor);

		widokVector = new WidokKolekcji(vector, 150, 200, "vector:");
		panel.add(widokVector);

		widokArrayList = new WidokKolekcji(arrayList, 150, 200, "arrayList:");
		panel.add(widokArrayList);

		widokLinkedList = new WidokKolekcji(linkedList, 150, 200, "linkedList:");
		panel.add(widokLinkedList);

		widokHashSet = new WidokKolekcji(hashSet, 150, 200, "hashSet:");
		panel.add(widokHashSet);

		widokTreeSet = new WidokKolekcji(treeSet, 150, 200, "treeSet:");
		panel.add(widokTreeSet);
		
		setContentPane(panel);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		String tytul;
		Object zrodlo = evt.getSource();

		if (zrodlo == przycisk_dodaj) {
			tytul = pole_tytul.getText();
			if (!tytul.equals("")) {
				vector.add(tytul);
				arrayList.add(tytul);
				linkedList.add(tytul);
				hashSet.add(tytul);
				treeSet.add(tytul);
			}
		}

		if (zrodlo == przycisk_wyczysc) {
			vector.clear();
			arrayList.clear();
			linkedList.clear();
			hashSet.clear();
			treeSet.clear();
		}

		if (zrodlo == przycisk_usun) {
			tytul = pole_tytul.getText();
			vector.remove(tytul);
			arrayList.remove(tytul);
			linkedList.remove(tytul);
			hashSet.remove(tytul);
			treeSet.remove(tytul);
		}
		
		if (zrodlo == przycisk_sortuj) {
			Collections.sort(vector);
			Collections.sort(arrayList);
			Collections.sort(linkedList);
		}

		if (zrodlo == przycisk_autor) {
			JOptionPane.showMessageDialog(this,"Autor: Adam Filipowicz\nData: 02.12.2016 r.");
		}

		widokVector.refresh();
		widokArrayList.refresh();
		widokLinkedList.refresh();
		widokHashSet.refresh();
		widokTreeSet.refresh();
	}


	public static void main(String[] args) {
		new PorownajKolekcje();
	}
}



class WidokKolekcji extends JScrollPane {
	private static final long serialVersionUID = 1L;

	private JTable tabela;
	private DefaultTableModel modelTabeli;
	private Collection<String> collection;

	WidokKolekcji(Collection<String> collection, int szerokosc, int wysokosc, String opis) {
		String[] kolumny = { "Tytuly:" };
			modelTabeli = new DefaultTableModel(kolumny, 0);
			tabela = new JTable(modelTabeli);
			tabela.setRowSelectionAllowed(false);
			this.collection = collection;
			setViewportView(tabela);
			setPreferredSize(new Dimension(szerokosc, wysokosc));
			setBorder(BorderFactory.createTitledBorder(opis));
		}
	
		void refresh(){
	    	modelTabeli.setRowCount(0);
			for (String tytul : collection.toArray(new String[0])) {
				String[] wiersz = { tytul };
				modelTabeli.addRow(wiersz);
	    	}
	    }

}
