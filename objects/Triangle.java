package objects;

import math.objects.CoordinateSystem;
import math.objects.Point3D;
import math.objects.Vector3D;
import tools.materials.MonoMaterial;
import tools.PixelColor;

public class Triangle {
    Point3D start;
    Vector3D c;
    Vector3D b;
    /*
    Coordinate system relative to triangle. start is zero in system
    b is x
    c is z
    y is normal
     */
    CoordinateSystem system;
    MonoMaterial material;

    /**
     * A triangle
     * Make sure that B is on the right of C from the direction you're looking at
     * @param A Point A
     * @param B Point B
     * @param C Point C
     */
    public Triangle(Point3D A, Point3D B, Point3D C, MonoMaterial m) {
        this.start=A;
        Vector3D v = A.getVector().invert();
        this.b = B.getVector().add(v);
        this.c = C.getVector().add(v);
        this.system = new CoordinateSystem(start,
                b,
                Vector3D.crossProduct(c, b),
                c);
        this.material = m;
    }

    public MonoMaterial getMaterial() {
        return material;
    }

    public Point3D getA(){
        return start;
    }
    public Point3D getB(){
        return start.getVector().add(b).pointsTo();
    }
    public Point3D getC(){
        return start.getVector().add(c).pointsTo();
    }
    public Vector3D getNormalVector(){
        return system.getUnitVectorY();
    }

    /**
     * Transforms a worldspace point into localscace of the triangle. The X-Z Plane is on the level of the triangle
     * @param p The world space point
     * @param respectZero In case of Vectors, we only care about the direction and not the relative location of zero
     * @return The local space point
     */
    public Point3D transformRelativeToTriangle(Point3D p, boolean respectZero){
        return system.transformIntoSystem(p, respectZero);
    }

    /**
     * Transforms a localspace point relative to the triangle into a world space point
     * @param p The local space point
     * @param respectZero In case of Vectors, we only care about the direction and not the relative location of zero
     * @return The world space point
     */
    public Point3D transformRelativeToWorldSpace(Point3D p, boolean respectZero){
        return system.toWorldSpace(p, respectZero);
    }

    public PixelColor getPixelColorAt(double x, double z){
        return material.getMaterialAtPoint(x, z);
    }
}
