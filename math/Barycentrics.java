package math;

import math.objects.Vector3D;

public class Barycentrics {
    public static Double getW1(Vector3D A, Vector3D B, Vector3D C, Vector3D P){
        return (1.0*(A.x*(C.y-A.y)+(P.y-A.y)*(C.x-A.x)-P.x*(C.y-A.y))) / (1.0*((B.y-A.y)*(C.x- A.x)-(B.x-A.x)*(C.y-A.y)));
    }
    public static Double getW2(Vector3D A, Vector3D B, Vector3D C, Vector3D P, Double w1){
        return (P.y-A.y-w1*(B.y-A.y)) / (C.y-A.y);
    }
    public static Double getW3(Double w1, Double w2){
        return 1-w1-w2;
    }
}
