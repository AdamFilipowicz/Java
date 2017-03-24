/*
 *  Program GraphicEditor - aplikacja z graficznym interfejsem
 *   - obsluga zdarzen od klawiatury, myszki i innych elementow GUI.
 *
 *  Autor: Adam Filipowicz
 *  Data: 05.11.2016
 */

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


abstract class Figure{
	
	static Random random = new Random();
	private Color kolor=Color.getColor("tekst",Color.BLUE);

	private boolean selected = false;
	
	public boolean isSelected() { return selected; }
	public void select() {	selected = true; }
	public void select(boolean z) { selected = z; }
	public void unselect() { selected = false; }

	protected void setColor(Graphics g) {
		if (selected) g.setColor(Color.RED);
		else g.setColor(getKolor());
	}
	protected Color getKolor(){
		return kolor;
	}
	protected void settColor(Color c){
		kolor=c;
	}
	public abstract boolean isInside(float px, float py);
	public boolean isInside(int px, int py) {
		return isInside((float) px, (float) py);
	}

	protected String properties() {
		String s = String.format("  Pole: %.0f  Obwod: %.0f", computeArea(), computePerimeter());
		if (isSelected()) s = s + "   [SELECTED]";
		return s;
	}

	abstract String getName();
	abstract float  getX();
	abstract float  getY();

    abstract float computeArea();
    abstract float computePerimeter();

    abstract void move(float dx, float dy);
    abstract void scale(float s);

    abstract void draw(Graphics g,Color c);

    @Override
    public String toString(){
        return getName();
    }

}


class Point extends Figure{

	protected float x, y;
	Color kolor;

	Point()
	{ this.x=random.nextFloat()*400;
	  this.y=random.nextFloat()*400;
	}

	Point(float x, float y)
	{ this.x=x;
	  this.y=y;
	}

	@Override
	public boolean isInside(float px, float py) {
		// by umozliwic zaznaczanie punktu myszka
		// miejsca odlegle nie wiecej niz 6 leza wewnatrz
		return (Math.sqrt((x - px) * (x - px) + (y - py) * (y - py)) <= 6);
	}


    @Override
	String getName() {
		return "Point(" + x + ", " + y + ")";
	}

	@Override
	float getX() {
		return x;
	}

	@Override
	float getY() {
		return y;
	}

	@Override
    float computeArea(){ return 0; }

	@Override
	float computePerimeter(){ return 0; }

	@Override
    void move(float dx, float dy){ x+=dx; y+=dy; }

	@Override
    void scale(float s){ }

	@Override
    void draw(Graphics g,Color c){
		settColor(c);
		setColor(g);
		g.fillOval((int)(x-3),(int)(y-3), 6,6);
	}

    String toStringXY(){ return "(" + x + " , " + y + ")"; }
}


class Circle extends Point{
    float r;
	Color kolor;

    Circle(){
        super();
        r=random.nextFloat()*100;
    }

    Circle(float px, float py, float pr){
        super(px,py);
        r=pr;
    }

    @Override
	public boolean isInside(float px, float py) {
		return (Math.sqrt((x - px) * (x - px) + (y - py) * (y - py)) <= r);
	}

    @Override
   	String getName() {
   		return "Circle(" + x + ", " + y + ")";
   	}

    @Override
    float computeArea(){ return (float)Math.PI*r*r; }

    @Override
    float computePerimeter(){ return (float)Math.PI*r*2; }

    @Override
    void scale(float s){ r*=s; }

    @Override
    void draw(Graphics g,Color c){
    	settColor(c);
		setColor(g);
        g.drawOval((int)(x-r), (int)(y-r), (int)(2*r), (int)(2*r));
        g.fillOval((int)(x-r), (int)(y-r), (int)(2*r), (int)(2*r));
    }

}


class Triangle extends Figure{
    Point point1, point2, point3;
	Color kolor;

    Triangle(){
    	point1 = new Point();
    	point2 = new Point();
    	point3 = new Point();
    }

    Triangle(Point p1, Point p2, Point p3){
        point1=p1; point2=p2; point3=p3;
    }

    @Override
    public boolean isInside(float px, float py)
    { float d1, d2, d3;
      d1 = px*(point1.y-point2.y) + py*(point2.x-point1.x) +
           (point1.x*point2.y-point1.y*point2.x);
      d2 = px*(point2.y-point3.y) + py*(point3.x-point2.x) +
           (point2.x*point3.y-point2.y*point3.x);
      d3 = px*(point3.y-point1.y) + py*(point1.x-point3.x) +
           (point3.x*point1.y-point3.y*point1.x);
      return ((d1<=0)&&(d2<=0)&&(d3<=0)) || ((d1>=0)&&(d2>=0)&&(d3>=0));
    }

    @Override
	String getName() {
    	return "Triangle{"+point1.toStringXY()+
                point2.toStringXY()+
                point3.toStringXY()+"}";
	}

	@Override
	float getX() {
		return (point1.x+point2.x+point3.x)/3;
	}

	@Override
	float getY() {
		return (point1.y+point2.y+point3.y)/3;
	}

