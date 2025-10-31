package client;

import math.Math3D;
import math.objects.Point3D;
import math.objects.Vector3D;
import math.objects.enums.Sign;
import objects.Camera;
import objects.Triangle;
import objects.World;
import tools.Tool;
import tools.Tuple2;
import tools.materials.ImageMaterial;
import tools.materials.Material;
import tools.materials.MonoMaterial;
import tools.PixelColor;
import tools.RayResult;
import useful.*;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;

public class Graphics extends Thread {

    private static Graphics instance;
    public JFrame frame;
    public Canvas canvas;
    public Integer width;
    public Integer height;
    public Boolean running = true;
    public FontMGR fonts;
    public ScreenEmulator screen;
    public World world;

    public Graphics(){
        instance=this;
    }
    public static Graphics getInstance(){
        return instance;
    }
    public String defaultFontName = "Consolas";
    public Font defaultFont = Font.getFont(defaultFontName);
    public final String version = "v1.0.0";

    public void checkInstallation(){

    }
    public void log(String... logs){
        for (String l: logs){
            Backend.getInstance().consoleLog.add(l); //TODO Add timestamp
            System.out.println(l);
        }
    }
    public Dimension getWindowSize(){
        return new Dimension(this.width, this.height);
    }

    /**Set up the program*/
    public void init(int x, int y){
        //TODO Set up client
        log("Connecting to server...");
        frame = new JFrame();
        frame.setResizable(true);
        frame.setTitle("Test");


        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(x, y));
        canvas.setMaximumSize(new Dimension(x, y));
        canvas.addKeyListener(Backend.getInstance().keyEvents); //TODO add mouselistener
        canvas.addMouseListener(Backend.getInstance().mouseEvents);
        canvas.addMouseWheelListener(Backend.getInstance().mouseEvents);
        canvas.createBufferStrategy(1);


        frame.add(canvas);
        frame.setFocusable(true);
        frame.setFocusTraversalKeysEnabled(false);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.width=canvas.getWidth();
        this.height=canvas.getHeight();

        this.fonts = new FontMGR(1);

        this.world=new World();
        checkInstallation();

        MonoMaterial m = new MonoMaterial(1.0, 0.0, 0.0,
                1.0, 0.0, 0.2); // new Material(1.0, 0.0, .0, 1.0, 0.0, 0.2);

        world.addTriangle(new Triangle(new Point3D(.0, -5.0, 5.),
                new Point3D(1., 0., 5.0),
                new Point3D(0., 1., 5.), m));
        world.addTriangle(new Triangle(
                new Point3D(.0, 1., 5.),
                new Point3D(10., 1., 5.),
                new Point3D(0., 1., -5.),
                new MonoMaterial(
                        0.0,
                        0.2,
                        0.7,
                        0.5,
                        0.0,
                        0.8
                )
        ));
        // rough 0.5 refl 0.8 -> Watery stone effect


        world.addTriangle(new Triangle(
                new Point3D(.0, -3., 5.),
                new Point3D(1., -3., 5.),
                new Point3D(0., -3., 3.),
                new MonoMaterial(
                        0.0,
                        0.0,
                        1.0,
                        0.0,
                        0.0,
                        0.8
                )
        ));



        world.setDefaultLight(new MonoMaterial(0.05, 0.4, 0.9, 0.0, 1.0, 0.0)
);

