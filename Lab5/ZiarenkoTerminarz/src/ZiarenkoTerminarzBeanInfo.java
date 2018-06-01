import java.awt.Image;
import java.beans.*;
import java.lang.reflect.Method;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ZiarenkoTerminarzBeanInfo extends SimpleBeanInfo {
    
    private final static Class ziarenkoTerminarzClass = ZiarenkoTerminarz.class;
    private final static Class customizerClass = ZiarenkoTerminarzCustomizer.class;
    
    public ZiarenkoTerminarzBeanInfo(){}
    
    @Override
    public BeanDescriptor getBeanDescriptor(){
        return new BeanDescriptor( ziarenkoTerminarzClass, customizerClass);
    }
    
    @Override
    public MethodDescriptor[] getMethodDescriptors(){
        Method wczytajMethod, zapiszMethod, load;
        Class args[] = { };
        Class actionEventArgs[] = { java.awt.event.ActionEvent.class };
        Class propertyChangeEventArgs[] = { PropertyChangeEvent.class };

        try {
            wczytajMethod = ziarenkoTerminarzClass.getMethod("wczytajZPliku", args);
            zapiszMethod = ziarenkoTerminarzClass.getMethod("zapiszDoPliku", args);
            load = ziarenkoTerminarzClass.getMethod("load", actionEventArgs);
        } catch (Exception ex) {
            throw new Error("Missing method: " + ex); 
        }
        MethodDescriptor result[] = {
            new MethodDescriptor(wczytajMethod),
            new MethodDescriptor(zapiszMethod),
            new MethodDescriptor(load)
        }; 
        return result;
    }
    
    @Override
    public PropertyDescriptor[] getPropertyDescriptors(){
        try { 
            PropertyDescriptor tytul = new PropertyDescriptor("tytulPanelu", ziarenkoTerminarzClass); 
            PropertyDescriptor pole = new PropertyDescriptor("rozmiarPola", ziarenkoTerminarzClass); 
            PropertyDescriptor ramy = new PropertyDescriptor("ramyCzasowe", ziarenkoTerminarzClass); 
            PropertyDescriptor rv[] = {tytul, pole, ramy}; 
            return rv; 
        } 
        catch (IntrospectionException e) { 
            throw new Error(e.toString()); 
        }
    }
    
    @Override
    public Image getIcon(int iconType) {
        String name = "";
        if (iconType == BeanInfo.ICON_COLOR_16x16)
            name = "COLOR_16x16";
        else if (iconType == BeanInfo.ICON_COLOR_32x32)
            name = "COLOR_32x32";
        else if (iconType == BeanInfo.ICON_MONO_16x16)
            name = "MONO_16x16";
        else if (iconType == BeanInfo.ICON_MONO_32x32)
            name = "MONO_32x32";
        else
            return null;
        Image im = null;
        try {
            ImageIcon ii = new ImageIcon(ZiarenkoTerminarzBeanInfo.class.getClassLoader().getResource("/ZiarenkoTerminarz_" + name + ".gif"));
            im = ii.getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return im;
    }
}
