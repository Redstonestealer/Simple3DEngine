package math.objects;

import errors.VectorsCantBeIdentical;

public class Plane {
    Point3D start;
    Vector3D a;
    Vector3D b;

    public Plane(Point3D start, Vector3D a, Vector3D b) throws VectorsCantBeIdentical {
        if (a.isMultiple(b)!=null){
            throw new VectorsCantBeIdentical();
        }
        this.start=start; this.a=a; this.b=b;
    }

    public Point3D intersectWithGraph(Graph3D g){
        return null;
    }


}

