import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Adam Filipowicz
 */
public class ZestawKartOdpowiedzi {
    private int liczbaPytan;
    private int liczbaOpcji;
    
    private int liczbaKart;
    
    private ArrayList<KartaOdpowiedzi> zestawKart;
    
    public ZestawKartOdpowiedzi(int liczbaPytan, int liczbaOpcji, int[] odpowiedzi){
        this.liczbaPytan=liczbaPytan;
        this.liczbaOpcji=liczbaOpcji;
        
        liczbaKart = 0;
        
        zestawKart=new ArrayList<KartaOdpowiedzi>();
        
        KartaOdpowiedzi kluczOdpowiedzi = new KartaOdpowiedzi(liczbaPytan, liczbaOpcji);
        kluczOdpowiedzi.setOdpowiedzi(odpowiedzi);
        zestawKart.add(kluczOdpowiedzi);
    }

    public List getZestawKart() {
        return zestawKart;
    }

    public void setZestawKart(ArrayList zestawKart) {
        this.zestawKart = zestawKart;
    }
    
    public int getLiczbaPytan() {
        return liczbaPytan;
    }

    public void setLiczbaPytan(int liczbaPytan) {
        this.liczbaPytan = liczbaPytan;
    }

    public int getLiczbaOpcji() {
        return liczbaOpcji;
    }

    public void setLiczbaOpcji(int liczbaOpcji) {
        this.liczbaOpcji = liczbaOpcji;
    }
    
    public int getLiczbaKart() {
        return liczbaKart;
    }

    public void setLiczbaKart(int liczbaKart) {
        this.liczbaKart = liczbaKart;
    }
    
    public void dodajKarte(KartaOdpowiedzi karta){
        zestawKart.add(karta);
        liczbaKart++;
    }
    
}
