
import java.io.FileNotFoundException;
import java.io.IOException;

public interface Algorytmy{
    
	public void algorithm();
        
        public void displaySolution();
        
        public void loadFromFile(String fileName) throws FileNotFoundException, IOException;
        
        public void setVars(int n);
        
        public void algorithmInfo();
        
        public int[] getWynik();
        
        public int[] getMacierz();
        
        public int getW();
	
}