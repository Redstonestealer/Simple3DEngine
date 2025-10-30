package tools.materials;

import tools.PixelColor;
import tools.Tool;

import java.awt.*;

public class MonoMaterial implements Material {
    public double R=1.0;
    public double G=1.0;
    public double B=1.0;
    public double roughness = 0.0;
    public double light=0.0;
    public double reflectiveness = 0.0;

    public MonoMaterial(double r, double g, double b, double roughness, double light, double reflectiveness) {
        R = r;
        G = g;
        B = b;
        this.roughness = roughness;
        this.light=light;
        this.reflectiveness=reflectiveness;
    }


    public PixelColor getMaterialAtPoint(double x, double z){
        return new PixelColor(
                this.R,
                this.G,
                this.B,
                this.roughness,
                this.light,
                this.reflectiveness
        );
    }
}
