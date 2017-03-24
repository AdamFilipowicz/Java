/*
 *  Program KrzyweLissajous - aplikacja rysujaca krzywe Lissajous
 *  dla zadanych parametrow a,b,fi.
 *
 *  Autor: Adam Filipowicz
 *  Data: 05.11.2016
 */

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JSlider;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
class Lissajous{

	protected int a,b;
	protected float f;

	Lissajous(){ 
		this.a=2;
		this.b=4;
		this.f=40/180;
	}

	Lissajous(int a, int b, float f){ 
		this.a=a;
		this.b=b;
		this.f=f;
	}

	public float getA() {
		return a;
	}
	
	public void setA(int aa) {
		this.a=aa;
	}

	float getB() {
		return b;
	}
	
	public void setB(int bb) {
		this.b=bb;
	}

	float getF() {
		return f;
	}

	public void setF(float ff) {
		this.f=ff;
	}
	
    void draw(Graphics g, Color c){
    	g.setColor(c);
		int []px=new int[399];
		int []py=new int[399];
		float t=(float)Math.PI/200;
		int tt=0;
		while(t<2*Math.PI){
			px[tt]=(int)(Math.sin(a*t+f)*200+230);
			py[tt]=(int)(Math.sin(b*t)*200+250);
			tt++;
			t+=Math.PI/200;
		}
		g.drawPolygon(px, py, 399);
    }
}

class Picture extends JPanel {

	private static final long serialVersionUID = 1L;

	private Lissajous lissajous = new Lissajous();
	/*
	 * UWAGA: ta metoda bedzie wywolywana automatycznie przy kazdej potrzebie
	 * odrysowania na ekranie zawartosci panelu
	 *
	 * W tej metodzie NIE WOLNO !!! wywolywac metody repaint()
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Color c = Color.getColor("tekst",Color.RED);
		lissajous.draw(g, c);
	}
   
	public void setA(int a){
		lissajous.setA(a);
		repaint();
	}
	
	public void setB(int b){
		lissajous.setB(b);
		repaint();
	}
	
	public void setF(float f){
		lissajous.setF(f);
		repaint();
	}
	
}



public class KrzyweLissajous extends JFrame implements ActionListener, ChangeListener, ItemListener{
	
	private static final long serialVersionUID = 3727471814914970170L;
	
	private final String AUTHOR = "Autor: Adam Filipowicz";

	private final String DESCRIPTION = "KRZYWA LISSAJOUS\n\n"
			+ "   Krzywa Lissajous to krzywa parametryczna\n"
			+ "   opisujaca drgania harmoniczne, dana wzorem:\n\n"
			+ "   x(t) = sin(a*t+fi),        y(t) = sin(b*t),\n\n"
			+ "   gdzie a,b,fi sa parametrami od ktorych zalezy\n"
			+ "   ksztalt krzywej.\n\n"
			+ "   Ksztalt krzywych zalezy od wspolczynnika a/b\n"
			+ "   Dla wspolczynnika 1 krzywa jest elipsa\n"
			+ "   ze specjalnymi przypadkami:\n"
			+ "   - okrag, gdy fi=90\n"
			+ "   - odcinek, gdy fi=0\n"
			+ "   Inne wartosci wspolczynnika daja bardziej zlozone\n"
			+ "   krzywe, ktore sa zamkniete, tylko gdy a/b\n"
			+ "   jest liczba wymierna.";

	protected Picture picture;

	private JMenu[] menu = { new JMenu("Menu") };

	private JMenuItem[] items = { new JMenuItem("Opis programu"),
								  new JMenuItem("Autor"),
			                      };
	
	private Vector<Integer> opcje = new Vector<Integer>();
	
	private JTextField parametrA = new JTextField("a:");
	private JTextField parametrB = new JTextField("b:");
	private JTextField parametrF = new JTextField("fi:");
	private JSlider slider = new JSlider(0,180);
	private JComboBox<Integer> buttonA = new JComboBox<>(opcje);
	private JComboBox<Integer> buttonB = new JComboBox<>(opcje);
	
    public KrzyweLissajous(){ 
    	super ("Edytor graficzny");
		for(int i=1;i<6;i++){
			opcje.add(i);
		}
		setSize(500,550);
	  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	  for (int i = 0; i < items.length; i++)
	  	items[i].addActionListener(this);
	
	  // dodanie opcji do menu
	  menu[0].add(items[0]);
	  menu[0].add(items[1]);
	
	  // dodanie do okna paska menu
	  JMenuBar menubar = new JMenuBar();
	  for (int i = 0; i < menu.length; i++)
	  	menubar.add(menu[i]);
	  setJMenuBar(menubar);
	
	  picture=new Picture();
	  picture.setFocusable(true);
	  picture.setLayout(new FlowLayout());
	  
	  buttonA.addItemListener(this);
	  buttonB.addItemListener(this);
	  slider.addChangeListener(this);
	  
	  picture.add(parametrA);
	  picture.add(buttonA);
	  picture.add(parametrB);
	  picture.add(buttonB);
	  picture.add(parametrF);
	  picture.add(slider);
	  
	  buttonA.setSelectedIndex(1);
	  buttonB.setSelectedIndex(3);
	  
	  parametrA.setEditable(false);
	  parametrB.setEditable(false);
	  parametrF.setEditable(false);
	  setContentPane(picture);
	  setVisible(true);
    }

	public void actionPerformed(ActionEvent evt) {
		Object zrodlo = evt.getSource();
		
		if (zrodlo == items[0])
			JOptionPane.showMessageDialog(null, DESCRIPTION);
		if (zrodlo == items[1])
			JOptionPane.showMessageDialog(null, AUTHOR);
		
		picture.requestFocus();
		repaint();
	}

	@Override
	public void itemStateChanged(ItemEvent evt) {
		Object zmiana = evt.getStateChange();
		if((int)zmiana==1){
			int aa=(int)(buttonA.getSelectedIndex())+1;
			picture.setA(aa);
			int bb=(int)(buttonB.getSelectedIndex())+1;
			picture.setB(bb);
		}

	}
	
	@Override
	public void stateChanged(ChangeEvent evt) {
		picture.setF((float)slider.getValue()/(float)57.3);
		
	}
	
    public static void main(String[] args){ 
    	new KrzyweLissajous();
    }

}