package math;

import math.objects.Vector3D;
import math.objects.enums.Axis;
import math.objects.enums.Sign;

public class Math3D {
    public static boolean isZero(double v){
        return Math.abs(v)<0.0000001;
    }
    public static Sign getSign(double v){
        if (isZero(v)) return Sign.ZERO;
        else if (v<0) return Sign.NEGATIVE;
        return Sign.POSITIVE;
    }
    public static double random(){
        return Math.random();
    }
    public static Vector3D rotateAround(Axis axis, double rad, Vector3D vector){
        double cos = Math.cos(rad);
        double sin = Math.sin(rad);
        switch (axis){
            case X -> {
                /*
                [ 1    0    0  ] ( x )
                [ 0   cos  sin ] ( y )
                [ 0  -sin  cos ] ( z )
                 */

                return new Vector3D(
                        vector.getX(),
                        vector.getY()*cos + vector.getZ()*sin,
                        vector.getY()*-sin + vector.getZ()*cos
                );
            }
            case Y->{
                /*
                [cos   0  -sin ] ( x )
                [ 0    1    0  ] ( y )
                [sin   0   cos ] ( z )
                 */
                return new Vector3D(
                        vector.getX()*cos + vector.getZ()*-sin,
                        vector.getY(),
                        vector.getX()*sin + vector.getZ()*cos
                );
            }
            case Z->{
                /*
                [cos  sin   0  ] ( x )
                [-sin cos   0  ] ( y )
                [ 0    0    1  ] ( z )
                 */
                return new Vector3D(
                        vector.getX()*cos+vector.getY()*sin,
                        vector.getX()*-sin + vector.getY()*cos,
                        vector.getZ()
                );
            }
        }
        return null;
    }
}
