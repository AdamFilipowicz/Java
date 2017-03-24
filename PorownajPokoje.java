/*
 *  Demonstracja dzialania nastepujacych kolekcji:
 *  vector, arrayList, linkedList, hashSet, treeSet.
 *
 *  Autor: Adam Filipowicz
 *  Data: 03 12 2016 r.
 */

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class PorownajPokoje extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	Vector<Room> vector = new Vector<Room>();
	ArrayList<Room> arrayList = new ArrayList<Room>();
	LinkedList<Room> linkedList = new LinkedList<Room>();
	HashSet<Room> hashSet = new HashSet<Room>();
	TreeSet<Room> treeSet = new TreeSet<Room>();

    WidokKolekcji widokVector;
    WidokKolekcji widokArrayList;
    WidokKolekcji widokLinkedList;
    WidokKolekcji widokHashSet;
    WidokKolekcji widokTreeSet;
    
	JLabel etykieta_budynku   = new JLabel("Budynek:");
	JTextField pole_budynek    = new JTextField(10);
	JLabel etykieta_pokoju  = new JLabel("Pokoj:");
	JTextField pole_pokoj    = new JTextField(10);
	JLabel etykieta_opisu   = new JLabel("Opis:");
	JTextField pole_opis    = new JTextField(10);
	JButton przycisk_dodaj   = new JButton("Dodaj");
	JButton przycisk_usun    = new JButton("Usun");
	JButton przycisk_wyczysc = new JButton("Wyczysc");
	JButton przycisk_sortuj  = new JButton("Sortuj listy");
	JButton przycisk_autor   = new JButton("Autor");


	public PorownajPokoje() {
		super("Porownanie dzialania kolekcji");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1000, 500);

		JPanel panel = new JPanel();
		panel.add(etykieta_budynku);
		panel.add(pole_budynek);
		panel.add(etykieta_pokoju);
		panel.add(pole_pokoj);
		panel.add(etykieta_opisu);
		panel.add(pole_opis);
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

		widokVector = new WidokKolekcji(vector, 250, 200, "vector:");
		panel.add(widokVector);

		widokArrayList = new WidokKolekcji(arrayList, 250, 200, "arrayList:");
		panel.add(widokArrayList);

		widokLinkedList = new WidokKolekcji(linkedList, 250, 200, "linkedList:");
		panel.add(widokLinkedList);

		widokHashSet = new WidokKolekcji(hashSet, 250, 200, "hashSet:");
		panel.add(widokHashSet);

		widokTreeSet = new WidokKolekcji(treeSet, 250, 200, "treeSet:");
		panel.add(widokTreeSet);
		
		setContentPane(panel);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		String budynek,opis;
		int pokoj;
		Object zrodlo = evt.getSource();

		if (zrodlo == przycisk_dodaj) {
			budynek = pole_budynek.getText();
			opis = pole_opis.getText();
			if (!budynek.equals("") && !pole_pokoj.getText().equals("")) {
				pokoj = Integer.parseInt(pole_pokoj.getText());
				vector.add(new Room(budynek,pokoj,opis));
				arrayList.add(new Room(budynek,pokoj,opis));
				linkedList.add(new Room(budynek,pokoj,opis));
				hashSet.add(new Room(budynek,pokoj,opis));
				treeSet.add(new Room(budynek,pokoj,opis));
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
			budynek = pole_budynek.getText();
			opis = pole_opis.getText();
			if(!budynek.equals("") && !pole_pokoj.getText().equals("")) {
				pokoj = Integer.parseInt(pole_pokoj.getText());
				vector.remove(new Room(budynek,pokoj,opis));
				arrayList.remove(new Room(budynek,pokoj,opis));
				linkedList.remove(new Room(budynek,pokoj,opis));
				hashSet.remove(new Room(budynek,pokoj,opis));
				treeSet.remove(new Room(budynek,pokoj,opis));
			}
		}
		
		if (zrodlo == przycisk_sortuj) {
			Collections.sort(vector);
			Collections.sort(arrayList);
			Collections.sort(linkedList);
		}

		if (zrodlo == przycisk_autor) {
			JOptionPane.showMessageDialog(this,"Autor: Adam Filipowicz\nData: 03.12.2016 r.");
		}

		widokVector.refresh();
		widokArrayList.refresh();
		widokLinkedList.refresh();
		widokHashSet.refresh();
		widokTreeSet.refresh();
	}


	public static void main(String[] args) {
		new PorownajPokoje();
	}
}

class Room implements Comparable<Room>{
	private String budynek;
	private int pokoj;
	private String opis;
	
	Room(){
		this.budynek="";
		this.pokoj=0;
		this.opis="";
	}
	
	Room(String s, int n, String o){
		this.budynek=s;
		this.pokoj=n;
		this.opis=o;
	}
	
	public String getBudynek(){
		return budynek;
	}
	
	public int getPokoj(){
		return pokoj;
	}
	
	public String getOpis(){
		return opis;
	}
	
	//17 i 31 to liczby pierwsze - pomysl zaczerpniety z ksiazki
	@Override
	public int hashCode(){
        int result = 17;
        result = 31 * result + budynek.hashCode();
        result = 31 * result + pokoj;
        return result;
	}
	
	@Override
	public boolean equals(Object other){
		if (!(other instanceof Room))
            return false;
        if (other == this)
            return true;
	    Room pokojj = (Room) other;
        if (!this.budynek.equals(pokojj.budynek))
            return this.budynek.equals(pokojj.budynek);
        return this.pokoj==pokojj.pokoj;
	}
	
	@Override
	public int compareTo(Room pokojj){
        if (!this.budynek.equals(pokojj.budynek))
            return this.budynek.compareTo(pokojj.budynek);
        if (!this.budynek.equals(pokojj.budynek))
            return this.budynek.compareTo(pokojj.budynek);
        return this.pokoj - pokojj.pokoj;
	}
	
    @Override
    public String toString(){
        return budynek+"/"+pokoj+" : "+opis;
    }
}

class WidokKolekcji extends JScrollPane {
	private static final long serialVersionUID = 1L;

	private JTable tabela;
	private DefaultTableModel modelTabeli;
	private Collection<Room> collection;

	WidokKolekcji(Collection<Room> collection, int szerokosc, int wysokosc, String opis) {
		String[] kolumny = { "Budynek:" , "Pokoj:" , "Opis:"};
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
			for (Room pokoj : collection) {
				String budynekk = pokoj.getBudynek();
				int pokojj = pokoj.getPokoj();
				String opiss = pokoj.getOpis();
				String[] wiersz = {budynekk, Integer.toString(pokojj), opiss};
				modelTabeli.addRow(wiersz);
	    	}
	    }

}
