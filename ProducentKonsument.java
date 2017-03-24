/* 
 *  Przedstawienie problemu producenta i konsumenta z buforem o pojemnosci 0-5 i 
 *  iloscia producentow i konsumentow od 1 do 5. Program posiada interfejs graficzny.
 *
 *  Autor: Adam Filipowicz
 *   Data: 11.12.2016 r.
 */

import java.awt.event.*;
import java.awt.*;
import java.util.Vector;

import javax.swing.*;


class Circle{
    float r, x, y;
    Color kolor;
    Circle(float r,float x,float y,Color kolor){
    	this.r=r;
    	this.x=x;
    	this.y=y;
    	this.kolor=kolor;
    }
    
    void draw(Graphics g){
    	g.setColor(kolor);
        g.fillOval((int)(x-r), (int)(y-r), (int)(2*r), (int)(2*r));
    }

}

class Picture extends JPanel{
	
	private int liczbaprod=0;
	
	private static final long serialVersionUID = 1L;
	
	Vector<Circle> konsum1 = new Vector<Circle>();
	Vector<Circle> konsum2 = new Vector<Circle>();
	Vector<Circle> produc1 = new Vector<Circle>();
	Vector<Circle> produc2 = new Vector<Circle>();
	Vector<Circle> prawilni = new Vector<Circle>();
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (Circle c : prawilni){
			c.draw(g);
		}
	}
    
    void addKonsum(Circle c){
    	konsum1.add(c);
    	prawilni.add(c);
    	repaint();
    }
    
    void addProduc(Circle c){
    	liczbaprod++;
    	produc1.add(c);
    	prawilni.add(c);
    	repaint();
    }
    void addKonsum2(Circle c){
    	konsum2.add(c);
    	repaint();
    }
    
    void addProduc2(Circle c){
    	produc2.add(c);
    	repaint();
    }
    
    void ProduSelling(int number){
    	prawilni.set(number-1, produc2.elementAt(number-1));
    	repaint();
    }
    
    void ProduBack(int number){
    	prawilni.set(number-1, produc1.elementAt(number-1));
    	repaint();
    }
    
    void KonsumBuying(int number){
    	prawilni.set(number+liczbaprod-1, konsum2.elementAt(number-1));
    	repaint();
    }
    
    void KonsumBack(int number){
    	prawilni.set(number+liczbaprod-1, konsum1.elementAt(number-1));
    	repaint();
    }
}

class Producent extends Thread
{
	static char item = 'A';
	
	Bufor buf;
	int number;
	
	public Producent(Bufor c, int number){ 
		buf = c;
		this.number = number;
	}
	
	public synchronized void run(){ 
		char c;
		while(true){
			if((int)item>90){
				item='A';
				c=item;
			}
			else{
			    c = item++;
			}
			try {
				buf.put(number, c);
			} 
			catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			try {
				sleep((int)(Math.random() * 1000));
				} catch (InterruptedException e) { }
		}
	}
}

class Konsument extends Thread
{
	Bufor buf;
    int number;
    
	public Konsument(Bufor c, int number){ 
		buf = c;
		this.number = number;
	}
	
	public synchronized void run(){ 
		while(true){ 
			try {
				buf.get(number);
			} 
			catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			try {
				sleep((int)(Math.random() * 1000));
				} catch (InterruptedException e) { }
		}
	}
}

class Bufor
{
	private char contents;
	private int iloscMiejsc = 0;
	private int iloscZajetych = 0;
	protected ProducentKonsument prodkons;
	protected Picture picture;

	Bufor(ProducentKonsument pk,Picture pic,int ilosc){
		prodkons = pk;
		picture = pic;
		iloscMiejsc = ilosc;
	}
	
	public synchronized int get(int kons) throws InterruptedException{
		if(prodkons.getStop()){
			wait();
		}
		prodkons.addToTextArea("Konsument #" + kons + " chce zabrac");
		picture.KonsumBuying(kons);
		Thread.sleep(30);
		while (iloscZajetych==0){
			try {  
				if(prodkons.getStop()){
					wait();
				}
				prodkons.addToTextArea("Konsument #" + kons + "   bufor pusty - czekam");
				wait();
				} 
			catch (InterruptedException e) { }
		}
		prodkons.removeFromBuforArea();
		iloscZajetych--;
		if(prodkons.getStop()){
			wait();
		}
		prodkons.addToTextArea("Konsument #" + kons + "      zabral: " + contents);
		picture.KonsumBack(kons);
		notifyAll();
		return contents;
	}

	public synchronized void put(int prod, char value) throws InterruptedException{
		if(prodkons.getStop()){
			wait();
		}
		prodkons.addToTextArea("Producent #" + prod + "  chce oddac: " + value);
		picture.ProduSelling(prod);
		Thread.sleep(30);
		while (iloscZajetych==iloscMiejsc){
			try { 
				if(prodkons.getStop()){
					wait();
				}
				prodkons.addToTextArea("Producent #" + prod + "   bufor zajety - czekam");
				wait();
			} 
			catch (InterruptedException e) { }
		}
		prodkons.addToBuforArea(value);
		contents = value;
		iloscZajetych++;
		if(prodkons.getStop()){
			wait();
		}
		prodkons.addToTextArea("Producent #" + prod + "       oddal: " + value);
		picture.ProduBack(prod);
		notifyAll();
	}
	
	public synchronized void notifyy(){
		notifyAll();
	}
}


public class ProducentKonsument extends JFrame implements ActionListener, ItemListener{

    private final static String newline = "\n";
	
	private static final long serialVersionUID = 1L;
	
	private final String AUTHOR = "Autor: Adam Filipowicz\nData: 11.12.2016 r.";

	private final String DESCRIPTION = "Program symuluje wspó³dzia³anie producentow i konsumentow";

