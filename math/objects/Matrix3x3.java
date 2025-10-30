package math.objects;

import math.Math3D;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Matrix3x3 {
    private ArrayList<Double> matrix = new ArrayList<>(List.of(new Double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}));
    /*
    Organized like:
     Y  X   0 1 2
     0  [ [ a b c ]
     1    [ d e f ]
     2    [ g h i ] ]
     */
    public Matrix3x3(Double a, Double b, Double c, Double d, Double e, Double f, Double g, Double h, Double i){
        set(0,0,a);
        set(1,0,b);
        set(2,0,c);
        set(0,1,d);
        set(1,1,e);
        set(2,1,f);
        set(0,2,g);
        set(1,2,h);
        set(2,2,i);
    }
    public Matrix3x3(Vector3D a, Vector3D b, Vector3D c){

    }
    private int transform(int x, int y){
        return y*3+x;
    }
    public void set(int x, int y, Double value){
        matrix.set(transform(x, y), value);
    }
    public Double get(int x, int y){
        return matrix.get(transform(x, y));
    }
    public Double determinant(){
        return get(0,0)*(get(1,1)*get(2,2)-get(2,1)*get(1,2))
                - get(1,0)*(get(0,1)*get(2,2)-get(2,1)*get(0,2))
                + get(2,0)*(get(0,1)*get(1,2)-get(0,2)*get(1,1));
    }
    /*
    ( a b c )
    ( d e f )
    ( g h i )
     */
    public Matrix3x3 inverse(){
        double det = determinant();
        if (Math3D.isZero(det)){
            return null;
        }
        double f = 1/det;
        /*
        Calculate Minors & apply sings
        Matrix3x3 a= new Matrix3x3(
                E()*I()-F()*H(), -D()*I()-G()*F(), D()*H()-E()*G(),
                -B()*I()-C()*H(), A()*I()-C()*G(), -A()*H()-B()*G(),
                B()*F()-E()*C(), -A()*F()-C()*D(), A()*E()-D()*B()
        );
        Transpose
        Matrix3x3 b = new Matrix3x3(
                E()*I()-F()*H(), -B()*I()-C()*H(), B()*F()-E()*C(),
                -D()*I()-G()*F(), A()*I()-C()*G(), -A()*F()-C()*D(),
                D()*H()-E()*G(), -A()*H()-B()*G(), A()*E()-D()*B()
        );*/
        return new Matrix3x3(
                (E()*I()-F()*H())*f,-( B()*I()-C()*H())*f,( B()*F()-E()*C())*f,
                -(D()*I()-G()*F())*f,( A()*I()-C()*G())*f,-( A()*F()-C()*D())*f,
                (D()*H()-E()*G())*f,-( A()*H()-B()*G())*f,( A()*E()-D()*B())*f
        );
    }
    public Vector3D multiply(Vector3D a){
        return new Vector3D(
                A()*a.x + B()*a.y + C()*a.z,
                D()*a.x + E()*a.y + F()*a.z,
                G()*a.x + H()*a.y + I()*a.z
        );
    }

    public Double A(){
        return get(0,0);
    }
    public Double B(){
        return get(1,0);
    }
    public Double C(){
        return get(2,0);
    }
    public Double D(){
        return get(0,1);
    }
    public Double E(){
        return get(1,1);
    }
    public Double F(){
        return get(2,1);
    }
    public Double G(){
        return get(0,2);
    }
    public Double H(){
        return get(1,2);
    }
    public Double I(){
        return get(2,2);
    }
}
