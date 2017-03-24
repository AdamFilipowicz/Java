/* 
 *  Klasa MiniSklep
 *  Autor: Adam Filipowicz
 *  Nr indeksu: 221713
 *  Data: 02 listopada 2016 r.
 */

/**
 *  Klasa zawiera wiele metod u¿ywanych w klasie MiniShopApplication,
 *  oraz bazuje na obiektach klasy Konto i Towar.
 *	Klasa posiada takze listy w ktorych sa one przechowywane.
 *  
 *  @author Adam Filipowicz
 *  @version 02.11.2016
 */

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;


class MiniSklep implements Serializable {
	
	/**
	 * Zmienna przechowuje utarg sklepu (wczytywany i zapisywany do pliku).
	 */
	private double utarg=0;
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Lista typu Konto w ktorej przechowywane sa wszystkie konta (wczytywana i zapisywana do pliku).
	 */
	private ArrayList<Konto> listaKont = new ArrayList<Konto>();
	
	/**
	 * Lista typu Towar w ktorej przechowywane sa wszystkie towary (wczytywana i zapisywana do pliku).
	 */
	private ArrayList<Towar> listaTowarow = new ArrayList<Towar>();
	
	/**
	 * Lista typu String w ktorej przechowywana jest historia zakupow (wczytywana i zapisywana do pliku).
	 */
	private ArrayList<String> listaZakupow = new ArrayList<String>();
	
	/**
	 * Metoda sprawdza czy istnieje juz sprzedawca (czy jakies konto jest juz utworzone).
	 * @return true - gdy istnieje konto sprzedawcy
	 * 		   false - gdy nie istnieje konto sprzedawcy
	 */
	public boolean sprawdzCzyIstniejeSprzedawca(){
		if(listaKont.isEmpty()) return false;
		return true;
	}
	
	/**
	 * Metoda sprawdza czy podane konto nalezy do sprzedawcy.
	 * @param daneKonto - podane konto, do sprawdzenia
	 * @return true - gdy podane konto faktycznie jest kontem sprzedawcy
	 * 		   false - gdy podane konto nie jest kontem sprzedawcy
	 */
	public boolean sprawdzCzyKontoSprzedawcy(Konto daneKonto){
		if(daneKonto==listaKont.get(0)) return true;
		return false;
	}
	
	/**
	 * Metoda daje dostep do konta sprzedawcy.
	 * @return obiekt typu Konto ktory jest kontem sprzedawcy
	 */
	public Konto getKontoSprzedawcy(){
		return listaKont.get(0);
	}
	
	/**
	 * Metoda dodaje towar o podanej nazwie do tablicy towarow.
	 * @param nazwa - nazwa towaru do dodania
	 * @return obiekt Towar ktory zostal dodany do tablicy towarow
	 * @throws Exception - wyjatek zglaszany, gdy nazwa towaru jest pusta lub gdy towar juz istnieje
	 */
	public Towar dodajTowar(String nazwa) throws Exception{
		if(nazwa==null || nazwa.equals("")) throw(new Exception("Nazwa towaru nie moze byc pusta"));
		if (znajdzTowar(nazwa)!=null) throw(new Exception("Towar juz istnieje"));
		Towar nowyTowar = new Towar(nazwa);
		listaTowarow.add(nowyTowar);
		return nowyTowar;
	}
	
	/**
	 * Metoda wycofuje towar o podanej nazwie ze sprzedazy (usuwa z tablicy)
	 * @param nazwa - nazwa towaru do usuniecia
	 * @throws Exception - wyjatek zglaszany, gdy nazwa towaru jest pusta lub gdy nazwa towaru jest bledna
	 */
	public void wycofajTowar(String nazwa) throws Exception{
		if(nazwa==null) throw(new Exception("Brak towaru"));
		Towar towar=znajdzTowar(nazwa);
		if (towar==null) throw(new Exception("Nie znaleziono danego towaru"));
		listaTowarow.remove(towar);
	}
	
	/**
	 * Metoda znajduje towar o podanej nazwie na liscie towarow i zwraca go.
	 * @param nazwa - nazwa wyszukiwanego towaru
	 * @return towar - gdy towar zostanie znaleziony na liscie
	 * 		   null - gdy towar nie zostanie znaleziony na liscie
	 */
	public Towar znajdzTowar(String nazwa){
		for(Towar towar: listaTowarow)
			if(towar.getNazwa().equals(nazwa)) return towar;
		return null;
	}
	
