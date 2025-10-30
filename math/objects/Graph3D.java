package math.objects;

import math.objects.enums.Graph3DRelation;
import tools.Tuple2;

public class Graph3D {
    Vector3D direction;
    Point3D start;
    Graph3D(Point3D start, Vector3D direction){
        this.start=start; this.direction=direction;
    }

    public Vector3D getDirection() {
        return direction;
    }

    public Point3D getStart() {
        return start;
    }
    public Point3D pointAt(double x){
        Vector3D toStart = start.getVector();
        return toStart.add(direction.factor(x)).pointsTo();
    }

    public Double isInGraph(Point3D p){
        // X
        Vector3D start = getStart().getVector();
        double k = (p.getX() - start.x)/direction.x;

        Point3D at = pointAt(k);
        if (!at.is(p)) return null;
        return k;
    }
    public static Point3D getIntersection(Graph3D a, Graph3D b){
        // Calculate intersection
        /*
        Given:
        ( A )      ( l )   ( p )      ( u )
        ( B ) + x* ( m ) = ( q ) + y* ( v )
        ( C )      ( n )   ( r )      ( w )
                            b + ( (m*p - m*a) / l ) - q
        We know that y = -------------------------------------
                                    v - (m*n)/l
                 p + y*u - a
        And x = -------------
                      l
         We can then check ith with c+y*n = r+w*y
         */
        double y = (a.start.getY()+ (a.direction.y/a.direction.x)*(b.start.getX()-a.start.getX())-b.start.getY()) / (b.direction.y-(a.direction.y*b.direction.x)/a.direction.x);
        double x = (b.start.getX()+y*b.direction.x-a.start.getX())/a.direction.x;
        if (a.start.getZ() + x*a.direction.z == b.start.getZ() + b.direction.z*y) return a.start.getVector().add(a.direction.factor(x)).pointsTo();
        return null;
    }
    public Point3D getIntersectionWith(Graph3D a){
        return Graph3D.getIntersection(this, a);
    }

    public static Graph3DRelation getRelation(Graph3D a, Graph3D b){
        if (a.direction.isMultiple(b.direction)!=null){
            // Is multiple, then check for point
            Point3D start = a.getStart();
            if (b.isInGraph(start)!=null){
                return Graph3DRelation.IDENTICAL;
            };
            return Graph3DRelation.PARALLEL;
        } else {
            // Isn't multiple
            // Check for intersection
            Point3D intersection = Graph3D.getIntersection(a, b);
            if (intersection!= null) return Graph3DRelation.INTERSECTING;
            return Graph3DRelation.APART;

        }
    }
}
