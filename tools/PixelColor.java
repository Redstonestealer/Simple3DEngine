package tools;

import java.awt.*;

public class PixelColor {
    public double red;
    public double green;
    public double blue;
    double reflectiveness;
    double roughness;
    double light;

    public PixelColor(double red, double green, double blue, double roughness, double light, double reflectiveness) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.reflectiveness = reflectiveness;
        this.roughness = roughness;
        this.light = light;
    }
    public void applyLightLevel(){
        red*=light;
        green*=light;
        blue*=light;
    }

    public double getRed() {
        return red;
    }

    public double getGreen() {
        return green;
    }

    public double getBlue() {
        return blue;
    }

    public double getReflectiveness() {
        return reflectiveness;
    }
    public double getRoughness(){
        return roughness;
    }
    public double getLight(){
        return light;
    }
    public Color to255BaseColor(){
        return new Color(
                ((Double)(red*255)).intValue(),
                ((Double)(green*255)).intValue(),
                ((Double)(blue*255)).intValue()
        );
    }
}