	@Override
	float computeArea(){
        float a = (float)Math.sqrt( (point1.x-point2.x)*(point1.x-point2.x)+
                                    (point1.y-point2.y)*(point1.y-point2.y));
        float b = (float)Math.sqrt( (point2.x-point3.x)*(point2.x-point3.x)+
                                    (point2.y-point3.y)*(point2.y-point3.y));
        float c = (float)Math.sqrt( (point1.x-point3.x)*(point1.x-point3.x)+
                                    (point1.y-point3.y)*(point1.y-point3.y));
        float p=(a+b+c)/2;
        return (float)Math.sqrt(p*(p-a)*(p-b)*(p-c));
    }

	@Override
    float computePerimeter(){
        float a = (float)Math.sqrt( (point1.x-point2.x)*(point1.x-point2.x)+
                                    (point1.y-point2.y)*(point1.y-point2.y));
        float b = (float)Math.sqrt( (point2.x-point3.x)*(point2.x-point3.x)+
                                    (point2.y-point3.y)*(point2.y-point3.y));
        float c = (float)Math.sqrt( (point1.x-point3.x)*(point1.x-point3.x)+
                                    (point1.y-point3.y)*(point1.y-point3.y));
        return a+b+c;
    }

	@Override
    void move(float dx, float dy){
        point1.move(dx,dy);
        point2.move(dx,dy);
        point3.move(dx,dy);
    }

	@Override
    void scale(float s){
        Point sr1 = new Point((point1.x+point2.x+point3.x)/3,
                              (point1.y+point2.y+point3.y)/3);
        point1.x*=s; point1.y*=s;
        point2.x*=s; point2.y*=s;
        point3.x*=s; point3.y*=s;
        Point sr2 = new Point((point1.x+point2.x+point3.x)/3,
                              (point1.y+point2.y+point3.y)/3);
        float dx=sr1.x-sr2.x;
        float dy=sr1.y-sr2.y;
        point1.move(dx,dy);
        point2.move(dx,dy);
        point3.move(dx,dy);
    }

	@Override
    void draw(Graphics g,Color c){
		settColor(c);
		setColor(g);
        g.drawLine((int)point1.x, (int)point1.y,
                   (int)point2.x, (int)point2.y);
        g.drawLine((int)point2.x, (int)point2.y,
                   (int)point3.x, (int)point3.y);
        g.drawLine((int)point3.x, (int)point3.y,
                   (int)point1.x, (int)point1.y);
        int pointsx[] = {(int)point1.x,(int)point2.x,(int)point3.x};
        int pointsy[] = {(int)point1.y,(int)point2.y,(int)point3.y};
        g.fillPolygon(pointsx, pointsy, 3);
    }

}

class Square extends Point{
    Point point1, point2, point3, point4;
    float r; //najblizsza odleglosc do odcinka
	Color kolor;

    Square(){
    	super();
    	r=random.nextFloat()*100;
    	point1 = new Point(getX()-r,getY()-r);
    	point2 = new Point(getX()-r,getY()+r);
    	point3 = new Point(getX()+r,getY()+r);
    	point4 = new Point(getX()+r,getY()-r);
    }

    Square(float px, float py, float pr){
    	super(px,py);
        point1=new Point(px-pr,py-pr); 
        point2=new Point(px-pr,py+pr);
        point3=new Point(px+pr,py+pr);
        point4=new Point(px+pr,py-pr);
    }

    @Override
    public boolean isInside(float px, float py)
    { 
    	return (px>=point1.getX() && px<=point4.getX() && py>=point1.getY() && py<=point2.getY());
    }

    @Override
	String getName() {
    	return "Square{"+point1.toStringXY()+
                point2.toStringXY()+
                point3.toStringXY()+
                point4.toStringXY()+"}";
	}
    
	@Override
	float computeArea(){
        float a = 2*r;
        return (float)a*a;
    }

	@Override
    float computePerimeter(){
        float a = 2*r;
        return 4*a;
    }

	@Override
    void move(float dx, float dy){
        point1.move(dx,dy);
        point2.move(dx,dy);
        point3.move(dx,dy);
        point4.move(dx,dy);
    }

	@Override
    void scale(float s){
		Point sr1 = new Point((point1.getX()+point4.getX())/2,(point1.getY()+point2.getY())/2);
		point1.x*=s; point1.y*=s;
		point2.x*=s; point2.y*=s;
		point3.x*=s; point3.y*=s;
		point4.x*=s; point4.y*=s;
		Point sr2 = new Point((point1.getX()+point4.getX())/2,(point1.getY()+point2.getY())/2);
		float dx=sr1.x-sr2.x;
		float dy=sr1.y-sr2.y;
		point1.move(dx,dy);
		point2.move(dx,dy);
		point3.move(dx,dy);
		point4.move(dx,dy);
    }

