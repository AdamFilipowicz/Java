public class Kolekcja {
    private int ziarno;
    private double[] kolekcja;
    
    public Kolekcja(int ziarno){
        kolekcja = new double[(125000 + ziarno*1000)];
        for(int i=0;i<kolekcja.length;i++)
            kolekcja[i]=i%ziarno;
    }
    
    public double[] getKolekcja(){
        return kolekcja;
    }
}
