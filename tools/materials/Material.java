package tools.materials;

import tools.PixelColor;

public interface Material {
    double R=0.0;
    double G=0.0;
    double B=0.0;
    double roughness=0.0;
    double light=0.0;
    double reflectiveness=0.0;
    PixelColor getMaterialAtPoint(double x, double z);
}
