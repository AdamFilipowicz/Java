/* 
 *  Program MiniShopApplication
 *  Autor: Adam Filipowicz
 *  Nr indeksu: 221713
 *  Data: 23 pazdziernika 2016 r.
 */

/**
 * Klasa MiniShopApplication wykorzystuje metody klasy MiniSklep.
 * Pozwala ona m.in. na tworzeniu kont sprzedawcy, kupuj¹cych 
 * oraz logowania siê na nie.
 * 
 * Po zalogowaniu na konto sprzedawcy mamy zupe³nie inne mozliwoœci ni¿
 * po zalogowaniu na konto kupuj¹cego (np. tworzenie faktury, czy dodawanie towaru).
 * 
 *  @author Adam Filipowicz
 *  @version 02.11.2016
 */

public class MiniShopApplication {
    /**
     * Metoda rozpoczyna dzialanie aplikacji MiniShopApplication.
     *
     * @param args parametr z lini polecen, ktory steruje
     *        sposobem realizacji dialogu z uzytkownikiem.
     *        Dozwolone parametry to:
     *        <li><i>InputMode.CON</i> - wprowadzanie danych i
     *         wyswietlanie wynikow w oknie konsoli</li>
     *         <li><i>InputMode.GUI</i> - wprowadzanie danych i
     *         wyswietlanie wynikow w oknach dialogowych</li>
     *
     */
	public static void main(String[] args) {
		new MiniShopApplication();
	}
		
	/** Tu mozna wybrac sposob, w jaki aplikacja MiniShopApplication
	 * bedzie sie komunikowac z uzytkownikiem.
	 * 
	 * Klasa ConsoleUserInterface implenentuje metody umozliwiajace
	 * wyswietlanie komunikatow oraz wczytywanie danych bezposrednio
	 * w konsoli, czyli wykorzystywane sa standardowe strumienie 
	 * wejscia/wyjscia:  System.out,  System.in,   System.err
	 * 
	 * Klasa JOptionPaneUserDialog implenentuje metody umozliwiajace
	 * wyswietlanie komunikatow oraz wczytywanie danych w oknach
	 * dialogowych wyswietlanych przez klase JOptionPane.
	 * 
	 */
	
	//private UserDialog UI = new ConsoleUserDialog();
	private UserDialog UI = new JOptionPaneUserDialog();
	
	/**
	 * Wiadomosc powitalna wyswietlana na rozpoczeciu aplikacji.
	 */
	private static final String GREETING_MESSAGE =
			"Program MINISHOP - wersja konsolowa\n" +
			"Autor: Adam Filipowicz\n" +
			"Data: 15 pazdziernika 2016 r.\n";

	/**
	 * Menu sklepu internetowego - wyswietlane tuz po wlaczeniu aplikacji.
	 */
	private static final String MENU_SKLEP = 
			"SKLEP - M E N U   G L O W N E \n" +
			"1 - Wyswietl wszystkie konta \n" +
			"2 - Utworz nowe konto        \n" +
			"3 - Utworz konto sprzedawcy  \n" +
			"4 - Zaloguj sie do konta     \n" + 
			"5 - Zaloguj sie jako sprzedawca\n" +
			"0 - Zakoncz program          \n";		
		
	/**
	 * Menu konta - wyswietlane po zalogowaniu sie do konta.
	 */
	private static final String MENU_KONTO =
			"1 - Wplac na konto             \n" +
			"2 - Wyplac z konta             \n" +
			"3 - Zlec przelew na inne konto \n" + 
			"4 - Zmien haslo                \n" +
			"5 - Zmien nazwisko wlasciciela \n" +
			"6 - Usun konto                 \n" +
			"7 - Przejrzyj dostepne towary  \n" +
			"8 - Kup wybrany towar          \n" +
			"9 - Zmien adres wlasciciela    \n" +
			"10 - Przejrzyj historie zakupow\n" +
			"0 - Wyloguj sie z konta        \n";
		