	protected Picture picture;
	
	private JMenu[] menu = {new JMenu("Plik"),
							new JMenu("Pomoc")
							};

	private JMenuItem[] items = { new JMenuItem("Zakoncz"),
								  new JMenuItem("Informacje"),
								  new JMenuItem("O programie"),
			                      };
	
	private Vector<Integer> opcjepk = new Vector<Integer>();
	private Vector<Integer> opcjeb = new Vector<Integer>();
	private Vector<Character> buforr = new Vector<Character>();
	
	private JTextField bufor = new JTextField("Rozmiar bufora:");
	private JTextField producenci = new JTextField("Ilosc producentow:");
	private JTextField konsumenci = new JTextField("Ilosc konsumentow:");
	private JComboBox<Integer> buf = new JComboBox<>(opcjeb);
	private JComboBox<Integer> prod = new JComboBox<>(opcjepk);
	private JComboBox<Integer> kons = new JComboBox<>(opcjepk);
	private JTextArea textArea = new JTextArea(0,0);
	private JTextArea buforArea = new JTextArea(3,15);
	private JButton start = new JButton("Start");
	private JButton stop = new JButton("Wstrzymaj symulacje");
	
	private int bufo=0;
	private int konsu=1;
	private int produ=1;
	private boolean stopp=false;
	private Bufor c;
	private Producent[] pr;
	private Konsument[] ko;
	
	public ProducentKonsument(){
		super ("ProducentKonsumentSymulacja");
		for(int i=1;i<6;i++){
			opcjepk.add(i);
		}
		for(int i=0;i<6;i++){
			opcjeb.add(i);
		}
		setSize(650,700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		for (int i = 0; i < items.length; i++)
		  	items[i].addActionListener(this);
		menu[0].add(items[0]);
		menu[1].add(items[1]);
		menu[1].add(items[2]);
		
		JScrollPane scrollPane = new JScrollPane(textArea);
		
	    JMenuBar menubar = new JMenuBar();
	    for (int i = 0; i < menu.length; i++)
	  	  menubar.add(menu[i]);
	    setJMenuBar(menubar);
		
		buf.addItemListener(this);
		prod.addItemListener(this);
		kons.addItemListener(this);
		start.addActionListener(this);
		stop.addActionListener(this);
		buf.setSelectedIndex(0);
		prod.setSelectedIndex(0);
		kons.setSelectedIndex(0);
		
		picture=new Picture();
		picture.setFocusable(true);
		picture.setLayout(new FlowLayout());

		buforArea.setEditable(false);
		
		picture.add(bufor);
		picture.add(buf);
		picture.add(producenci);
		picture.add(prod);
		picture.add(konsumenci);
		picture.add(kons);
		picture.add(textArea);   
		picture.add(scrollPane);
		picture.add(start);
		picture.add(stop);
		picture.add(buforArea);
		
		bufor.setEditable(false);
		producenci.setEditable(false);
		konsumenci.setEditable(false);
		textArea.setEditable(false);
		scrollPane.setPreferredSize(new Dimension(300,250));
		scrollPane.setViewportView(textArea);
		
		setContentPane(picture);
		setVisible(true);
		
	}
	
	public void addToTextArea(String napis){
	    textArea.append(napis + newline);
        textArea.setCaretPosition(textArea.getDocument().getLength());
	}
	
	public void addToBuforArea(char litera){
		buforr.add(litera);
		String s="";
		for(int i=0;i<buforr.size();i++){
			s+=Character.toString(buforr.elementAt(i));
		}
		buforArea.setText(s);
	}
	
	public boolean getStop(){
		return stopp;
	}
	
	public void removeFromBuforArea(){
		buforr.remove(0);
		String s="";
		for(int i=0;i<buforr.size();i++){
			s+=Character.toString(buforr.elementAt(i));
		}
		buforArea.setText(s);
	}
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		Object zrodlo = evt.getSource();
		if (zrodlo == items[0])
			System.exit(0);
		if (zrodlo == items[1])
			JOptionPane.showMessageDialog(null, DESCRIPTION);
		if (zrodlo == items[2])
			JOptionPane.showMessageDialog(null, AUTHOR);
		if (zrodlo == start){
			if(stopp){
				stopp=false;
				c.notifyy();
			}
			else{
				c = new Bufor(this,picture,bufo);
				pr = new Producent[5];
				ko = new Konsument[5];
				for(int i=0;i<produ;i++){
					picture.addProduc(new Circle(25,100,350+50*i,Color.green));
					picture.addProduc2(new Circle(25,200,350+50*i,Color.red));
				}
				for(int i=0;i<konsu;i++){
					picture.addKonsum(new Circle(25,550,350+50*i,Color.blue));
					picture.addKonsum2(new Circle(25,450,350+50*i,Color.red));
				}
				for(int i=0;i<produ;i++){
					pr[i] = new Producent(c, i+1);
					pr[i].start();
				}
				for(int i=0;i<konsu;i++){
					ko[i] = new Konsument(c, i+1);
					ko[i].start();
				}
				try {Thread.sleep(50);} 
				catch (InterruptedException e) { }
				buf.setEnabled(false);
				prod.setEnabled(false);
				kons.setEnabled(false);
			}
		}
		if (zrodlo == stop){
			stopp=true;
		}
	}
	
	@Override	public void itemStateChanged(ItemEvent evt) {
		Object zmiana = evt.getStateChange();
		if((int)zmiana==1){
			bufo=(int)(buf.getSelectedIndex());
			produ=(int)(prod.getSelectedIndex())+1;
			konsu=(int)(kons.getSelectedIndex())+1;
		}

	}
	
	public static void main(String[] args){
		new ProducentKonsument();
	}
}