	@Override
    void draw(Graphics g,Color c){
		settColor(c);
		setColor(g);
        g.drawLine((int)point1.x, (int)point1.y,
                   (int)point2.x, (int)point2.y);
        g.drawLine((int)point2.x, (int)point2.y,
                   (int)point3.x, (int)point3.y);
        g.drawLine((int)point3.x, (int)point3.y,
                   (int)point4.x, (int)point4.y);
        g.drawLine((int)point4.x, (int)point4.y,
                (int)point1.x, (int)point1.y);
        int pointsx[] = {(int)point1.x,(int)point2.x,(int)point3.x,(int)point4.x};
        int pointsy[] = {(int)point1.y,(int)point2.y,(int)point3.y,(int)point4.y};
        g.fillPolygon(pointsx, pointsy, 4);
    }

}

class Rectangle extends Point{
    Point point1, point2, point3, point4;
    float r1; //najblizsza odleglosc do odcinka
    float r2; //dlugosc od srodka figury do pionowych linii
	Color kolor;

    Rectangle(){
    	super();
    	r1=random.nextFloat()*100;
    	r2=random.nextFloat()*100;
    	point1 = new Point(getX()-r2,getY()-r1);
    	point2 = new Point(getX()-r2,getY()+r1);
    	point3 = new Point(getX()+r2,getY()+r1);
    	point4 = new Point(getX()+r2,getY()-r1);
    }

    Rectangle(float px, float py, float pr1, float pr2){
    	super(px,py);
        point1=new Point(px-pr2,py-pr1); 
        point2=new Point(px-pr2,py+pr1);
        point3=new Point(px+pr2,py+pr1);
        point4=new Point(px+pr2,py-pr1);
    }

    @Override
    public boolean isInside(float px, float py)
    { 
    	return (px>=point1.getX() && px<=point4.getX() && py>=point1.getY() && py<=point2.getY());
    }

    @Override
	String getName() {
    	return "Rectangle{"+point1.toStringXY()+
                point2.toStringXY()+
                point3.toStringXY()+
                point4.toStringXY()+"}";
	}
    
	@Override
	float computeArea(){
        float a = 2*r1;
        float b = 2*r2;
        return (float)a*b;
    }

	@Override
    float computePerimeter(){
        float a = 2*r1;
        float b = 2*r2;
        return 2*a+2*b;
    }

	@Override
    void move(float dx, float dy){
        point1.move(dx,dy);
        point2.move(dx,dy);
        point3.move(dx,dy);
        point4.move(dx,dy);
    }

	@Override
    void scale(float s){
		Point sr1 = new Point((point1.getX()+point4.getX())/2,(point1.getY()+point2.getY())/2);
		point1.x*=s; point1.y*=s;
		point2.x*=s; point2.y*=s;
		point3.x*=s; point3.y*=s;
		point4.x*=s; point4.y*=s;
		Point sr2 = new Point((point1.getX()+point4.getX())/2,(point1.getY()+point2.getY())/2);
		float dx=sr1.x-sr2.x;
		float dy=sr1.y-sr2.y;
		point1.move(dx,dy);
		point2.move(dx,dy);
		point3.move(dx,dy);
		point4.move(dx,dy);
    }

	@Override
    void draw(Graphics g,Color c){
		settColor(c);
		setColor(g);
        g.drawLine((int)point1.x, (int)point1.y,
                   (int)point2.x, (int)point2.y);
        g.drawLine((int)point2.x, (int)point2.y,
                   (int)point3.x, (int)point3.y);
        g.drawLine((int)point3.x, (int)point3.y,
                   (int)point4.x, (int)point4.y);
        g.drawLine((int)point4.x, (int)point4.y,
                (int)point1.x, (int)point1.y);
        int pointsx[] = {(int)point1.x,(int)point2.x,(int)point3.x,(int)point4.x};
        int pointsy[] = {(int)point1.y,(int)point2.y,(int)point3.y,(int)point4.y};
        g.fillPolygon(pointsx, pointsy, 4);
    }

}

class Star extends Point{
	Point point1, point2, point3, point4, point5, sr;
	Color kolor;
    float r; //odleglosc punktow do odcinka

    Star(){
    	super();
    	r=random.nextFloat()*100;
    	point1 = new Point(getX(),getY()-r);
    	point2 = new Point(getX()-(float)Math.sqrt((Math.pow(r,2))/((Math.pow(Math.tan(3*Math.PI/10),2)+1))),getY()+(float)Math.tan(3*Math.PI/10)*(float)Math.sqrt((Math.pow(r,2))/((Math.pow(Math.tan(3*Math.PI/10),2)+1))));                       
    	point3 = new Point(getX()+(float)Math.sqrt((Math.pow(r,2))/((Math.pow(Math.tan(9*Math.PI/10),2)+1))),getY()+(float)Math.tan(9*Math.PI/10)*(float)Math.sqrt((Math.pow(r,2))/((Math.pow(Math.tan(9*Math.PI/10),2)+1))));
    	point4 = new Point(getX()-(float)Math.sqrt((Math.pow(r,2))/((Math.pow(Math.tan(9*Math.PI/10),2)+1))),getY()+(float)Math.tan(9*Math.PI/10)*(float)Math.sqrt((Math.pow(r,2))/((Math.pow(Math.tan(9*Math.PI/10),2)+1))));
    	point5 = new Point(getX()+(float)Math.sqrt((Math.pow(r,2))/((Math.pow(Math.tan(3*Math.PI/10),2)+1))),getY()+(float)Math.tan(3*Math.PI/10)*(float)Math.sqrt((Math.pow(r,2))/((Math.pow(Math.tan(3*Math.PI/10),2)+1))));
    }