	/**
	 * Menu sprzedawcy - wyswietlane po zalogowaniu sie do konta sprzedawcy
	 */
	private static final String MENU_SPRZEDAWCA =
			"1 - Wyplac z konta             \n" +
			"2 - Zlec przelew na inne konto \n" +
			"3 - Zmien haslo                \n" +
			"4 - Zmien nazwisko wlasciciela \n" +
			"5 - Dodaj towar                \n" +
			"6 - Usun towar                 \n" +
			"7 - Przejrzyj dostepne towary  \n" +
			"8 - Dodaj ilosc towaru         \n" +
			"9 - Zmien cene towaru          \n" +
			"10 - Sprawdz utarg             \n" +
			"11 - Wyswietl liste klientow   \n" +
			"12 - Zmien adres wlasciciela   \n" +
			"13 - Utworz fakture dla wybranego klienta\n"+
			"0 - Wyloguj sie z konta        \n";
	
	/**
	 * Nazwa pliku do zapisu i wczytywania danych.
	 */
	private static final String DATA_FILE_NAME = "MINISHOP.BIN";

	/**
	 * Stworzenie obiektu klasy MiniSklep.
	 */
	private MiniSklep sklep = new MiniSklep();
	
	/**
	 * Aplikacja MiniShopApplication pokazuje glowne menu, wczytuje dane z pliku oraz
	 * pozwala na utworzenie konta, konta sprzedawcy, zalogowanie sie na konto oraz konto sprzedawcy,
	 * wypisanie wszystkich kont oraz zakonczenie dzialania aplikacji i zapis danych do pliku.
	 * @throws Exception - wyjatki lapane przy wczytywaniu oraz zapisywaniu pliku oraz w metodach menu.
	 */
	public MiniShopApplication() {
		UI.printMessage(GREETING_MESSAGE);
		try {
			sklep.wczytajZPliku(DATA_FILE_NAME);
			UI.printMessage("Wczytano konta, towary i calkowity utarg z pliku " + DATA_FILE_NAME);
		}
		catch (Exception e) {
			UI.printErrorMessage(e.getMessage());
		}
		
		while (true) {
			UI.clearConsole();

			try {
				switch (UI.enterInt(MENU_SKLEP + "==>> ")) {
				case 1:
					wypiszKonta();
					break;
				case 2:
					utworzKonto();
					break;
				case 3:
					utworzKontoS();
					break;
				case 4:
					zalogujSie();
					break;
				case 5:
					zalogujSieS();
					break;
				case 0:
					try {
						sklep.zapiszDoPliku(DATA_FILE_NAME);
						UI.printMessage("\nKonta, towary i calkowity utarg zostaly zapisane do pliku " + DATA_FILE_NAME);
					} 
					catch (Exception e) {
						UI.printErrorMessage(e.getMessage());
					}

					UI.printInfoMessage("\nProgram zakonczyl dzialanie!");
					System.exit(0);
				}
			} 
			catch (Exception e) {
				UI.printErrorMessage(e.getMessage());
			}
		}
		
	}
	
	/**
	 * Metoda wypisujaca wszystkie konta.
	 */
	public void wypiszKonta(){
		StringBuilder lista = new StringBuilder("\nLISTA KONT:\n");
		lista.append(sklep.listaKont());
		UI.printMessage(lista.toString());
	}
	 
	/**
	 * Metoda pozwalajaca na utworzenie nowego konta o podanej niepustej nazwie, 
	 * oraz hasle pod warunkiem, ze konto sprzedawcy zostalo juz utworzone.
	 * @throws Exception - wyjatki lapane z metody zalozKonto(String).
	 */
	public void utworzKonto(){
		
		/**
		 * Nazwa nowego konta.
		 */
		String nazwa;
		
		/**
		 * Haslo nowego konta.
		 */
		String haslo;
		
		/**
		 * Obiekt naszego tworzonego konta.
		 */
		Konto konto;
		
		UI.printMessage("\nTWORZENIE NOWEGO KONTA\n");
		while(true) {
			if(!sklep.sprawdzCzyIstniejeSprzedawca()){
				UI.printErrorMessage("Stworz najpierw konto sprzedawcy");
				return;
			}
			nazwa = UI.enterString("Wybierz nazwe konta:");
			if (nazwa.equals("")) return;
			if (sklep.znajdzKonto(nazwa)!=null) {
				UI.printErrorMessage("Konto juz istnieje");
				continue;
			}
			
			haslo = UI.enterString("Podaj haslo:");	
			try {
				konto = sklep.zalozKonto(nazwa);
				konto.setHaslo("", haslo);
			} 
			catch (Exception e) {
				UI.printErrorMessage(e.getMessage());
				continue;
			}
			UI.printMessage("Konto zostalo utworzone");
			break;
		}
	}
	
