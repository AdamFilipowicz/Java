/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Student221713
 */
public class Ladowacz {
    
    private UserDialog UI = new ConsoleUserDialog();
    
    private static final String MENU = 
                    "1 - Wywolaj metode hello world \n" +
                    "2 - Wywolaj metode is prime        \n" +
                    "3 - Wywolaj metode for each element  \n" +
                    "0 - Zakoncz program          \n";
    
    static{
       System.loadLibrary( "biblio" );
    }
    public native void helloWorld();
    
    public native boolean isPrime( int num );
    
    public native float[] forEachElement( float [] array , float val , String op );
    
    public void konsola(){
        while (true) {
            UI.clearConsole();
            switch (UI.enterInt(MENU + "==>> ")) {
            case 1:
                new Ladowacz().helloWorld();
                break;
            case 2:
                int i = UI.enterInt("Podaj liczbe do sprawdzenia: ");
                boolean pierwsza = new Ladowacz().isPrime(i);
                if(pierwsza)
                    UI.printMessage("Podana liczba jest pierwsza");
                else
                    UI.printMessage("Podana liczba nie jest pierwsza");
                break;
            case 3:
                int ilosc = UI.enterInt("Podaj ilosc podawanych elementow tablicy: ");
                float[] tab = new float[ilosc];
                for(int j=0;j<ilosc;j++)
                    tab[j] = UI.enterFloat("Podaj kolejna liczbe: ");
                String op = UI.enterString("Podaj operacje na elementach tablicy: add, subtract, multiply, divide: ");
                int wartosc = UI.enterInt("Podaj wartosc do operacji (dodawana, odejmowana, mnozona lub dzielona): ");
                if(op.equals("add") || op.equals("subtract") || op.equals("multiply") || op.equals("divide")){
                    float[] tab2 = forEachElement(tab, wartosc, op);
                    StringBuilder sb = new StringBuilder();
                    sb.append("Tablica po operacji: ");
                    for(int k=0;k<ilosc;k++){
                        sb.append(tab2[k] + " ");
                    }
                    UI.printMessage(sb.toString());
                }
                else
                    UI.printMessage("Podano nieprawidlowa nazwe operacji");
                break;
            case 0:
                System.exit(0);
            }
        }
    }
    
}
