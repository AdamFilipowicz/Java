/* 
 *  Program ukazuje animacje problemu producenta-konsumenta.
 *
 *  Autor: Adam Filipowicz
 *   Data: 11.12.2016 r.
 */

import java.awt.*;
import java.util.*;
import java.util.Vector;

import javax.swing.*;


abstract class Circle{
	static Random random = new Random();
    protected float kierunek=(float)(random.nextInt(360)*Math.PI)/180;
    protected float predkosc=(float)(random.nextFloat()+0.1)/3;
    protected float x=random.nextInt(300)+100;
    protected float y=80;
    protected float r=random.nextInt(30)+10;
    
    public float computeArea(){ return (float)Math.PI*r*r; }
    
    public void setx(float x){
    	this.x=x;
    }
    
    public void sety(float y){
    	this.y=y;
    }
    
    public void setr(float r){
    	this.r=r;
    }
    
    public void setv(float v){
    	predkosc=v;
    }
    
    public void setkier(float kier){
    	kierunek=kier;
    }
    
    abstract void draw(Graphics g);
    abstract void fill(Graphics g);
    boolean isTouchingBorder(){
		if(x-r<20 || x+r>480){
			return true;
		}
		if(y-r<20 || y+r>480){
			return true;
		}
		return false;
    }
    boolean isOutsideBuffer(){
    	return r+100<Math.sqrt(Math.pow(Math.abs(x-250),2)+Math.pow(Math.abs(y-250),2));
    }
    boolean isInsideBuffer(){
    	return 100-r>=Math.sqrt(Math.pow(Math.abs(x-250),2)+Math.pow(Math.abs(y-250),2));
    }
}

class GrayBlack extends Circle{
	private Color kolor;
	
	GrayBlack(Color kolor){
		setx(250);
		sety(250);
		if(kolor==Color.gray){
			setr(100);
		}
		else{
			setr(10);
		}
		this.kolor=kolor;
	}
	
	@Override
    void draw(Graphics g){
		g.setColor(kolor);
	    g.fillOval((int)(x-r), (int)(y-r), (int)(2*r), (int)(2*r));
	}
	@Override
    void fill(Graphics g){}
}

class Producent extends Circle implements Runnable{
	
	private Bufor buffer;
	private Color kolor=Color.white;
	private float ladunek=0;
	private Picture picture;
	
	Producent(Bufor buffer,Picture picture){
		this.buffer=buffer;
		this.picture=picture;
	}
	
	@Override
    void draw(Graphics g){
		g.setColor(Color.red);
	    g.drawOval((int)(x-r), (int)(y-r), (int)(2*r), (int)(2*r));
	}
	
	@Override
    void fill(Graphics g){
		g.setColor(kolor);
	    g.fillOval((int)(x-r), (int)(y-r), (int)(2*r), (int)(2*r));
	}
	public synchronized void run(){
		while(true){
			while(isOutsideBuffer()){
				if(isTouchingBorder()){
					ladunek=computeArea();
					kolor=Color.red;
				}
				picture.moveCircle(this);
			}
			
			synchronized(buffer){
				do{
					if(isInsideBuffer()){
						while(buffer.full(computeArea())){
							try {
								buffer.wait();
							} 
							catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						picture.addToBlack(ladunek);
						ladunek=0;
						kolor=Color.white;
					}
					picture.moveCircle(this);
				} while(!isOutsideBuffer());
				buffer.notifyAll();
			}
			
		}
	}

}

class Konsument extends Circle implements Runnable
{
	private Bufor buffer;
    private Color kolor=Color.white;
    private Picture picture;
    private float ladunek=0;
    private boolean taken=false;
    
	public Konsument(Bufor buffer,Picture picture){ 
		this.buffer=buffer;
		this.picture=picture;
	}
	