	/**
	 * Metoda pozwalajaca na utworzenie nowego konta sprzedawcy o podanej niepustej nazwie, 
	 * oraz hasle pod warunkiem, ze konto sprzedawcy nie zostalo jeszcze utworzone.
	 * @throws Exception - wyjatki lapane z metody zalozKonto(String).
	 */
	public void utworzKontoS(){
		/**
		 * Nazwa nowego konta.
		 */
		String nazwa;
		
		/**
		 * Haslo nowego konta.
		 */
		String haslo;
		
		/**
		 * Obiekt naszego tworzonego konta.
		 */
		Konto konto;
		UI.printMessage("\nTWORZENIE NOWEGO KONTA\n");
		while(true) {
			if(sklep.sprawdzCzyIstniejeSprzedawca()){
				UI.printErrorMessage("Konto sprzedawcy zostalo juz stworzone");
				return;
			}
			nazwa = UI.enterString("Wybierz nazwe konta:");
			if (nazwa.equals("")) return;
			if (sklep.znajdzKonto(nazwa)!=null) {
				UI.printErrorMessage("Konto juz istnieje");
				continue;
			}
			haslo = UI.enterString("Podaj haslo:");	
			try {
				konto = sklep.zalozKontoSprzedawcy(nazwa);
				konto.setHaslo("", haslo);
			} 
			catch (Exception e) {
				UI.printErrorMessage(e.getMessage());
				continue;
			}
			UI.printMessage("Konto zostalo utworzone");
			break;
		}
	 }
	
	/**
	 * Metoda pozwalajaca na zalogowanie sie do konta po podaniu nazwy konta i poprawnego hasla.
	 * Po zalogowaniu wyswietla menu konta i pozwala na wplate i wyplate pieniedzy, przelew, zmiane hasla,
	 * zmiane nazwiska, usuniecie konta, sprawdzenie dostepnych towarow, zakup towarow, zmiane adresu,
	 * przejrzenie historii zakupow dla konta oraz wylogowanie sie.
	 * @throws Exception - wyjatki lapane z metod menu.
	 */
	public void zalogujSie(){
		/**
		 * Nazwa nowego konta.
		 */
		String nazwa;
		
		/**
		 * Haslo nowego konta.
		 */
		String haslo;
		
		/**
		 * Obiekt naszego tworzonego konta.
		 */
		Konto konto;

		UI.printMessage("\nLOGOWANIE DO KONTA\n");
		nazwa = UI.enterString("Podaj nazwe konta:");
		haslo = UI.enterString("Podaj haslo:");
		konto = sklep.znajdzKonto(nazwa,haslo);
		if (konto == null) {
			UI.printErrorMessage("Bledne dane");
			return;
		}
		if(sklep.sprawdzCzyKontoSprzedawcy(konto)){
			UI.printErrorMessage("Probujesz zalogowac sie na konto sprzedawcy");
			return;
		}
		while (true) {
			UI.printMessage("\nJESTES ZALOGOWANY DO KONTA ");
			UI.printMessage("Nazwa konta: " + konto.getNazwa());
			UI.printMessage("Nazwisko wlasciciela: " + konto.getNazwisko());
			UI.printMessage("Adres konta: " + konto.getAdres());
			UI.printMessage("Saldo konta: " + konto.getKwota());

			try {

				switch (UI.enterInt(MENU_KONTO + "==>> ")) {
				case 1:
					wplacPieniadze(konto);
					break;
				case 2:
					wyplacPieniadze(konto);
					break;
				case 3:
					przelejPieniadze(konto);
					break;
				case 4:
					haslo = zmienHaslo(konto);
					break;
				case 5:
					zmienNazwisko(konto);
					break;
				case 6:
					if (usunKonto(konto) == false)
						break;
					konto = null;
					return;
				case 7:
					sprawdzTowary();
					break;
				case 8:
					zakupTowarow(konto);
					break;
				case 9:
					zmienAdres(konto);
					break;
				case 10:
					przejrzyjHistorie(konto);
					break;
				case 0:
					konto = null;
					UI.printMessage("Nastapilo wylogowanie z konta");
					return;
				}
			} catch (Exception e) {
				UI.printErrorMessage(e.getMessage());
			}
		}
	}
	
