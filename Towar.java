/* 
 *  Klasa Towar
 *  Autor: Adam Filipowicz
 *  Nr indeksu: 221713
 *  Data: 02 listopada 2016 r.
 */

/**
 *  Klasa, ktorej obiektami sa towary ze sklepu.
 *  Maja one rozne atrybuty: nazwa, cena, ilosc, nazwaKlienta, wartosc.
 *  Klasa pozwala na dostep do nich i zmiane czesci z nich.
 * 
 *  @author Adam Filipowicz
 *  @version 02.11.2016
 */

import java.io.Serializable;


public class Towar implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/**
	 * Niezmienna nazwa konta.
	 */
	private String nazwa;
	
	/**
	 * Cena towaru. Jest mozliwa zmiana ceny.
	 */
	private double cena;  
	
	/**
	 * Ilosc towaru w magazynie. Jest mozliwa zmiana ilosci,
	 * zakup podanej ilosci (odjecie od aktualnej) i
	 * dodanie podanej ilosci (dodanie do aktualniej).
	 */
	private int ilosc;
	
	/**
	 * Nazwa klienta ktory zakupil dany towar.
	 */
	private String nazwaKlienta;
	
	/**
	 * Wartosc zakupu (cena*ilosc).
	 */
	private double wartosc;
	
	/**
	 * Konstruktor parametrowy.
	 * @param nazwa - nazwa z jaka chcemy utworzyc nowy towar.
	 */
	Towar(String nazwa){
		this.nazwa = nazwa;
		this.cena = 0;
		this.ilosc = 0;
		this.nazwaKlienta = "";
	}
	
	/**
	 * Konstruktor parametrowy.
	 * @param nazwa - nazwa z jaka chcemy utworzyc nowy towar.
	 * @param cena - cena z jaka chcemy utworzyc nowy towar.
	 * @param ilosc - ilosc towaru z jaka chcemy utworzyc nowy towar.
	 */
	Towar(String nazwa, double cena, int ilosc){
		this.nazwa=nazwa;
		this.cena=cena;
		this.ilosc=ilosc;
		this.nazwaKlienta = "";
	}

	/**
	 * Metoda zwracajaca nazwe towaru.
	 * @return nazwa - nazwa towaru.
	 */
	public String getNazwa(){
		return nazwa;
	}

	/**
	 * Metoda zwracajaca nazwe klienta.
	 * @return nazwaKlienta - nazwa klienta.
	 */
	public String getKlient(){
		return nazwaKlienta;
	}
	
	/**
	 * Metoda ustawiajaca dana nazwe jako nazwe klienta.
	 * @param klient - nazwa klienta ktora chcemy przypisac towarowi
	 */
	public void setKlient(String klient){
		this.nazwaKlienta=klient;
	}
	
	/**
	 * Metoda zwracajaca cene towaru.
	 * @return cena - cena towaru.
	 */
	public double getCena(){
		return cena;
	}
	
	/**
	 * Metoda ustawiajaca dana cene jako cena towaru.
	 * @param nowaCena - cena jaka chcemy przypisac cenie towaru.
	 */
	public void setCena(double nowaCena){
		this.cena=nowaCena;
	}
	
	/**
	 * Metoda zwracajaca wartosc towaru.
	 * @return wartosc - wartosc towaru.
	 */
	public double getWartosc(){
		return wartosc;
	}
	
	/**
	 * Metoda ustawiajaca dana wartosc jako wartosc towaru.
	 * @param nowaWartosc - wartosc jaka chcemy przypisac wartosci towaru.
	 */
	public void setWartosc(double nowaWartosc){
		this.wartosc=nowaWartosc;
	}
	
	/**
	 * Metoda zwracajaca ilosc towaru.
	 * @return ilosc - ilosc towaru.
	 */
	public int getIlosc(){
		return ilosc;
	}
	
	/**
	 * Metoda zmniejszajaca ilosc towaru o podana ilosc. ( zakup danej ilosci towaru przez klienta )
	 * @param iloscKupiona - ilosc jaka chcemy odjac od ilosci naszego towaru.
	 */
	public void minusIlosc(int iloscKupiona){
		this.ilosc=this.ilosc-iloscKupiona;
	}
	
	/**
	 * Metoda zwiekszajaca ilosc towaru o podana ilosc. ( dodanie danej ilosci towaru do sklepu )
	 * @param iloscDodana - ilosc jaka chcemy dodac do ilosci naszego towaru.
	 */
	public void addIlosc(int iloscDodana){
		this.ilosc=this.ilosc+iloscDodana;
	}
	
	/**
	 * Metoda zwraca reprezentacje kilku atrybutow obiektow jako string: nazwa konta,
	 * cena i ilosc.
	 * @return Tekstowa postac atrybutow konta.
	 */
	public String toString(){
		return String.format("Nazwa: %s. Cena: %f. Ilosc: %d.", nazwa,cena,ilosc);
	}

	
}