	/**
	 * Metoda zwracajaca liste towarow jako String.
	 * @return lista towarow jako String
	 */
	public String wypiszListeTowarow(){
		StringBuilder sb = new StringBuilder();
		int n = 0;
		for (Towar towar : listaTowarow){
			if (n++ != 0) sb.append("\n");		
			sb.append(towar.toString());
		}
		return sb.toString();
	}
	
	/**
	 * Metoda dodajaca podany utarg do ogolnego utargu.
	 * @param dodajUtarg - liczba do dodania do utargu.
	 */
	public void aktualizujUtarg(double dodajUtarg){
		this.utarg+=dodajUtarg;
	}
	
	/**
	 * Metoda zwracajaca wartosc utargu.
	 * @return utarg - wartosc utargu
	 */
	public double sprawdzUtarg(){
		return utarg;
	}
	
	/**
	 * Metoda dodaje konto sprzedawcy o podanej nazwie do tablicy kont.
	 * @param nazwa - nazwa konta do dodania
	 * @return obiekt Konto ktore zostalo dodane do tablicy kont
	 * @throws Exception - wyjatek zglaszany, gdy nazwa konta jest pusta
	 */
	public Konto zalozKontoSprzedawcy(String nazwa) throws Exception {
		if (nazwa==null || nazwa.equals("")) throw(new Exception("Nazwa konta nie moze byc pusta"));
		Konto noweKonto = new Konto(nazwa);
		listaKont.add(noweKonto);
		return noweKonto;
	}
	
	/**
	 * Metoda dodaje konto o podanej nazwie do tablicy kont.
	 * @param nazwa - nazwa konta do dodania
	 * @return obiekt Konto ktore zostalo dodane do tablicy kont
	 * @throws Exception - wyjatek zglaszany, gdy nazwa konta jest pusta lub gdy konto o podanej nazwie juz istnieje
	 */
	public Konto zalozKonto(String nazwa) throws Exception {
		if (nazwa==null || nazwa.equals("")) throw(new Exception("Nazwa konta nie moze byc pusta"));
		if (znajdzKonto(nazwa)!=null) throw(new Exception("Konto juz istnieje"));
		Konto noweKonto = new Konto(nazwa);
		listaKont.add(noweKonto);
		return noweKonto;
	}
	
	/**
	 * Metoda usuwa podane konto (usuwa z tablicy)
	 * @param nazwa - nazwa konta do usuniecia
	 * @throws Exception - wyjatek zglaszany, gdy nazwa konta jest pusta lub gdy na koncie pozostaly jeszcze pieniadze
	 */
	public void usunKonto(Konto konto) throws Exception {
		if (konto==null) throw(new Exception("Brak konta"));
		if (konto.getKwota()!=0) throw(new Exception("Na koncie pozostaly jeszcze pieniadze"));
		listaKont.remove(konto);
	}
	
	/**
	 * Metoda znajduje konto o podanej nazwie na liscie kont i zwraca go.
	 * @param nazwa - nazwa wyszukiwanego konta
	 * @return konto - gdy konto zostanie znalezione na liscie
	 * 		   null - gdy konto nie zostanie znalezione na liscie
	 */
	public Konto znajdzKonto(String nazwa) {
		for (Konto konto : listaKont)
			if (konto.getNazwa().equals(nazwa)) return konto;
		return null;
	}
	
	/**
	 * Metoda znajduje konto o podanej nazwie na liscie kont i zwraca go jesli podano poprawne haslo.
	 * @param nazwa - nazwa wyszukiwanego konta
	 * @param haslo - haslo wyszukiwanego konta
	 * @return konto - gdy konto zostanie znalezione na liscie i haslo jest poprawne
	 * 		   null - gdy konto nie zostanie znalezione na liscie lub/i gdy haslo jest niepoprawne
	 */
	public Konto znajdzKonto(String nazwa, String haslo) {
		Konto konto = znajdzKonto(nazwa);
		if (konto!=null){
			if (!konto.sprawdzHaslo(haslo)) konto = null;
		}
		return konto;
	}
	