	/**
	 * Metoda pozwalajaca na zalogowanie sie do konta sprzedawcy po podaniu nazwy konta i poprawnego hasla.
	 * Po zalogowaniu wyswietla menu konta i pozwala na wyplate pieniedzy, przelew, zmiane hasla,
	 * zmiane nazwiska, dodanie towaru, wycofanie towaru, sprawdzenie dostepnych towarow, dodanie ilosci towaru,
	 * zmiane ceny towaru, sprawdzenie utargu, wyswietlenie listy klientow, zmiane adresu,
	 * wyswietlenie faktury dla podanego klienta oraz wylogowanie sie.
	 * @throws Exception - wyjatki lapane z metod menu.
	 */
	public void zalogujSieS(){
		/**
		 * Nazwa nowego konta.
		 */
		String nazwa;
		
		/**
		 * Haslo nowego konta.
		 */
		String haslo;
		
		/**
		 * Obiekt naszego tworzonego konta.
		 */
		Konto konto;

		UI.printMessage("\nLOGOWANIE DO KONTA-SPRZEDAWCA\n");
		nazwa = UI.enterString("Podaj nazwe konta:");
		haslo = UI.enterString("Podaj haslo:");
		konto = sklep.znajdzKonto(nazwa,haslo);
		if (konto == null) {
			UI.printErrorMessage("Bledne dane");
			return;
		}
		if(!sklep.sprawdzCzyKontoSprzedawcy(konto)){
			UI.printErrorMessage("Probujesz zalogowac sie na inne konto ni¿ sprzedawcy");
			return;
		}
		while (true) {
			UI.printMessage("\nJESTES ZALOGOWANY DO KONTA ");
			UI.printMessage("Nazwa konta: " + konto.getNazwa());
			UI.printMessage("Nazwisko wlasciciela: " + konto.getNazwisko());
			UI.printMessage("Adres konta: " + konto.getAdres());
			UI.printMessage("Saldo konta: " + konto.getKwota());

			try {

				switch (UI.enterInt(MENU_SPRZEDAWCA + "==>> ")) {
				case 1:
					wyplacPieniadze(konto);
					break;
				case 2:
					przelejPieniadze(konto);
					break;
				case 3:
					haslo = zmienHaslo(konto);
					break;
				case 4:
					zmienNazwisko(konto);
					break;
				case 5:
					dodajTowar();
					break;
				case 6:
					wycofajTowar();
					break;
				case 7:
					sprawdzTowary();
					break;
				case 8:
					dodajIloscTowaru();
					break;
				case 9:
					zmienCene();
					break;
				case 10:
					sprawdzUtarg();
					break;
				case 11:
					wyswietlKlientow();
					break;
				case 12:
					zmienAdres(konto);
					break;
				case 13:
					generujFakture();
					break;
				case 0:
					konto = null;
					UI.printMessage("Nastapilo wylogowanie z konta");
					return;
				}
			} catch (Exception e) {
				UI.printErrorMessage(e.getMessage());
			}
		}
	}
	
	/**
	 * Metoda pozwalajaca na wplate pieniedzy na konto.
	 * @param konto - podane konto
	 * @throws Exception - wyjatek lapany z metody Wplac
	 */
	public void wplacPieniadze(Konto konto)throws Exception {
		double kwota;
		UI.printMessage("\nWPLATA NA KONTO");
		kwota = UI.enterDouble("Podaj kwote: ");
		konto.Wplac(kwota);
	}
	
