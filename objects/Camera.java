package objects;

import math.Math3D;
import math.objects.Point3D;
import math.objects.Vector3D;
import math.objects.enums.Axis;
import math.objects.enums.Direction;
import tools.Tuple2;

import java.awt.*;

public class Camera {
    Vector3D toPosition = new Vector3D(
            0., 0., -5.0
    );
    public double yaw=0.0;
    public double pitch=0.0;
    public double roll=0.0;
    Vector3D forwardDirection; // Z direction
    Vector3D rightSideDirection; // X direction
    Vector3D upwardsDirection; // Y direction

    double near = 0.1;
    double far = 100;
    double fov = 0.5*Math.PI;

    public Vector3D getForwardDirection() {
        return forwardDirection;
    }

    public Vector3D getRightSideDirection() {
        return rightSideDirection;
    }

    public Vector3D getUpwardsDirection() {
        return upwardsDirection;
    }

    public Vector3D getToPosition() {
        return toPosition;
    }

    public double getNear() {
        return near;
    }

    public double getFar() {
        return far;
    }

    public double getFov() {
        return fov;
    }

    public void calculateDirection(){
        this.forwardDirection = Math3D.rotateAround(
                Axis.X,
                pitch,
                Math3D.rotateAround(
                        Axis.Y,
                        yaw,
                        Vector3D.getZUnitVector()
                )
        );
        this.upwardsDirection = Math3D.rotateAround(
                Axis.X,
                pitch,
                Vector3D.getYUnitVector()
        );
        this.rightSideDirection = Math3D.rotateAround(
                Axis.Y,
                yaw,
                Vector3D.getXUnitVector()
        );

        //this.rightSideDirection = new Vector3D(forwardDirection.z, forwardDirection.y, forwardDirection.x);
        //this.upwardsDirection = new Vector3D(forwardDirection.x, forwardDirection.z, forwardDirection.y);
    }
    public void moveAlongLocalAxis(Direction dir, double amount, boolean recalculated){
        if (!recalculated) calculateDirection();
        switch (dir){
            case FORWARD->{
                toPosition=toPosition.add(forwardDirection.factor(amount));
                break;
            }
            case RIGHT->{
                toPosition=toPosition.add(rightSideDirection.factor(amount));
                break;
            }
            case UP->{
                toPosition=toPosition.add(upwardsDirection.factor(amount));
                break;
            }
        }
    }

    @Override
    public String toString() {
        return "Camera{" +
                "forwardDirection=" + forwardDirection +
                ", rightSideDirection=" + rightSideDirection +
                ", upwardsDirection=" + upwardsDirection +
                '}';
    }
    public String getRot() {
        return "Camera{" +
                "yaw=" + yaw +
                ", pitch=" + pitch +
                ", roll=" + roll +
                '}';
    }

    public void rotateAroundLocalAxis(Axis axis, double amount){
        switch (axis){
            case X->{
                this.pitch+=amount;
                break;
            }
            case Y->{
                this.yaw+=amount;
                break;
            }
            case Z->{
                this.roll+=amount;
                break;
            }

        }
    }

    public Point3D transformToLocalSpace(Point3D p){
        Vector3D relativeToCam = p.getVector().add(this.toPosition.invert());
        Vector3D inRotation = Vector3D.add(
                this.rightSideDirection.factor(relativeToCam.x),
                this.upwardsDirection.factor(relativeToCam.y),
                this.forwardDirection.factor(relativeToCam.z)
        );
        return inRotation.pointsTo();
    }
    public Point projectToScreen(Point3D p, int width, int height){
        double aspect = width*1./height;
        double x = p.getX();
        double y = p.getY();
        double z = p.getZ();
        // Cut out point beyond camera view
        if (z<=near ||z >= far) return null;

        double f = 1/Math.tan(fov/2);
        double x_ndc = (f/aspect) * (x/z);
        double y_ndc = f* (y/z);

        // FOV Cutoff
        if (Math.abs(x_ndc)>1 ||Math.abs(y_ndc)>1) return null;
        return new Point(
                ((Double)((x_ndc+1)*0.5*width)).intValue(),
                ((Double)((1-y_ndc)*0.5*height)).intValue()
        );
    }
    public Point3D getLocation(){
        return toPosition.pointsTo();
    }

}