	@Override
    void draw(Graphics g){
		g.setColor(Color.green);
	    g.drawOval((int)(x-r), (int)(y-r), (int)(2*r), (int)(2*r));
	}
	@Override
    void fill(Graphics g){
		g.setColor(kolor);
	    g.fillOval((int)(x-r), (int)(y-r), (int)(2*r), (int)(2*r));
	}
	public synchronized void run(){
		while(true){
			while(isOutsideBuffer()){
				if(isTouchingBorder()){
					ladunek=0;
					kolor=Color.white;
				}
				picture.moveCircle(this);
			}
			synchronized(buffer){
				do{
					if(isInsideBuffer()){
						while(!buffer.notEnough(computeArea()) && ladunek==0){
							try {
								buffer.wait();
							} 
							catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						if(!taken){
							ladunek=computeArea();
							picture.subtractFromBlack(ladunek);
							taken=true;
							kolor=Color.green;
						}
					}
					picture.moveCircle(this);
				} while(!isOutsideBuffer());
				taken=false;
				buffer.notifyAll();
			}
			
		}
	}
}

class Bufor{
	protected AnimacjaProducentKonsument prodkons;
	protected Picture picture;

	Bufor(AnimacjaProducentKonsument pk,Picture pic){
		prodkons = pk;
		picture = pic;
	}
	public synchronized boolean full(float amount){
		return picture.checkBlack2(amount);
	}

	public synchronized boolean notEnough(float amount){
		return picture.checkBlack(amount);
	}
}

class Picture extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	Vector<Circle> konsum1 = new Vector<Circle>();
	Vector<Circle> produc1 = new Vector<Circle>();
	
	Circle gray=new GrayBlack(Color.gray);
	Circle black=new GrayBlack(Color.black);
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		gray.draw(g);
		black.draw(g);
		for (Circle c : konsum1){
			c.fill(g);
			c.draw(g);
		}
		for (Circle c : produc1){
			c.fill(g);
			c.draw(g);
		}
	}
    
    void addKonsum(Circle c){
    	konsum1.add(c);
    	repaint();
    }
    
    void addProduc(Circle c){
    	produc1.add(c);
    	repaint();
    }
    
    //true, gdy pole czarne jest wieksze od zielonego
    boolean checkBlack(float amount){ 
    	return black.computeArea()>amount;
    }
    
    //true, gdy po dodaniu bufer bedzie wypelniony
    boolean checkBlack2(float amount){
    	return black.computeArea()+amount>10000*Math.PI;
    }
    
    void subtractFromBlack(float amount){
    	black.setr((float)Math.sqrt((black.computeArea()-amount)/Math.PI));
    	repaint();
    }
    
    void addToBlack(float amount){
    	black.setr((float)Math.sqrt((black.computeArea()+amount)/Math.PI));
    	repaint();
    }
    
    void moveCircle(Circle c){
    	try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	c.setx(c.x+(float)Math.sin(c.kierunek)*c.predkosc);
		c.sety(c.y+(float)Math.cos(c.kierunek)*c.predkosc);
		if(c.x-c.r<5 || c.x+c.r>495){
			c.setkier((float)Math.PI*2-c.kierunek);
		}
		if(c.y-c.r<5 || c.y+c.r>460){
			c.setkier((float)Math.PI-c.kierunek);
		}
		repaint();
    }
    
}


public class AnimacjaProducentKonsument extends JFrame{
	
	private static final long serialVersionUID = 1L;

	protected Picture picture;
	
	private Bufor c;
	
	public AnimacjaProducentKonsument(){
		super ("ProducentKonsumentAnimacja");
		
		setSize(500,500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		picture=new Picture();
		picture.setFocusable(true);
		picture.setLayout(new FlowLayout());
		
		c=new Bufor(this,picture);
		for(int i=0;i<5;i++){
			Producent pro=new Producent(c,picture);
			Konsument kon=new Konsument(c,picture);
			picture.addProduc(pro);
			picture.addKonsum(kon);
			Thread t1=new Thread(pro);
			Thread t2=new Thread(kon);
			t1.start();
			t2.start();
			
		}
		setContentPane(picture);
		setVisible(true);
		
	}
	
	public static void main(String[] args){
		new AnimacjaProducentKonsument();
	}
}

