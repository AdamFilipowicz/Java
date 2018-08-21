import java.io.*;
import java.nio.file.*;

public class MapItem {
    private String filepath;
    private String text;
    
    public MapItem(String filepath) {
        this.filepath = "src/Files/"+filepath;
        setText();
    }

    public String getFilepath() {
        return filepath;
    }

    public String getText() {
        return text;
    }
    
    private void setText(){
        try {
            text = new String(Files.readAllBytes(Paths.get(filepath)));
        } catch (IOException ex) {}
    }
}
