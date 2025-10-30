package tools;

import math.objects.Point3D;
import math.objects.Vector3D;
import objects.Triangle;
import objects.World;
import tools.materials.ImageMaterial;
import tools.materials.Material;
import tools.materials.MonoMaterial;

import javax.imageio.ImageIO;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Tool {
    public static int toInt(Double d){
        return d.intValue();
    }

    /**
     * Create a rectangle with a picture in it.
     * @param path
     * @param w
     * @param A
     * @param B
     * @param C
     * @param D
     */
    /*
    Asume
    A-B
    |/|
    C-D
     */
    public static void createImage(String path, World w, Point3D A, Point3D B, Point3D C, Point3D D){
        try {
            BufferedImage img = ImageIO.read(new File(path));

            Triangle first = new Triangle(
                    Point3D.copy(A),Point3D.copy(C),Point3D.copy(B),
                    new ImageMaterial(
                            img,
                            new Point2D.Double(0.0, 0.0),
                            new Point2D.Double(0.0, 1.0),
                            new Point2D.Double(1.0, 0.0),
                            0.0,0.0,0.0
                    ));

            Triangle second = new Triangle(
                    Point3D.copy(D),Point3D.copy(B),Point3D.copy(C),
                    new ImageMaterial(
                            img,
                            new Point2D.Double(1.0, 1.0),
                            new Point2D.Double(1.0, 0.0),
                            new Point2D.Double(0.0, 1.0),
                            0.0,0.0,0.0
                    )
            );
            w.addTriangle(first);
            w.addTriangle(second);

        } catch (IOException e){

        }


    }
    public static void drawPoint(World w, Point3D p){
        w.addTriangle(new Triangle(
                p, new Point3D(p.getX(), p.getY()+0.1, p.getZ()),
                new Point3D(p.getX()+0.1, p.getY(), p.getZ()),
                new MonoMaterial(1.0,1.0, 1.0, 0.0,0.0,0.0)
        ));
    }

    public static void createGrid(World w){
        w.addTriangle(new Triangle(
                new Point3D(0.0,0.1,0.0),
                new Point3D(0.0, -0.1, 0.0),
                new Point3D(2.0, 0.0, 0.0),
                new MonoMaterial(1.0, 0.0, 0.0, 0.0, 0.0, 0.0)
        ));

        w.addTriangle(new Triangle(
                new Point3D(0.1,0.0,0.0),
                new Point3D(-.1, 0.0, 0.0),
                new Point3D(0.0, 2.0, 0.0),
                new MonoMaterial(0.0, 1.0, 0.0, 0.0, 0.0, 0.0)
        ));

        w.addTriangle(new Triangle(
                new Point3D(0.0,0.1,0.0),
                new Point3D(0.0, -.1, 0.0),
                new Point3D(0.0, 0.0, 2.0),
                new MonoMaterial(0.0, 0.0, 1.0, 0.0, 0.0, 0.0)
        ));

    }
}