    Star(float px, float py, float pr){
    	super(px,py);
    	point1 = new Point(px,py-pr);
    	point2 = new Point(px-(float)Math.sqrt((Math.pow(pr,2))/((Math.pow(Math.tan(3*Math.PI/10),2)+1))),py+(float)Math.tan(3*Math.PI/10)*(float)Math.sqrt((Math.pow(pr,2))/((Math.pow(Math.tan(3*Math.PI/10),2)+1))));                       
    	point3 = new Point(px+(float)Math.sqrt((Math.pow(pr,2))/((Math.pow(Math.tan(9*Math.PI/10),2)+1))),py+(float)Math.tan(9*Math.PI/10)*(float)Math.sqrt((Math.pow(pr,2))/((Math.pow(Math.tan(9*Math.PI/10),2)+1))));
    	point4 = new Point(px-(float)Math.sqrt((Math.pow(pr,2))/((Math.pow(Math.tan(9*Math.PI/10),2)+1))),py+(float)Math.tan(9*Math.PI/10)*(float)Math.sqrt((Math.pow(pr,2))/((Math.pow(Math.tan(9*Math.PI/10),2)+1))));
    	point5 = new Point(px+(float)Math.sqrt((Math.pow(pr,2))/((Math.pow(Math.tan(3*Math.PI/10),2)+1))),py+(float)Math.tan(3*Math.PI/10)*(float)Math.sqrt((Math.pow(pr,2))/((Math.pow(Math.tan(3*Math.PI/10),2)+1))));
    }

    @Override
    public boolean isInside(float px, float py)
    { 
		return (Math.sqrt((point1.getX() - px) * (point1.getX() - px) + (point1.getY()+r - py) * (point1.getY()+r - py)) <= r);
    }

    @Override
	String getName() {
    	return "Star{"+point1.toStringXY()+
                point2.toStringXY()+
                point3.toStringXY()+
                point4.toStringXY()+
                point5.toStringXY()+"}";
	}
    
	@Override
	float computeArea(){
		Point point6 = new Point(  point1.getX() , (getY()+(float)Math.tan(9*Math.PI/10)*(float)Math.sqrt((Math.pow(r,2))/((Math.pow(Math.tan(9*Math.PI/10),2)+1))) - getY()+(float)Math.tan(3*Math.PI/10)*(float)Math.sqrt((Math.pow(r,2))/((Math.pow(Math.tan(3*Math.PI/10),2)+1))))  /  ( getX()-(float)Math.sqrt((Math.pow(r,2))/((Math.pow(Math.tan(9*Math.PI/10),2)+1))) -  getX()+(float)Math.sqrt((Math.pow(r,2))/((Math.pow(Math.tan(3*Math.PI/10),2)+1)))) *(point1.getX()-getX()+(float)Math.sqrt((Math.pow(r,2))/((Math.pow(Math.tan(3*Math.PI/10),2)+1)))) +  getY()+(float)Math.tan(3*Math.PI/10)*(float)Math.sqrt((Math.pow(r,2))/((Math.pow(Math.tan(3*Math.PI/10),2)+1)))); 
        return (float)0.5*(float)Math.abs(point5.getX()-point2.getX())*(float)Math.abs(point1.getY()-point5.getY())-(float)0.5*(float)Math.abs(point5.getX()-point2.getX())*(float)Math.abs(point6.getY()-point5.getY()) + (float)Math.abs(point5.getX()-point2.getX())*(float)Math.abs(point3.getY()-point5.getY()) - 2*(float)Math.abs(point5.getX()-point2.getX())*(float)Math.abs(point6.getY()-point5.getY());
    }

	@Override
    float computePerimeter(){
		Point point6 = new Point(  point1.getX() , (getY()+(float)Math.tan(9*Math.PI/10)*(float)Math.sqrt((Math.pow(r,2))/((Math.pow(Math.tan(9*Math.PI/10),2)+1))) - getY()+(float)Math.tan(3*Math.PI/10)*(float)Math.sqrt((Math.pow(r,2))/((Math.pow(Math.tan(3*Math.PI/10),2)+1))))  /  ( getX()-(float)Math.sqrt((Math.pow(r,2))/((Math.pow(Math.tan(9*Math.PI/10),2)+1))) -  getX()+(float)Math.sqrt((Math.pow(r,2))/((Math.pow(Math.tan(3*Math.PI/10),2)+1)))) *(point1.getX()-getX()+(float)Math.sqrt((Math.pow(r,2))/((Math.pow(Math.tan(3*Math.PI/10),2)+1)))) +  getY()+(float)Math.tan(3*Math.PI/10)*(float)Math.sqrt((Math.pow(r,2))/((Math.pow(Math.tan(3*Math.PI/10),2)+1))));
        return (float)Math.sqrt(Math.pow(Math.abs(point5.getX()-point6.getX()),2)+Math.pow(Math.abs(point5.getY()-point6.getY()),2))*10;
    }