	/**
	 * Metoda pozwalajaca na wyplate pieniedzy z konta.
	 * @param konto - podane konto
	 * @throws Exception - wyjatek lapany z metody Wyplac
	 */
	public void wyplacPieniadze(Konto konto) throws Exception {
		String haslo;
		UI.printMessage("\nWYPLATA Z KONTA");
		haslo = UI.enterString("Podaj haslo: ");
		if(konto.sprawdzHaslo(haslo)){
			double kwota;
			kwota = UI.enterDouble("Podaj kwote: ");
			konto.Wyplac(kwota, haslo);
			UI.printMessage("\nWyplacono: "+kwota);
		}
		else
			UI.printErrorMessage("Podano bledne haslo!");
	}
	
	/**
	 * Metoda pozwalajaca na przelew pieniedzy na konto.
	 * @param konto - podane konto
	 * @throws Exception - wyjatek lapany z metody Przelej
	 */
	public void przelejPieniadze(Konto konto) throws Exception {
		String nazwaInnegoKonta;
		Konto inneKonto;
		double kwota;
		String haslo;
		UI.printMessage("\nPRZELEW Z KONTA NA KONTO");
		haslo = UI.enterString("Podaj haslo: ");
		if(konto.sprawdzHaslo(haslo)){
			nazwaInnegoKonta = UI.enterString("Podaj nazwe konta docelowego: ");
			inneKonto = sklep.znajdzKonto(nazwaInnegoKonta);
			if (nazwaInnegoKonta == null) {
				UI.printErrorMessage("Nieznane konto docelowe");
				return;
			}
			kwota = UI.enterDouble("Podaj kwote: ");
			konto.Przelej(kwota, haslo, inneKonto);
		}
		else
			UI.printErrorMessage("Podano bledne haslo!");
	}
	
	/**
	 * Metoda pozwalajaca na zmiane hasla podanego konta
	 * @param konto - podane konto
	 * @throws Exception - wyjatek lapany z metody sprawdzHaslo
	 */
	public  String zmienHaslo(Konto konto) throws Exception {
		String noweHaslo;
		String stareHaslo;
		
		UI.printMessage("\nZMIANA HASLA DO KONTA");
		stareHaslo = UI.enterString("Podaj stare haslo: ");
		noweHaslo = UI.enterString("Podaj nowe haslo: ");
		if(konto.sprawdzHaslo(stareHaslo)){
			UI.printMessage("\nHaslo zostalo zmienione");
			konto.setHaslo(stareHaslo, noweHaslo);
			return noweHaslo;
		}
		UI.printErrorMessage("Podano bledne stare haslo!");
		return stareHaslo;
	}
	
	/**
	 * Metoda pozwalajaca na zmiane nazwiska podanego konta
	 * @param konto - podane konto
	 */
	public  void zmienNazwisko(Konto konto){
		String noweNazwisko;
		
		UI.printMessage("\nZMIANA NAZWISKA WLASCICIELA KONTA");
		noweNazwisko = UI.enterString("Podaj nazwisko: ");
		konto.setNazwisko(noweNazwisko);
	}
	
	/**
	 * Metoda pozwalajaca na usuniecie podanego konta
	 * @param konto - podane konto
	 * @throws Exception - wyjatek lapany z metody sprawdzHaslo
	 */
	public boolean usunKonto(Konto konto) throws Exception {
		String odpowiedz;
		String haslo;
		UI.printMessage("\nUSUWANIE KONTA");
		haslo = UI.enterString("Podaj haslo: ");
		if(konto.sprawdzHaslo(haslo)){
			odpowiedz = UI.enterString("Czy na pewno usunac to konto? (TAK/NIE)");
			if (!odpowiedz.equals("TAK")) {
				UI.printErrorMessage("\nKonto nie zostalo usuniete");
				return false;
			}
			sklep.usunKonto(konto);
			UI.printInfoMessage("\nKonto zostalo usuniete");
			return true;
		}
		UI.printErrorMessage("Podano bledne haslo!");
		return false;
	}
	
	/**
	 * Metoda pozwalajaca na sprawdzenie dostepnych w sklepie
	 */
	public void sprawdzTowary() {
		UI.printMessage("\nSPRAWDZANIE STANU TOWAROW");
		UI.printMessage(sklep.wypiszListeTowarow());
	}
	
