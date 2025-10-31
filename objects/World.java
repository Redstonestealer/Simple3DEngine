package objects;

import tools.PixelColor;
import tools.materials.Material;

import java.util.ArrayList;

public class World {

    public ArrayList<Triangle> triangles = new ArrayList<>();
    private PixelColor defaultLight;

    public World(){

    }
    public void setDefaultLight(PixelColor defaultLight){
        this.defaultLight=defaultLight;
    }
    public void setDefaultLight(Material m){
        this.defaultLight=m.getMaterialAtPoint(0.0,0.0);
    }
    public PixelColor getDefaultLight(){
        return this.defaultLight;
    }
    public void addTriangle(Triangle t){
        this.triangles.add(t);
    }
}
