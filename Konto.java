/* 
 *  Klasa Konto
 *  Autor: Adam Filipowicz
 *  Nr indeksu: 221713
 *  Data: 02 listopada 2016 r.
 *  
 */

/**
 *  Klasa, ktorej obiektami sa konta w sklepie. 
 *  Maja one rozne atrybuty: nazwa, nazwisko, adres, haslo oraz kwota.
 *  Klasa pozwala na dostep do nich i zmiane czesci z nich.
 *  
 *  @author Adam Filipowicz
 *  @version 02.11.2016
 */

import java.io.Serializable;


public class Konto implements Serializable {
	
	private final static long serialVersionUID = 1L;
	
	/**
	 * Niezmienna nazwa konta.
	 */
	private String nazwa;  
	
	/**
	 * Nazwisko wlasciciela konta. Jest mozliwa zmiana nazwiska.
	 */
	private String nazwisko; 
	
	/**
	 * Adres wlasciciela konta. Jest mozliwa zmiana adresu.
	 */
	private String adres;  
	
	/**
	 * Haslo do konta. Mozna je zmienic przez podanie starego hasla.
	 */
	private long haslo;   
	
	/**
	 * Kwota pieniedzy na koncie. Jest mozliwa wplata pieniedzy na konto, 
	 * wyplata z konta oraz przelew na inne konto.
	 */
	private double kwota;  
	
	/**
	 * Konstruktor parametrowy.
	 * @param nazwa - nazwa z jaka chcemy utworzyc nasze nowe konto
	 */
	Konto(String nazwa){
		this.nazwa = nazwa;
	}
	
	/**
	 * Metoda zwracajaca nazwe.
	 * @return nazwa konta
	 */
	public String getNazwa(){
		return nazwa;
	}
	
	/**
	 * Metoda przypisujaca atrybutowi nazwisko podanego String'a.
	 * @param nazwisko - nazwisko jakie chcemy ustawic dla naszego konta
	 */
	public void setNazwisko(String nazwisko){
		this.nazwisko = nazwisko;
	}
	
	/**
	 * Metoda zwracajaca nazwisko.
	 * @return nazwisko - nazwisko konta
	 */
	public String getNazwisko(){
		return nazwisko;
	}
	
	/**
	 * Metoda przypisujaca atrybutowi adres podanego String'a.
	 * @param adres - adres jaki chcemy ustawic dla naszego konta
	 */
	public void setAdres(String adres){
		this.adres = adres;
	}
	
	/**
	 * Metoda zwracajaca adres.
	 * @return adres - adres konta
	 */
	public String getAdres(){
		return adres;
	}
	
	/**
	 * Metoda sprawdzajaca czy podane haslo jest prawidlowe.
	 * @param podanehaslo - podane haslo do sprawdzenia
	 * @return - true, jesli haslo jest poprawna
	 * 		   - false, jesli haslo jest niepoprawne.
	 */
	public boolean sprawdzHaslo(String podanehaslo) {
		if (podanehaslo==null) return false;
		return podanehaslo.hashCode()==haslo;
	}
	
	/**
	 * Metoda ustawiajaca nowe haslo pod warunkiem podania poprawnego starego hasla.
	 * @param stareHaslo - stare haslo
	 * @param noweHaslo - nowe haslo
	 * @throws Exception - wyjatek zglaszany gdy stare haslo jest niepoprawne
	 */
	public void setHaslo(String stareHaslo, String noweHaslo) throws Exception {
		if (!sprawdzHaslo(stareHaslo)) throw new Exception("Bledne haslo");
		haslo = noweHaslo.hashCode(); 
	}
	
	/**
	 * Metoda zwracajaca kwote pieniedzy na koncie.
	 * @return kwota - kwota na koncie
	 */
	public double getKwota(){
		return kwota;
	}
	
	/**
	 * Metoda powiekszajaca kwote pieniedzy na koncie o podana kwote.
	 * @param kwotaWplaty - kwota do powiekszenia pieniedzy na koncie (uwaga: kwotaWplaty>0)
	 * @throws Exception - wyjatek zglaszany gdy podana kwota jest ujemna
	 */
	public void Wplac(double kwotaWplaty) throws Exception{
		if(kwotaWplaty<0) throw new Exception("Wplata nie moze byc ujemna");
		kwota+=kwotaWplaty;
	}
	
	/**
	 * Metoda zmniejszajaca kwote pieniedzy na koncie o podana kwote.
	 * @param kwotaWyplaty - kwota do wyplacenia z konta (uwaga: kwotaWyplaty>0 i kwotaWyplaty<kwota)
	 * @param twojeHaslo - haslo, do sprawdzenia
	 * @throws Exception - wyjatek zglaszany, gdy haslo jest bledne, gdy wyplata jest ujemna lub gdy
	 * kwotawyplaty przekrasza ilosc pieniedzy na naszym Koncie.
	 */
	public void Wyplac(double kwotaWyplaty, String twojeHaslo) throws Exception{
		if(!sprawdzHaslo(twojeHaslo)) throw new Exception("Bledne haslo!");
		if(kwotaWyplaty<0) throw new Exception("Wyplata nie moze byc ujemna");
		if(kwotaWyplaty>kwota) throw new Exception("Brak srodkow na koncie");
		kwota-=kwotaWyplaty;
	}
	
	/**
	 * Metoda przelewajaca podana ilosc pieniedzy na inne, podane konto.
	 * @param kwotaPrzelania - kwota pieniedzy do przelania na inne konto
	 * @param twojeHaslo - haslo, do sprawdzenia
	 * @param czyjesKonto - konto do przelania pieniedzy
	 * @throws Exception - wyjatek zglaszany, gdy podane haslo jest bledne, lub gdy podane konto nie istnieje
	 */
	public void Przelej(double kwotaPrzelania, String twojeHaslo, Konto czyjesKonto) throws Exception{
		if(!sprawdzHaslo(twojeHaslo)) throw new Exception("Bledne haslo!");
		if(czyjesKonto==null) throw new Exception("Konto docelowe nie istenieje");
		Wyplac(kwotaPrzelania,twojeHaslo);
		czyjesKonto.Wplac(kwotaPrzelania);
	}
	
	/**
	 * Metoda zwraca reprezentacje kilku atrybutow obiektow jako string: nazwa konta,
	 * nazwisko i adres.
	 * @return Tekstowa postac atrybutow konta.
	 */
	public String toString(){
		return String.format("Nazwa: %s. Nazwisko: %s. Adres: %s.", nazwa, nazwisko, adres);
	}
	
}