	/**
	 * Metoda zwracajaca liste kont jako String.
	 * @return lista kont jako String
	 */
	public String listaKont(){
		StringBuilder sb = new StringBuilder();
		int n = 0;
		for (Konto konto : listaKont){
			if (n++ != 0) sb.append("\n");		
			sb.append(konto.toString());
		}
		return sb.toString();
	}
	
	/**
	 * Metoda dodaje zakup o podanych parametrach do historii zakupow.
	 * @param _nazwa - nazwa towaru
	 * @param _cena - cena towaru
	 * @param _ilosc - ilosc zakupionego towaru
	 * @param _wartosc - wartosc towaru
	 * @param _nazwaKlienta - nazwa klienta ktory zakupil towar
	 */
	public void dodajDoHistoriiZakupow(String _nazwa, double _cena, int _ilosc, double _wartosc, String _nazwaKlienta){
		String tekst="Nazwa: "+_nazwa+" Cena: "+_cena+" Ilosc: "+_ilosc+" Wartosc: "+_wartosc+" "+_nazwaKlienta;
		listaZakupow.add(tekst);
	}
	
	/**
	 * Metoda zwracajaca historie zakupow dla danego konta jako String.
	 * @return historia zakupow dla danego konta jako String
	 */
	public String wyswietlHistorieZakupow(Konto konto){
		if(listaZakupow==null)
			return "Lista zakupow jest pusta";
		StringBuilder sb = new StringBuilder();
		int n = 0;
		for (String zakup : listaZakupow){
			int ostatniZnak=zakup.length();
			int pierwszyZnak=ostatniZnak;
			String znak=zakup.substring(pierwszyZnak-1,pierwszyZnak);
			while(!znak.equals(" ")){
				pierwszyZnak--;
				znak=zakup.substring(pierwszyZnak-1,pierwszyZnak);
			}
			if(zakup.substring(pierwszyZnak, ostatniZnak).equals(konto.getNazwa())){
				if (n++ != 0) sb.append("\n");	
				sb.append(zakup.substring(0,pierwszyZnak));
			}
		}
		return sb.toString();
	}
	
	/**
	 * Metoda zwracajaca laczna zaplate z historii zakupow dla danego konta (potrzebna przy fakturze).
	 * @return laczna zaplata z historii zakupow dla danego konta
	 */
	public double wyswietlZaplate(Konto konto){
		if(listaZakupow==null)
			return 0;
		double suma=0.0;
		etykieta:
		for (String zakup : listaZakupow){
			int ostatniZnak=zakup.length();
			int pierwszyZnak=ostatniZnak;
			int licznik=0;
			String znak=zakup.substring(pierwszyZnak-1,pierwszyZnak);
			while(licznik<2){
				while(!znak.equals(" ")){
					pierwszyZnak--;
					znak=zakup.substring(pierwszyZnak-1,pierwszyZnak);
				}
				licznik++;
				pierwszyZnak--;
				znak=zakup.substring(pierwszyZnak-1,pierwszyZnak);
				if(licznik==1){
					if(!zakup.substring(pierwszyZnak+1, ostatniZnak).equals(konto.getNazwa()))
						continue etykieta;
					ostatniZnak=pierwszyZnak+1;
				}
			}
			pierwszyZnak++;
			suma+=Double.parseDouble(zakup.substring(pierwszyZnak, ostatniZnak));
		}
		return suma;
	}
	
	/**
	 * Metoda zapisujaca do pliku liste kont, liste towarow, utarg oraz historie zakupow.
	 * @param fileName - nazwa pliku do zapisania
	 * @throws Exception - wyjatek
	 */
	void zapiszDoPliku(String fileName) throws Exception {
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
		out.writeObject(listaKont);
		out.writeObject(listaTowarow);
		out.writeObject(utarg);
		out.writeObject(listaZakupow);
		out.close();
	}
	
	/**
	 * Metoda wczytujaca z pliku liste kont, liste towarow, utarg oraz historie zakupow.
	 * @param fileName - nazwa pliku do wczytania informacji
	 * @throws Exception - wyjatek
	 */
	void wczytajZPliku(String fileName) throws Exception {
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
		listaKont = (ArrayList<Konto>)in.readObject();
		listaTowarow = (ArrayList<Towar>)in.readObject();
		utarg = (double)in.readObject();
		listaZakupow=(ArrayList<String>)in.readObject();
		in.close();
	}
	
}