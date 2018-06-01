
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "logins")
@XmlAccessorType (XmlAccessType.FIELD)
public class Logins {
    
    @XmlElement(name = "login")
    private ArrayList<Login> loginList;
 
    public ArrayList<Login> getLoginList() {
        return loginList;
    }
    
    public void setLoginList(ArrayList<Login> loginList) {
        this.loginList = loginList;
    }
}
