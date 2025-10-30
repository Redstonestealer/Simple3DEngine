package math.objects;

import math.Math3D;

public class CoordinateSystem {
    Vector3D toZero;
    Vector3D unitVectorX;
    Vector3D unitVectorY;
    Vector3D unitVectorZ;
    Matrix3x3 matrix;
    Matrix3x3 inverseMatrix;
    public CoordinateSystem(Point3D zero, Vector3D X, Vector3D Y, Vector3D Z){
        this.toZero=zero.getVector();
        this.unitVectorZ=Z;
        this.unitVectorY=Y;
        this.unitVectorX=X;
        matrix = new Matrix3x3(X.x, Y.x, Z.x,
                X.y, Y.y, Z.y,
                X.z, Y.z, Z.z);
        inverseMatrix = matrix.inverse();
    }


    /**
     * Transforms a world space point into a local space point
     * @param p The world space Point
     * @return The Local space point corresponding to p
     */
    public Point3D transformIntoSystem(Point3D p, boolean respectZero){
        Vector3D v = (respectZero?p.getVector().add(toZero.invert()):p.getVector());
        return inverseMatrix.multiply(v).pointsTo();
    }

    /**
     * Transforms a local space point into world space
     * @param p The local space Point
     * @return The corresponding world space point
     */
    public Point3D toWorldSpace(Point3D p, boolean respectZero){
        return matrix.multiply(p.getVector()).add(
                (respectZero?toZero:Vector3D.getZeroVector())
        ).pointsTo();
    }
    public Vector3D getUnitVectorX() {
        return unitVectorX;
    }

    public Vector3D getUnitVectorY() {
        return unitVectorY;
    }

    public Vector3D getUnitVectorZ() {
        return unitVectorZ;
    }

    public Point3D getZero() {
        return this.toZero.pointsTo();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
