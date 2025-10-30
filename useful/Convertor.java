package useful;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Convertor {
    public static Integer toInt(String in){
        return Integer.parseInt(in);
    }
    public static Integer toInt(Double in){
        return in.intValue();
    }
    public static Integer toInt(Float in){
        return in.intValue();
    }
    public static String toStr(Integer in){
        return in.toString();
    }
    public static String toStr(Double in){
        return in.toString();
    }
    public static String toStr(Float in){
        return in.toString();
    }
    public static String toStr(Boolean in){return in.toString();}
    public static String toStr(Character in){return in.toString();}
    public static Double toDouble(String in){
        return Double.parseDouble(in);
    }
    public static Double toDouble(Integer in){
        return in*1.0;
    }
    public static Dimension toDim(Rectangle2D in){
        return new Dimension(Convertor.toInt(in.getWidth()), Convertor.toInt(in.getHeight()));
    }
    public static Dimension toDim(Point in){
        return new Dimension(in.x, in.y);
    }
    public static String multString(String string, Integer times){
        String out ="";
        for (int i = 0; i<times; i++){
            out+=string;
        }
        return out;
    }
    public static Dimension addDimension(Dimension a, Dimension b){
        return new Dimension(a.width+b.width, a.height+b.height);
    }

}