/*
 * Program sortujacy slowa podane w pliku .txt
 * Do programu dolaczylem plik txt o nazwie mojPlik 
 * z ktorego slowa zostana posortowane.
 *
 *  Autor: Adam Filipowicz
 *  Data: 03 12 2016 r.
 */

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;
import java.io.File;
import javax.swing.table.DefaultTableModel;


public class LiczbaSlow extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private static final File fileName = new File ("mojPlik.txt");
	
	private static TreeMap<String, Integer> mapaSlow = new TreeMap<String, Integer>();
	
	private static Vector<String> wektor = new Vector<String>();
	
	private static Vector<Integer> wektorInt = new Vector<Integer>();

	static final Comparator<String> komparator = new Comparator<String>() {
		public int compare(String x, String y){
			for(int i=0;i<mapaSlow.size();i++){
				wektorInt.set(i,mapaSlow.get(wektor.elementAt(i)));
			}
			int x1 = wektor.indexOf(x);
			int x2 = wektor.indexOf(y);
			return wektorInt.get(x2)-wektorInt.get(x1);
		}
	};
	
    WidokMapy widokMapa;
    WidokMapy2 widokLiczbowy;
   
	JButton przycisk_segreguj = new JButton("Posegreguj slowa");
	JButton przycisk_autor    = new JButton("Autor");


	public LiczbaSlow() {
		super("Porownanie dzialania kolekcji");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(400,500);

		JPanel panel = new JPanel();
		
		przycisk_segreguj.addActionListener(this);
		panel.add(przycisk_segreguj);
		
		przycisk_autor.addActionListener(this);
		panel.add(przycisk_autor);

		widokMapa = new WidokMapy(mapaSlow, 250, 200, "Alfabetycznie:");
		panel.add(widokMapa);
		
		widokLiczbowy = new WidokMapy2(wektor, wektorInt, 250, 200, "Wedlug ilosci wystapien:");
		panel.add(widokLiczbowy);
		
		setContentPane(panel);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		Object zrodlo = evt.getSource();
		if (zrodlo == przycisk_segreguj) {
			if(mapaSlow.isEmpty()){
				try{
					Scanner sc = new Scanner(fileName);
					while(sc.hasNext()){
						String slowo=sc.next().toLowerCase();
						if(mapaSlow.containsKey(slowo)){
							mapaSlow.put(slowo, mapaSlow.get(slowo)+1);
						}
						else{
							mapaSlow.put(slowo, 1);
						}
					}
					sc.close();
				}
				catch(Exception e){
					StringBuilder messageBuilder = new StringBuilder();
					messageBuilder.append(e);
					messageBuilder.append("\n");
					JOptionPane.showMessageDialog(null, messageBuilder, "", JOptionPane.ERROR_MESSAGE);
					messageBuilder = new StringBuilder();
				}
			}
			for(int i:mapaSlow.values()){
				wektorInt.add(i);
			}
			for(String i:mapaSlow.keySet()){
				wektor.add(i);
			}
			Collections.sort(wektor,komparator);
			for(int i=0;i<mapaSlow.size();i++){
				wektorInt.set(i,mapaSlow.get(wektor.elementAt(i)));
			}
		}

		if (zrodlo == przycisk_autor) {
			JOptionPane.showMessageDialog(this,"Autor: Adam Filipowicz\nData: 03.12.2016 r.");
		}

		widokMapa.refresh();
		widokLiczbowy.refresh();
	}	
	
	public static void main(String[] args) {
		new LiczbaSlow();
	}
}

class WidokMapy extends JScrollPane {
	private static final long serialVersionUID = 1L;

	private JTable tabela;
	private DefaultTableModel modelTabeli;
	private Map<String,Integer> mapa;

	WidokMapy(Map<String,Integer> mapa, int szerokosc, int wysokosc, String opis) {
		String[] kolumny = { "Slowo" , "Liczba wystapien"};
			modelTabeli = new DefaultTableModel(kolumny, 0);
			tabela = new JTable(modelTabeli);
			tabela.setRowSelectionAllowed(false);
			this.mapa = mapa;
			setViewportView(tabela);
			setPreferredSize(new Dimension(szerokosc, wysokosc));
			setBorder(BorderFactory.createTitledBorder(opis));
		}
	
	void refresh(){
    	modelTabeli.setRowCount(0);
		for (String slowo : mapa.keySet()) {
			int wartosc = mapa.get(slowo);
			String[] wiersz = { slowo, Integer.toString(wartosc) };
			modelTabeli.addRow(wiersz);
    	}
    }
}

class WidokMapy2 extends JScrollPane {
	private static final long serialVersionUID = 1L;

	private JTable tabela;
	private DefaultTableModel modelTabeli;
	private Vector<String> wektorS;
	private Vector<Integer> wektorInt;

	WidokMapy2(Vector<String> wektorS, Vector<Integer> wektorInt, int szerokosc, int wysokosc, String opis) {
		String[] kolumny = { "Slowo" , "Liczba wystapien"};
			modelTabeli = new DefaultTableModel(kolumny, 0);
			tabela = new JTable(modelTabeli);
			tabela.setRowSelectionAllowed(false);
			this.wektorS = wektorS;
			this.wektorInt = wektorInt;
			setViewportView(tabela);
			setPreferredSize(new Dimension(szerokosc, wysokosc));
			setBorder(BorderFactory.createTitledBorder(opis));
		}
	
	void refresh(){
    	modelTabeli.setRowCount(0);
    	int i=0;
		for (String slowo : wektorS) {
			int wartosc = wektorInt.get(i);
			String[] wiersz = { slowo, Integer.toString(wartosc) };
			modelTabeli.addRow(wiersz);
			i++;
    	}
    }
}