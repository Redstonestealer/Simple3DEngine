package useful;

import java.awt.*;
import java.util.HashMap;

public class FontMGR {
    private HashMap<String,Font> fonts = new HashMap<>();
    private int size;
    private static FontMGR instance;
    public static FontMGR getInstance(){
        return instance;
    }

    public FontMGR(int size){
        this.size=size; instance=this;
    }
    public Font get(String name){
        return get(name, 1.0);
    }

    public Font get(String name, Double size){
        Font f = fonts.get(name);
        if (f == null){
            Double s = size * this.size;
            fonts.put(name, new Font(name, 0, s.intValue()));
            return fonts.get(name);
        }
        return f;
    }
}
