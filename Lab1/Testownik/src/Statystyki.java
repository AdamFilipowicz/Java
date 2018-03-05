
import java.util.List;

/**
 *
 * @author Adam Filipowicz
 */
public class Statystyki {
    private int liczbaKart;
    
    private int[] zdobytePunkty;
    private ZestawKartOdpowiedzi zestawAnalizowany;
    private List<KartaOdpowiedzi> zestawKart;
    
    private KartaOdpowiedzi klucz;
    
    
    public Statystyki(ZestawKartOdpowiedzi zestawAnalizowany){
        KartaOdpowiedzi aktualnaKarta;
        liczbaKart=zestawAnalizowany.getLiczbaKart()-1;
        this.zestawAnalizowany = zestawAnalizowany;
        zestawKart = zestawAnalizowany.getZestawKart();
        klucz = zestawKart.get(0);
        zdobytePunkty = new int[liczbaKart+1];
        int[] odpowiedziKlucza;
        int[] odpowiedziAktualnejKarty;
        if(klucz!=null){
            odpowiedziKlucza=klucz.getOdpowiedzi();
            for(int i=1;i<zestawAnalizowany.getLiczbaKart();i++){
                aktualnaKarta=zestawKart.get(i);
                odpowiedziAktualnejKarty=aktualnaKarta.getOdpowiedzi();
                for(int j=0;j<zestawAnalizowany.getLiczbaPytan();j++){
                    if(odpowiedziKlucza[j]==odpowiedziAktualnejKarty[j])
                        zdobytePunkty[i-1]++;
                }
            }
        }
    }
    
    public String Histogram(){
        int[] histogram = new int[zestawAnalizowany.getLiczbaPytan()+1];
        for(int i=0;i<liczbaKart+1;i++)
            histogram[zdobytePunkty[i]]++;
        StringBuilder sb = new StringBuilder();
        sb.append("Histogram zdobytych punktów: \n");
        for(int i=0;i<zestawAnalizowany.getLiczbaPytan()+1;i++){
            sb.append(i);
            for(int j=0;j<histogram[i];j++){
                sb.append('*');
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    
    public String LiczbaNiezaliczonych(){
        int niezaliczone = 0;
        for(int i=0;i<liczbaKart+1;i++)
            if(zdobytePunkty[i]<zestawAnalizowany.getLiczbaPytan()/2)
                niezaliczone++;
        return "Liczba niezaliczonych prac: "+niezaliczone;
    }
    
    public String SredniaPunktow(){
        int suma = 0;
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<liczbaKart+1;i++)
            suma+=zdobytePunkty[i];
        return "Srednia punktow zdobytych przez studentow: "+(float)suma/(float)(liczbaKart+1);
    }
}
