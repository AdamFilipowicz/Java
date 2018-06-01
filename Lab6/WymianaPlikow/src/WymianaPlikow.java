public class WymianaPlikow {
    
    private WezelCentralny wezelCentr;
    
    public static void main(String[] args) {
        new WymianaPlikow();
    }
    
    public WymianaPlikow(){
        try {
            wezelCentr = new WezelCentralny(true);
        } catch (Exception e) {
            System.err.println("BLAD - Nieudane utworzenie serwera lub klienta" + e.toString());
            e.printStackTrace();
        }
    }
    
}
