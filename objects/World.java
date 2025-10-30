package objects;

import java.util.ArrayList;

public class World {

    public ArrayList<Triangle> triangles = new ArrayList<>();

    public World(){

    }
    public void addTriangle(Triangle t){
        this.triangles.add(t);
    }
}