	@Override
    void move(float dx, float dy){
        point1.move(dx,dy);
        point2.move(dx,dy);
        point3.move(dx,dy);
        point4.move(dx,dy);
        point5.move(dx,dy);
    }

	@Override
    void scale(float s){
		Point sr1 = new Point(point1.getX(),point1.getY()+r);
		r*=s;
		point1.x*=s; point1.y*=s;
		point2.x*=s; point2.y*=s;
		point3.x*=s; point3.y*=s;
		point4.x*=s; point4.y*=s;
		point5.x*=s; point5.y*=s;
		Point sr2 = new Point(point1.getX(),point1.getY()+r);
		float dx=sr1.x-sr2.x;
		float dy=sr1.y-sr2.y;
		point1.move(dx,dy);
		point2.move(dx,dy);
		point3.move(dx,dy);
		point4.move(dx,dy);
		point5.move(dx,dy);
    }

	@Override
    void draw(Graphics g,Color c){
		settColor(c);
		setColor(g);
        g.drawLine((int)point1.x, (int)point1.y,
                   (int)point2.x, (int)point2.y);
        g.drawLine((int)point2.x, (int)point2.y,
                   (int)point3.x, (int)point3.y);
        g.drawLine((int)point3.x, (int)point3.y,
                   (int)point4.x, (int)point4.y);
        g.drawLine((int)point4.x, (int)point4.y,
                (int)point5.x, (int)point5.y);
        g.drawLine((int)point5.x, (int)point5.y,
                (int)point1.x, (int)point1.y);
        int pointsx[] = {(int)point1.x,(int)point2.x,(int)point3.x,(int)point4.x,(int)point5.x};
        int pointsy[] = {(int)point1.y,(int)point2.y,(int)point3.y,(int)point4.y,(int)point5.y};
        g.fillPolygon(pointsx, pointsy, 5);
    }

}

class Picture extends JPanel implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

	private static final long serialVersionUID = 1L;

	private Vector<Figure> figures = new Vector<Figure>();
	
	float mouseX, mouseY;
	
	/*
	 * UWAGA: ta metoda bedzie wywolywana automatycznie przy kazdej potrzebie
	 * odrysowania na ekranie zawartosci panelu
	 *
	 * W tej metodzie NIE WOLNO !!! wywolywac metody repaint()
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (Figure f : figures){
			f.draw(g,f.getKolor());
		}
	}
	
	void setColorr(Color kolorr){
		for (Figure f : figures){
			if(f.isSelected()) f.settColor(kolorr);
		}
		repaint();
	}
	
    void addFigure(Figure fig)
    { for (Figure f : figures){ f.unselect(); }
      fig.select();
      figures.add(fig);
      repaint();
    }


    void moveAllFigures(float dx, float dy){
    	for (Figure f : figures){
    		if (f.isSelected()) f.move(dx,dy);
    	}
        repaint();
    }

    void scaleAllFigures(float s){
    	for (Figure f : figures)
        	{ if (f.isSelected()) f.scale(s);
        	}
         repaint();
    }

    public String toString(){
        String str = "Rysunek{ ";
        for(Figure f : figures)
            str+=f.toString() +"\n         ";
        str+="}";
        return str;
    }


    /*
     *  Impelentacja interfejsu KeyListener - obsluga zdarzen generowanych
     *  przez klawiature gdy focus jest ustawiony na ten obiekt.
     */
    public void keyPressed (KeyEvent evt)
    //Virtual keys (arrow keys, function keys, etc) - handled with keyPressed() listener.
    {  int dist;
       if (evt.isShiftDown()) dist = 10;
                         else dist = 1;
		switch (evt.getKeyCode()) {
		case KeyEvent.VK_UP:
			moveAllFigures(0, -dist);
			break;
		case KeyEvent.VK_DOWN:
			moveAllFigures(0, dist);
			break;
		case KeyEvent.VK_LEFT:
			moveAllFigures(-dist,0);
			break;
		case KeyEvent.VK_RIGHT:
			moveAllFigures(dist,0);
			break;
		case KeyEvent.VK_DELETE:
			Iterator<Figure> i = figures.iterator();
			while (i.hasNext()) {
				Figure f = i.next();
				if (f.isSelected()) {
					i.remove();
				}
			}
			repaint();
			break;
		}
    }

   public void keyReleased (KeyEvent evt)
   {  }

   public void keyTyped (KeyEvent evt)
   {
     char znak=evt.getKeyChar(); //reakcja na przycisku na nacisniecie klawisza
		switch (znak) {
		case 'p':
			Point p=new Point();
			addFigure(p);
			break;
		case 'c':
			Circle c=new Circle();
			addFigure(c);
			break;
		case 't':
			Triangle t=new Triangle();
			addFigure(t);
			break;
		case 's':
			Square s=new Square();
			addFigure(s);
			break;
		case 'a':
			Star ss=new Star();
			addFigure(ss);
			break;
		case 'r':
			Rectangle r=new Rectangle();
			addFigure(r);
			break;
			
		case '+':
			scaleAllFigures(1.1f);
			break;
		case '-':
			scaleAllFigures(0.9f);
			break;
		}
   }

   /*
    *  Impelentacja interfejsu MouseWheelListener - obsluga zdarzen generowanych
    *  przez rolke myszki.
    */
   public void mouseWheelMoved(MouseWheelEvent e) {
       int notches = e.getWheelRotation();
       scaleAllFigures(1-notches*(float)0.1);
    }

   /*
    * Implementacja interfejsu MouseListener - obsluga zdarzen generowanych przez myszke
    * gdy kursor myszki jest na tym panelu
    */
   public void mouseClicked(MouseEvent e)
   // Invoked when the mouse button has been clicked (pressed and released) on a component.
   { int px = e.getX();
     int py = e.getY();
     for (Figure f : figures)
       { if (e.isAltDown()==false) f.unselect();
         if (f.isInside(px,py)) f.select( !f.isSelected() );
       }
     repaint();
   }

   public void mouseEntered(MouseEvent e)
   //Invoked when the mouse enters a component.
   { }

   public void mouseExited(MouseEvent e)
   //Invoked when the mouse exits a component.
   { }


	public void mousePressed(MouseEvent e)
	// Invoked when a mouse button has been pressed on a component.
	{
		mouseX=e.getX();
		mouseY=e.getY();
	}

   public void mouseReleased(MouseEvent e)
   //Invoked when a mouse button has been released on a component.
   { }

   /*
    * Implementacja interfejsu MouseMotionListener
    */
   public void mouseDragged(MouseEvent e){
	   moveAllFigures(e.getX()-mouseX,e.getY()-mouseY);
	   mouseX=e.getX();
	   mouseY=e.getY();
   }
   
   public void mouseMoved(MouseEvent e){
	   
   }
   
}



