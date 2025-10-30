package tools;

import math.objects.Point3D;
import math.objects.Vector3D;

public class RayResult {
    public double k;
    public PixelColor m;
    public Vector3D newRay;
    public Point3D newCurr;

    public RayResult(double k, PixelColor m, Vector3D newRay, Point3D newCurr) {
        this.k = k;
        this.m = m;
        this.newRay = newRay;
        this.newCurr = newCurr;
    }
}