        screen = new ScreenEmulator(this.canvas, 1);
    }

    @Override
    public void run() {
        Long lastExecution = 0L;
        while (true) {
            // Making sure the program runs at 62.5 fps
            Long now = System.nanoTime();
            if (now - lastExecution <= 16000000) {
                continue;
            }
            lastExecution = now;

            // Write any code below

            screen.prepare();

            Point mousePosition = canvas.getMousePosition();
            Camera c = Backend.getInstance().camera;
            c.calculateDirection();


            //g2d.drawString("Pos: "+c.getLocation().toString(), 50, 50);
            //g2d.drawString("Rot: "+c.getRot(), 50, 64);


            // DRAW here


            final int hw = screen.getWidth()/2;
            final int hh = screen.getHeight()/2;
            final double hfov = c.getFov()/2;
            double forwardMagnitude = c.getForwardDirection().length();
            // Start with the ray shooting
            for (int x = 0; x<screen.getWidth(); x++){
                for (int y = 0; y<screen.getHeight(); y++){
                    /* For each pixel, shoot a ray. The rays(vectors) dimensions can be calculated like this:
                    x = tan(x_fov)*near*dx,
                    y = tan(y_fov)*near*dy,
                    z = near
                    Its important to note that dx and dy tell us how the pixel is actually found withing the screen
                     */
                    double dx = (x-hw*1.)/hw;
                    double dy = (y-hh*1.)/hh;

                    /* NOW WE MUST ADD ROTATION
                    Vector3D ray = new Vector3D(
                            Math.tan(hfov*height/width)*c.getNear()*dx,
                            Math.tan(hfov)*c.getNear()*dy,
                            c.getNear()*5
                    );*/
                    final int factor = 100;

                    Vector3D startRay = Vector3D.add(
                            c.getRightSideDirection().factor(Math.tan(hfov*height/width)*forwardMagnitude*dx*factor),
                            c.getUpwardsDirection().factor(Math.tan(hfov)*forwardMagnitude*dy*factor),
                            c.getForwardDirection().factor(factor)
                    );
                    /*
                    We're shooting multiple rays the same way, so that they can scatter around differently and create a more natural looking image
                    We set the amount of repetitions to 4 (for now)
                     */
                    final int maxRep=4;
                    // We also want to keep track of all resulting materials, to then average them out
                    ArrayList<PixelColor> finalPixelColors = new ArrayList<>();
                    for (int re = 0; re<maxRep; re++){
                        Vector3D ray=startRay;


                        // Create a list of all encountered colors
                        ArrayList<PixelColor> pixelColors = new ArrayList<>();
                        // Marching up to 5 reflections
                        final int maxD = 5;
                        // Also keeping track if it encountered any triangles this time
                        // set to true to satisfy the first condition
                        boolean encounteredTriangle=true;
                        // Save previous point to compare (check if hitting triangle)
                        Point3D prev = c.getLocation();



                        for( int d = 0; d<maxD && encounteredTriangle; d++){
                            // Calculate new point
                            Point3D curr = new Point3D(prev.getX()+ray.x, prev.getY()+ray.y, prev.getZ()+ray.z);
                            // Set encounter triangle to false
                            encounteredTriangle=false;
                            // We could also possibly encounter multiple triangles in this step. To prevent any unwanted behavior,
                            // we keep track of all intersections and calculate at the end of this march which triangle is the closest
                            // (and should thus be our new starting point). This can be done by checking whose k (see intersection) is smaller
                            ArrayList<RayResult> rayResults = new ArrayList<>();
                            // Now check all triangles (if side changed)
                            for (Triangle t: world.triangles){
                                // Check if the plane of a triangle is pierced
                                // We calculate the dot product between the prev and the normal vector and the curr and the normal vector
                                // The normal vector is the one looking away from the triangle plane (normal vector)
                                // If the 2 points are on different sides of the plane, their dot product will have different signs
                                double prev_dot = Vector3D.dotProduct(t.getNormalVector(), prev.getVector().add(t.getA().getVector().invert()));
                                if (Math3D.getSign(prev_dot) != Sign.POSITIVE) continue; // If the ray is coming from the wrong side, we can skip everything
                                double curr_dot = Vector3D.dotProduct(t.getNormalVector(), curr.getVector().add(t.getA().getVector().invert()));
                                // Now compare
                                if (Math3D.getSign(prev_dot)!=Math3D.getSign(curr_dot)){
                                    /*
                                    If this is true, the ray has pierced through the plane.
                                    Now we need to verify if the point P, where the plane was pierced, is also inside the triangle
                                    We do this by saying P1 = previous point, P2 = current point, P=?. (All in local space)
                                    For this we need to transform the current and previous point into local space for each triangle
                                    */
                                    Point3D LS_prev = t.transformRelativeToTriangle(prev, true);
                                    Point3D LS_curr = t.transformRelativeToTriangle(curr, true);
                                    /*
                                    P1P2 = P2-P1 = V
                                    We then look for a value k, such that:
                                    P1.y + k*V.y = 0 -> k = - (P1.y/V.y)
                                    We can then calculate the point P:
                                    P1 + k*V = P
                                    */

                                    Vector3D V = LS_curr.getVector().add(LS_prev.getVector().invert());
                                    double k = - (LS_prev.getY() / V.y);
                                    Point3D P = LS_prev.getVector().add(V.factor(k)).pointsTo();

                                /*
                                Point P is thus the point, where the plane was pieced. Now we must check if P is withim the triangle
                                We could do this by calculating the barycentric point within the triangle.
                                Fortunately, our Local Space has another advantage: the X and Z unit vectors are exactly the triangles defining vectors
                                Thus, we can simply use P.x and P.z
                                To be on the triangle, P.x+P.z must be <=1, but each >= 0
                                We call P.x+P.z u
                                */
                                    double u = P.getX()+P.getZ();
                                    if (P.getX()>=0 && P.getZ()>=0 && u<=1){
                                        // If we reach this, we know the point P is withing the triangle t. We must now reflect it
                                        // Now we need to reflect the ray. This is done by finding the mirror line of the ray:
                                        /*   ray  l  outgoing ray
                                               \  |  /
                                                \ | /
                                                 \|/
                                        ------------------------------------ Triangle Plane
                                        The incoming ray is mirrored exactly against the mirror line l to find the outgoing ray.
                                        Fortunately, the mirror line is easily found: it's simply the normal vector
                                        WE must thus mirror the ray along the normal vector.
                                        For this, we can simply invert the local y value
                                        It will still go into the exact same x and z directions, just away from the triangle
                                        TODO: fix length of ray changing
                                        First we recalculate our current ray, but in local space
                                         */
                                        Vector3D LS_ray = P.getVector().add(LS_prev.getVector().invert());
                                        // Now we transform it:
                                        Vector3D LS_newRay = new Vector3D(LS_ray.x, -LS_ray.y, LS_ray.z);
                                        // Before transforming it back into world space, we must make sure that the ray is
                                        // of the same length as before. For this, we simply multiply by 1/k, as this will restore the length
                                        LS_newRay = LS_newRay.factor(1/k);
                                        // Now, we must transform this ray back into world space to keep shooting
                                        // This can also be done by the same function used to transform the point P back into world space
                                        // We save these in our RayResult class, to then compare it to other possible hits
                                        Point3D new_curr = t.transformRelativeToWorldSpace(P, true);
                                        Vector3D new_ray = t.transformRelativeToWorldSpace(LS_newRay.pointsTo(), false).getVector();
                                        // Here we dont respect zero, as we dont care about the offset
                                        // Saving the results
                                        RayResult rr = new RayResult(k, t.getPixelColorAt(P.getX(), P.getZ()), new_ray, new_curr);
                                        rayResults.add(rr);
                                        // Now the ray is recalculated, and starting from another position

                                    }
                                    // Else, if we reach this, the point P is not within the triangle. We thus don't care

                                }
                            }
                            // Now we must process the RayResults we collected. We check each one and look for the closest one
                            if (rayResults.size()!=0){
                                // As we encountered a triangle, we keep track of it:
                                encounteredTriangle=true;

                                RayResult closest = null;
                                double distance = -1;
                                for (RayResult r: rayResults){
                                    if (r.k<distance || distance==-1){
                                        closest=r;
                                        distance=r.k;
                                    }
                                }
                                pixelColors.add(closest.m);
                                double roughness = closest.m.getRoughness();
                                ray=new Vector3D(closest.newRay.x*(2-Math.random()*roughness)/2,
                                        closest.newRay.y*(2-Math.random()*roughness)/2,
                                        closest.newRay.z*(2-Math.random()*roughness)/2);
                                curr=closest.newCurr.getVector().add(ray.factor(0.01)).pointsTo();
                            }
                            // Finally, we set the previous point to the current point and start again
                            prev = curr;
                        }
                        // Once we reach this, we know the ray has been shot all the way. Now we must just calculate the color of the pixel.
                        // For this we use the list of materials we have
                        // First, we add the sourround light material though
                        PixelColor result = world.getDefaultLight();
                        // We now go backwards and always calculate the new colors as follows:
                        /*
                        m is the material, ref is the reflectiveness, p is the previously calculated material
                        ( r )   m.r * (1-ref) + p.r*ref
                        ( g ) = m.g * (1-ref) + p.g*ref
                        ( b )   m.b * (1-ref) + p.b*ref
                         */

                        for (int i = 0; i<pixelColors.size(); i++){
                            PixelColor prev_m = result;
                            PixelColor m = pixelColors.get(pixelColors.size()-1-i);
                            double ref = m.getReflectiveness();
                            result = new PixelColor(
                                    m.getRed() * (1-ref) + prev_m.getRed()*ref,
                                    m.getGreen() * (1-ref) + prev_m.getGreen()*ref,
                                    m.getBlue() * (1-ref) + prev_m.getBlue()*ref,
                                    0.0,
                                    prev_m.getLight(),
                                    0.0
                            );
                        }
                        // Now, finally, result holds the colors of our pixel
                        // We quickly apply the light level of result.light, and then we can draw it
                        result.applyLightLevel();
                        // Now we store it away to use it later
                        finalPixelColors.add(result);
                    }

                    // Finally, after all repetitions, we can now finally calculate the average color of each pixel
                    // We want the weight of each (w) color
                    double w = 1./maxRep;

                    // now we simply add them together
                    PixelColor f = new PixelColor(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
                    for (PixelColor pc: finalPixelColors){
                        f.red+=pc.red*w;
                        f.green+=pc.green*w;
                        f.blue+=pc.blue*w;
                    }
                    // After this, we can FINALLY apply the color
                    screen.setColor(f.to255BaseColor());

                    // And now we draw:
                    screen.draw(x, y);
                }
            }

            //screen.dispose();

        }
    }
    public void drawToScreen(Graphics2D g2d, int x, int y){
        final int scalar = 10;
        g2d.fillRect(x*scalar, y*scalar, x+scalar-1, y+scalar-1);
    }
    public void resize(){
        this.width = this.frame.getWidth();
        this.height = this.frame.getHeight();
        this.defaultFont = this.fonts.get(this.defaultFontName, this.height*0.03);

    }
    // Based on my older project "quickengine"
}
