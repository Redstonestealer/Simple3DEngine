package math.objects;

public class Point3D {
    private double x=0.0;
    private double y=0.0;
    private double z=0.0;

    public Point3D(double x, double y, double z){
        this.x=x;
        this.y=y;
        this.z=z;
    }
    public static Point3D getZero(){
        return new Point3D(0.0, 0.0, 0.0);
    }
    public Double getX() {
        return x;
    }

    public Double getY() {
        return y;
    }
    public Vector3D getVector(){
        return new Vector3D(x, y, z);
    }
    public boolean is(Point3D compare){
        return (compare.x==x &&
                compare.y == y &&
                compare.z == z);
    }

    public static Point3D copy(Point3D p){
        return new Point3D(p.x,p.y,p.z);
    }
    @Override
    public String toString() {
        return "Point3D{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    public Double getZ() {
        return z;
    }
}