public class GraphicEditor extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 3727471814914970170L;
	
	private final String AUTHOR = "Autor: Adam Filipowicz";

	private final String DESCRIPTION = "OPIS PROGRAMU\n\n" + "Aktywna klawisze:\n"
			+ "   strzalki ==> przesuwanie figur\n"
			+ "   SHIFT + strzalki ==> szybkie przesuwanie figur\n"
			+ "   +,-  ==> powiekszanie, pomniejszanie\n"
			+ "   DEL  ==> kasowanie figur\n"
			+ "   p  ==> dodanie nowego punktu\n"
			+ "   c  ==> dodanie nowego kola\n"
			+ "   t  ==> dodanie nowego trojkata\n"
			+ "   s  ==> dodanie nowego kwadratu\n"
			+ "   a  ==> dodanie nowej gwiazdy\n"
			+ "   r  ==> dodanie nowego prostokata\n"
			+ "\nOperacje myszka:\n" + "   klik ==> zaznaczanie figur\n"
			+ "   ALT + klik ==> zmiana zaznaczenia figur\n"
			+ "   przeciaganie ==> przesuwanie figur";

	private final String ADDITION = 
			 "  - W menu 'Podaj pozycje' mozemy utworzyc kazda z figur\n"
			+"  podajac punkt srodkowy i promien (w wiekszosci przypadkow).\n"
			+"  - Uzywajac 'scrolla' myszy mozemy powiekszac i zmniejszac figury.\n"
			+"  - Figury tworza sie wypelnione na kolor niebieski.\n"
			+"  - W menu 'Pomaluj' mozemy zaznaczone figury pomalowac\n"
			+"  na podane kolory: zolty, niebieski, czarny, cyjan, zielony,\n"
			+"  pomaranczowy.";

	protected Picture picture;

	private JMenu[] menu = { new JMenu("Figury"),
			                 new JMenu("Edytuj"),
			                 new JMenu("Pomaluj"),
			                 new JMenu("Podaj pozycje"),
			                 new JMenu("Pomoc")};

	private JMenuItem[] items = { new JMenuItem("Punkt"),
			                      new JMenuItem("Kolo"),
			                      new JMenuItem("Trojkat"),
			                      new JMenuItem("Kwadrat"),
			                      new JMenuItem("Gwiazda"),
			                      new JMenuItem("Prostokat"),
			                      new JMenuItem("Wypisz wszystkie"),
			                      new JMenuItem("Przesun w gore"),
			                      new JMenuItem("Przesun w dol"),
			                      new JMenuItem("Przesun w lewo"),
			                      new JMenuItem("Przesun w prawo"),
			                      new JMenuItem("Powieksz"),
			                      new JMenuItem("Pomniejsz"),
			                      new JMenuItem("Pomaluj na zolto"),
			                      new JMenuItem("Pomaluj na niebiesko"),
			                      new JMenuItem("Pomaluj na czarno"),
			                      new JMenuItem("Pomaluj na cyjanowo"),
			                      new JMenuItem("Pomaluj na zielono"),
			                      new JMenuItem("Pomaluj na pomaranczowo"),
			                      new JMenuItem("Punkt"),
			                      new JMenuItem("Kolo"),
			                      new JMenuItem("Trojkat"),
			                      new JMenuItem("Kwadrat"),
			                      new JMenuItem("Gwiazda"),
			                      new JMenuItem("Prostokat"),
			                      new JMenuItem("Autor"),
			                      new JMenuItem("Opis programu"),
			                      new JMenuItem("Dodatkowe opcje"),
			                      };

	private JButton buttonPoint = new JButton("Punkt");
	private JButton buttonCircle = new JButton("Kolo");
	private JButton buttonTriangle = new JButton("Trojkat");
	private JButton buttonSquare = new JButton("Kwadrat");
	private JButton buttonStar = new JButton("Gwiazda");
	private JButton buttonRectangle = new JButton("Prostokat");
	
    public GraphicEditor(){ 
  	  super ("Edytor graficzny");
      setSize(400,400);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      for (int i = 0; i < items.length; i++)
      	items[i].addActionListener(this);

      // dodanie opcji do menu "Figury"
      menu[0].add(items[0]);
      menu[0].add(items[1]);
      menu[0].add(items[2]);
      menu[0].add(items[3]);
      menu[0].add(items[4]);
      menu[0].add(items[5]);
      menu[0].addSeparator();
      menu[0].add(items[6]);

      // dodanie opcji do menu "Edytuj"
      menu[1].add(items[7]);
      menu[1].add(items[8]);
      menu[1].add(items[9]);
      menu[1].add(items[10]);
      menu[1].addSeparator();
      menu[1].add(items[11]);
      menu[1].add(items[12]);
      
      //menu "Pomaluj"
      menu[2].add(items[13]);
      menu[2].add(items[14]);
      menu[2].add(items[15]);
      menu[2].add(items[16]);
      menu[2].add(items[17]);
      menu[2].add(items[18]);
      
      //menu "Podaj pozycje"
      menu[3].add(items[19]);
      menu[3].add(items[20]);
      menu[3].add(items[21]);
      menu[3].add(items[22]);
      menu[3].add(items[23]);
      menu[3].add(items[24]);
      
      //dodanie opcji do menu "Pomoc"
      menu[4].add(items[25]);
      menu[4].add(items[26]);
      menu[4].add(items[27]);
      
      // dodanie do okna paska menu
      JMenuBar menubar = new JMenuBar();
      for (int i = 0; i < menu.length; i++)
      	menubar.add(menu[i]);
      setJMenuBar(menubar);

      picture=new Picture();
      picture.addKeyListener(picture);
      picture.setFocusable(true);
      picture.addMouseListener(picture);
      picture.addMouseMotionListener(picture);
      picture.addMouseWheelListener(picture);
      picture.setLayout(new FlowLayout());

      buttonPoint.addActionListener(this);
      buttonCircle.addActionListener(this);
      buttonTriangle.addActionListener(this);
      buttonSquare.addActionListener(this);
      buttonStar.addActionListener(this);
      buttonRectangle.addActionListener(this);

      picture.add(buttonPoint);
      picture.add(buttonCircle);
      picture.add(buttonTriangle);
      picture.add(buttonSquare);
      picture.add(buttonStar);
      picture.add(buttonRectangle);

      setContentPane(picture);
      setVisible(true);
    }

	public void actionPerformed(ActionEvent evt) {
		Object zrodlo = evt.getSource();

		if (zrodlo == buttonPoint)
			picture.addFigure(new Point());
		if (zrodlo == buttonCircle)
			picture.addFigure(new Circle());
		if (zrodlo == buttonTriangle)
			picture.addFigure(new Triangle());
		if (zrodlo == buttonSquare)
			picture.addFigure(new Square());
		if (zrodlo == buttonStar)
			picture.addFigure(new Star());
		if (zrodlo == buttonRectangle)
			picture.addFigure(new Rectangle());

		if (zrodlo == items[0])
			picture.addFigure(new Point());
		if (zrodlo == items[1])
			picture.addFigure(new Circle());
		if (zrodlo == items[2])
			picture.addFigure(new Triangle());
		if (zrodlo == items[3])
			picture.addFigure(new Square());
		if (zrodlo == items[4])
			picture.addFigure(new Star());
		if (zrodlo == items[5])
			picture.addFigure(new Rectangle());
		if (zrodlo == items[6])
			JOptionPane.showMessageDialog(null, picture.toString());

		if (zrodlo == items[7])
			picture.moveAllFigures(0, -10);
		if (zrodlo == items[8])
			picture.moveAllFigures(0, 10);
		if (zrodlo == items[9])
			picture.moveAllFigures(-10, 0);
		if (zrodlo == items[10])
			picture.moveAllFigures(10, 0);
		if (zrodlo == items[11])
			picture.scaleAllFigures(1.1f);
		if (zrodlo == items[12])
			picture.scaleAllFigures(0.9f);

		if (zrodlo == items[13]){
			Color k=Color.getColor("tekst",Color.YELLOW);
			picture.setColorr(k);
		}
		if (zrodlo == items[14]){
			Color k=Color.getColor("tekst",Color.BLUE);
			picture.setColorr(k);
		}
		if (zrodlo == items[15]){
			Color k=Color.getColor("tekst",Color.BLACK);
			picture.setColorr(k);
		}
		if (zrodlo == items[16]){
			Color k=Color.getColor("tekst",Color.CYAN);
			picture.setColorr(k);
		}
		if (zrodlo == items[17]){
			Color k=Color.getColor("tekst",Color.GREEN);
			picture.setColorr(k);
		}
		if (zrodlo == items[18]){
			Color k=Color.getColor("tekst",Color.ORANGE);
			picture.setColorr(k);
		}
		
		if (zrodlo == items[19]){
			String x=JOptionPane.showInputDialog(null, "Podaj wspolrzedna x punktu");
			float x1=Float.parseFloat(x);
			String y=JOptionPane.showInputDialog(null, "Podaj wspolrzedna y punktu");
			float y1=Float.parseFloat(y);
			picture.addFigure(new Point(x1,y1));
		}
		if (zrodlo == items[20]){
			String x=JOptionPane.showInputDialog(null, "Podaj wspolrzedna x punktu");
			float x1=Float.parseFloat(x);
			String y=JOptionPane.showInputDialog(null, "Podaj wspolrzedna y punktu");
			float y1=Float.parseFloat(y);
			String r=JOptionPane.showInputDialog(null, "Podaj promien r");
			float r1=Float.parseFloat(r);
			picture.addFigure(new Circle(x1,y1,r1));
		}
		if (zrodlo == items[21]){
			String x=JOptionPane.showInputDialog(null, "Podaj wspolrzedna x pierwszego punktu");
			float x1=Float.parseFloat(x);
			String y=JOptionPane.showInputDialog(null, "Podaj wspolrzedna y pierwszego punktu");
			float y1=Float.parseFloat(y);
			x=JOptionPane.showInputDialog(null, "Podaj wspolrzedna x drugiego punktu");
			float x2=Float.parseFloat(x);
			y=JOptionPane.showInputDialog(null, "Podaj wspolrzedna y drugiego punktu");
			float y2=Float.parseFloat(y);
			x=JOptionPane.showInputDialog(null, "Podaj wspolrzedna x trzeciego punktu");
			float x3=Float.parseFloat(x);
			y=JOptionPane.showInputDialog(null, "Podaj wspolrzedna y trzeciego punktu");
			float y3=Float.parseFloat(y);
			Point point1 = new Point(x1,y1);
			Point point2 = new Point(x2,y2);
			Point point3 = new Point(x3,y3);
			picture.addFigure(new Triangle(point1,point2,point3));
		}
		if (zrodlo == items[22]){
			String x=JOptionPane.showInputDialog(null, "Podaj wspolrzedna x punktu");
			float x1=Float.parseFloat(x);
			String y=JOptionPane.showInputDialog(null, "Podaj wspolrzedna y punktu");
			float y1=Float.parseFloat(y);
			String r=JOptionPane.showInputDialog(null, "Podaj odleglosc r (polowa boku kwadratu)");
			float r1=Float.parseFloat(r);
			picture.addFigure(new Square(x1,y1,r1));
		}
		if (zrodlo == items[23]){
			String x=JOptionPane.showInputDialog(null, "Podaj wspolrzedna x punktu");
			float x1=Float.parseFloat(x);
			String y=JOptionPane.showInputDialog(null, "Podaj wspolrzedna y punktu");
			float y1=Float.parseFloat(y);
			String r=JOptionPane.showInputDialog(null, "Podaj odleglosc r miedzy srodkiem a kazdym z wierzcholkow gwiazdy");
			float r1=Float.parseFloat(r);
			picture.addFigure(new Star(x1,y1,r1));
		}
		if (zrodlo == items[24]){
			String x=JOptionPane.showInputDialog(null, "Podaj wspolrzedna x punktu");
			float x1=Float.parseFloat(x);
			String y=JOptionPane.showInputDialog(null, "Podaj wspolrzedna y punktu");
			float y1=Float.parseFloat(y);
			String r=JOptionPane.showInputDialog(null, "Podaj odleglosc r1 (polowa pionowych bokow kwadratu");
			float r1=Float.parseFloat(r);
			r=JOptionPane.showInputDialog(null, "Podaj odleglosc r2 (polowa poziomych bokow kwadratu");
			float r2=Float.parseFloat(r);
			picture.addFigure(new Rectangle(x1,y1,r1,r2));
		}
		
		if (zrodlo == items[25])
			JOptionPane.showMessageDialog(null, AUTHOR);
		if (zrodlo == items[26])
			JOptionPane.showMessageDialog(null, DESCRIPTION);
		if (zrodlo == items[27])
			JOptionPane.showMessageDialog(null, ADDITION);
		
		picture.requestFocus(); // przywrocenie ogniskowania w celu przywrocenia
								// obslugi zadarzen od klawiatury
		repaint();
	}

    public static void main(String[] args){ 
    	new GraphicEditor();
    }

}

