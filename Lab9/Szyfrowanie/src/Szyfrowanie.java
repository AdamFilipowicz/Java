import java.security.SecureClassLoader;

public class Szyfrowanie {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException{
        SzyfrowanieApp ap = new SzyfrowanieApp();
        ap.setResizable(false);
        ap.setVisible(true);
    }
    
    public Object load(String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
        Class classL = Class.forName(className);
        return classL.newInstance();
    }
}