	/**
	 * Metoda pozwalajaca na zakup towarow po podaniu hasla, az do wpisania "KONIEC".
	 * @param konto - podane konto
	 * @throws Exception - wyjatek lapany z roznych metod uzywanych przy zakupie towarow
	 */
	public void zakupTowarow(Konto konto) throws Exception {
		
		/**
		 * Nazwa towaru do zakupu.
		 */
		String nazwaTowaru;
		
		/**
		 * Towar ktory kupujemy.
		 */
		Towar towar;
		
		/**
		 * Liczba sztuk towaru ktora chcemy kupic.
		 */
		int liczbaSztuk;
		
		/**
		 * Haslo podane do zakupu towarow
		 */
		String haslo;
		UI.printMessage("\nZAKUP TOWAROW");
		haslo = UI.enterString("Podaj haslo: ");
		if(konto.sprawdzHaslo(haslo)){
			while(true){
				nazwaTowaru=UI.enterString("\nPodaj nazwe towaru ktory chcesz zakupic lub 'KONIEC' zeby zakonczyc zakupy: ");
				towar=sklep.znajdzTowar(nazwaTowaru);
				if(nazwaTowaru.equals("KONIEC"))
					break;
				else if(towar==null){
					UI.printErrorMessage("Podany towar nie istnieje");
					continue;
				}
				UI.printMessage("\nW sklepie zostalo "+towar.getIlosc()+" sztuk "+towar.getNazwa());
				try{
					while(true){
						liczbaSztuk=UI.enterInt("Podaj ilosc towaru do zakupu (0 - anuluj zakup): ");
						if(liczbaSztuk>towar.getIlosc()){
							UI.printErrorMessage("W sklepie nie ma wystarczajacej ilosci towaru");
							continue;
						}
						if(liczbaSztuk==0){
							UI.printMessage("\nAnulowano Zakup");
							break;
						}
						if(konto.getKwota()<liczbaSztuk*towar.getCena()){
							UI.printErrorMessage("\nNie masz wystarczajacych srodkow na koncie");
							continue;
						}
						konto.Przelej(liczbaSztuk*towar.getCena(), haslo, sklep.getKontoSprzedawcy());
						towar.setKlient(konto.getNazwa());
						sklep.dodajDoHistoriiZakupow(towar.getNazwa(),towar.getCena(),liczbaSztuk, towar.getCena()*liczbaSztuk, towar.getKlient());
						towar.minusIlosc(liczbaSztuk);
						sklep.aktualizujUtarg(liczbaSztuk*towar.getCena());
						UI.printMessage("\nZakupiles "+liczbaSztuk+" sztuk "+towar.getNazwa());
						break;
					}
				}
				catch (Exception e) {
					UI.printErrorMessage(e.getMessage());
				}
			}
		}
		else
			UI.printErrorMessage("Podano bledne haslo!");
	}
	
	/**
	 * Metoda pozwalajaca na dodanie towaru
	 * @throws Exception - wyjatek lapany z metody dodajTowar
	 */
	public void dodajTowar() throws Exception {
		String nazwaTowaru;
		UI.printMessage("\nDODAWANIE TOWARU");
		nazwaTowaru=UI.enterString("Podaj nazwe nowego towaru: ");
		sklep.dodajTowar(nazwaTowaru);
	}
	
	/**
	 * Metoda pozwalajaca na wycofanie towaru
	 * @throws Exception - wyjatek lapany z metody wycofajTowar
	 */
	public void wycofajTowar() throws Exception {
		String nazwaTowaru;
		UI.printMessage("\nWYCOFANIE TOWARU");
		nazwaTowaru=UI.enterString("Podaj nazwe towaru do wycofania: ");
		sklep.wycofajTowar(nazwaTowaru);
	}
	
