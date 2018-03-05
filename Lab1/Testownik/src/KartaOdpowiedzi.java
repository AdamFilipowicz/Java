/**
 *
 * @author Adam Filipowicz
 */
public class KartaOdpowiedzi {
    private int liczbaPytan;
    private int liczbaOpcji;
    
    private int[] odpowiedzi;
    
    public KartaOdpowiedzi(int liczbaPytan,int liczbaOpcji){
        this.liczbaPytan=liczbaPytan;
        this.liczbaOpcji=liczbaOpcji;
        
        
        odpowiedzi=new int[liczbaPytan];
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

    public int[] getOdpowiedzi() {
        return odpowiedzi;
    }

    public void setOdpowiedzi(int[] odpowiedzi) {
        for(int i=0;i<liczbaPytan;i++)
            if(odpowiedzi[i]>liczbaOpcji)
                odpowiedzi[i]=liczbaOpcji;
        this.odpowiedzi = odpowiedzi;
    }
    
}
