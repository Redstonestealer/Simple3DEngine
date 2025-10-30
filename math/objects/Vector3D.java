package math.objects;

public class Vector3D {
    public double x=.0;
    public double y=.0;
    public double z=.0;

    public Vector3D(double x, double y, double z){
        this.x=x;
        this.y=y;
        this.z=z;
    }
    public Point3D pointsTo(){
        return new Point3D(x,y,z);
    }
    public static Vector3D getZeroVector(){
        return new Vector3D(0.0,0.0,0.0);
    }
    public static Vector3D getXUnitVector(){
        return new Vector3D(1.0, 0.0, 0.0);
    }
    public static Vector3D getYUnitVector(){
        return new Vector3D(0.0, 1.0, 0.0);
    }
    public static Vector3D getZUnitVector(){
        return new Vector3D(0.0, 0.0, 1.0);
    }
    public static Vector3D add(Vector3D a, Vector3D b){
        return new Vector3D(a.x+b.x, a.y+b.y, a.z+b.z);
    }
    public static Vector3D add(Vector3D... a){
        Vector3D v=Vector3D.getZeroVector();
        for (Vector3D b: a){
            v= v.add(b);
        }
        return v;
    }

    /**
     * Returns a vector that describes the path from this vector to a vector v
     * Mathematical writing: AB = B-A
     * @param v The vector
     * @return The new vector
     */
    public Vector3D fromThisToV(Vector3D v){
        return new Vector3D(
                v.x-x,
                v.y-y,
                v.z-z
        );
    }

    @Override
    public String toString() {
        return "Vector3D{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    public static Vector3D invert(Vector3D a){
        return new Vector3D(-a.x, -a.y, -a.z);
    }
    public Vector3D invert(){
        return Vector3D.invert(this);
    }
    public static Vector3D factor(Vector3D a, double f){
        return new Vector3D(a.x*f, a.y*f, a.z*f);
    }
    public Vector3D add(Vector3D a){
        return Vector3D.add(this, a);
    }
    public Double isMultiple(Vector3D a){
        double k = a.getX()/this.x;

        if (!(a.getY()*k==y && a.getZ()*k==z)) return null;
        return k;
    }
    public boolean is(Vector3D compare){
        return (compare.x==x &&
        compare.y == y &&
        compare.z == z);
    }
    public Vector3D factor(double factor){
        return Vector3D.factor(this, factor);
    }
    public double lengthSquared(){
        return x*x+y*y+z*z;
    }

    public double length(){
        return Math.sqrt(this.lengthSquared());
    }
    public Vector3D normalize(){
        double length = this.length();
        double inverse = 1/length;
        return new Vector3D(x*inverse, y*inverse, z*inverse);
    }
    public Vector3D half(){
        return new Vector3D(x/2, y/2, z/2);
    }
    public double dotProduct(Vector3D a){
        return Vector3D.dotProduct(this, a);
    }
    public Vector3D crossProduct(Vector3D a){
        return Vector3D.crossProduct(this, a);
    }
    public static double dotProduct(Vector3D a, Vector3D b){
        return a.x*b.x + a.y*b.y + a.z*b.z;
    }
    public static Vector3D crossProduct(Vector3D a, Vector3D b){
        return new Vector3D(
                a.y*b.z - a.z*b.y,
                a.z*b.x - a.x*b.z,
                a.x*b.y - a.y*b.x
        );
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }
}