	/**
	 * Metoda pozwalajaca na dodanie ilosci towaru
	 * @throws Exception - wyjatek lapany z metody addIlosc(Int)
	 */
	public void dodajIloscTowaru() throws Exception{
		String nazwaTowaru;
		int iloscTowaru;
		Towar towar=null;
		
		UI.printMessage("\nDODAWANIE ILOSCI TOWARU");
		while(true){
			nazwaTowaru=UI.enterString("Podaj nazwe towaru lub KONIEC zeby anulowac: ");
			if(nazwaTowaru.equals("KONIEC"))
				break;
			towar=sklep.znajdzTowar(nazwaTowaru);
			if(towar==null){
				UI.printErrorMessage("Podany towar nie istnieje");
				continue;
			}
			try{
				iloscTowaru=UI.enterInt("Podaj ilosc towaru do dodania: ");
				towar.addIlosc(iloscTowaru);
			}
			catch(Exception e){
				UI.printErrorMessage(e.getMessage());
			}
			break;
		}
	}
	
	/**
	 * Metoda pozwalajaca na zmiane ceny towaru
	 * @throws Exception - wyjatek lapany z metody nowaCena(Double)
	 */
	public void zmienCene() throws Exception{
		String nazwaTowaru;
		double nowaCena;
		Towar towar;
		
		UI.printMessage("\nZMIANA CENY TOWARU");
		while(true){
			nazwaTowaru=UI.enterString("Podaj nazwe towaru lub KONIEC zeby anulowac: ");
			if(nazwaTowaru.equals("KONIEC"))
				break;
			towar=sklep.znajdzTowar(nazwaTowaru);
			if(towar==null){
				UI.printErrorMessage("Podany towar nie istnieje");
				continue;
			}
			try{
				nowaCena=UI.enterDouble("Podaj nowa cene towaru: ");
				towar.setCena(nowaCena);
			}
			catch(Exception e){
				UI.printErrorMessage(e.getMessage());
			}
			break;
		}
	}
	
	/**
	 * Metoda pozwalajaca na sprawdzenie utargu
	 * @throws Exception - wyjatek lapany z metody sprawdzUtarg
	 */
	public void sprawdzUtarg() throws Exception{
		UI.printMessage("\nSPRAWDZANIE UTARGU");
		UI.printMessage("\nUtarg: "+sklep.sprawdzUtarg());
	}

	/**
	 * Metoda pozwalajaca na wyswietlenie klientow
	 * @throws Exception - wyjatek lapany z metody wyswietlKlientow
	 */
	public void wyswietlKlientow() throws Exception{
		UI.printMessage("\nWYSWIETLANIE KLIENTOW");
		wypiszKonta();
	}
	
	/**
	 * Metoda pozwalajaca na zmiane adresu.
	 * @param konto - podane konto
	 */
	public void zmienAdres(Konto konto){
		String nowyAdres;
		
		UI.printMessage("\nZMIANA ADRESU WLASCICIELA KONTA");
		nowyAdres = UI.enterString("Podaj adres: ");
		konto.setAdres(nowyAdres);
	}
	
	/**
	 * Metoda pozwalajaca na wyswietlenie historii zakupow
	 * @param konto - podane konto
	 */
	public void przejrzyjHistorie(Konto konto){
		UI.printMessage("\nHISTORIA ZAKUPOW");
		UI.printMessage(sklep.wyswietlHistorieZakupow(konto));
	}
	
	/**
	 * Metoda pozwalajaca na wyswietlenie faktury dla podanego klienta
	 * @param konto - podane konto
	 */
	public void generujFakture(){
		UI.printMessage("\nGENERUJ FAKTURE");
		String nazwa=UI.enterString("\nPodaj nazwe konta klienta");
		if (nazwa.equals("")) return;
		if (sklep.znajdzKonto(nazwa)==null) {
			UI.printErrorMessage("Podano bledna nazwe konta");
			return;
		}
		Konto klient=sklep.znajdzKonto(nazwa);
		String faktura="Firma: "+sklep.getKontoSprzedawcy().getNazwa();
		faktura+="\nAdres sklepu: "+sklep.getKontoSprzedawcy().getAdres();
		faktura+="\n\n\n";
		faktura+="Nazwisko klienta: "+klient.getNazwisko();
		faktura+="\nAdres klienta: "+klient.getAdres();
		faktura+="\n\nTowar: "+"\n"+sklep.wyswietlHistorieZakupow(klient);
		faktura+="\nLaczna kwota zaplaty: "+sklep.wyswietlZaplate(klient);
		UI.printMessage(faktura);
	}
	
}
