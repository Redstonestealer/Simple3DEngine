package tools.materials;

import math.objects.Vector3D;
import tools.PixelColor;
import tools.Tuple2;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageMaterial extends MonoMaterial {
    BufferedImage img;
    int width;
    int height;
    Point2D.Double vA;
    Point2D.Double vB;
    Point2D.Double vC;


    /**
     * Create a Material with an image.
     * Takes 3 Tuples for each of the triangle's vertex, so that the UV map can be applied as wished.
     * These Tuples can only go from 0.0 to 1.0
     * @param path The path to the image
     * @param vertexA The A vertex
     * @param vertexB The B vertex
     * @param vertexC The C vertex
     * @param roughness The roughness
     * @param light The light level
     * @param reflectiveness How well it reflects
     * @throws IOException If image isn't found
     */
    public ImageMaterial(String path, Point2D.Double vertexA, Point2D.Double vertexB, Point2D.Double vertexC, double roughness, double light, double reflectiveness) throws IOException {
        super(0.0,0.0,0.0, roughness, light, reflectiveness);
        img = ImageIO.read(new File(path));

        // Save the uv mapping of the vertecies to the picture
        this.vA=vertexA; this.vB=vertexB; this.vC=vertexC;

        width=img.getWidth();
        height=img.getHeight();
    }

    public ImageMaterial(BufferedImage img, Point2D.Double vertexA, Point2D.Double vertexB, Point2D.Double vertexC, double roughness, double light, double reflectiveness) throws IOException {
        super(0.0,0.0,0.0, roughness, light, reflectiveness);
        this.img = img;

        // Save the uv mapping of the vertecies to the picture
        this.vA=vertexA; this.vB=vertexB; this.vC=vertexC;

        width=img.getWidth();
        height=img.getHeight();
    }
    private int toInt(Double d){
        return d.intValue();
    }

    @Override
    public PixelColor getMaterialAtPoint(double x, double z) {
        // Interpolate the position
        Vector3D toB = new Vector3D(
                this.vB.getX()-this.vA.getX(),
                0.0,
                this.vB.getY()-this.vA.getY()
        );
        Vector3D toC = new Vector3D(
                this.vC.getX()-this.vA.getX(),
                0.0,
                this.vC.getY()-this.vA.getY()
        );
        Vector3D toA = new Vector3D(
                this.vA.getX(),
                0.0,
                this.vA.getY()
        );
        Vector3D f = Vector3D.add(
                toA,
                toB.factor(x),
                toC.factor(z)
        );


        Color c;
        try {
            c= new Color(img.getRGB(
                    toInt(f.getX()*width),
                    toInt(f.getZ()*height)
            ));
        } catch (Exception e){
            c = new Color(0,0,0);
        }
        return new PixelColor(
                c.getRed()/255.,c.getGreen()/255.,c.getBlue()/255.,
                roughness, light, reflectiveness
        );
    }
}
