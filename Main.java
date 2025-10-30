import client.Backend;
import client.Graphics;
import math.objects.enums.Axis;
import math.objects.enums.Direction;
import objects.Camera;

public class Main {
    public static void main(String[] args) {
        Camera c = new Camera();
        c.moveAlongLocalAxis(Direction.FORWARD, 5.0, false);
        c.moveAlongLocalAxis(Direction.UP, 3.0, true);
        c.rotateAroundLocalAxis(Axis.X, 0.5*Math.PI);
        c.moveAlongLocalAxis(Direction.FORWARD, 2.0, false);
        c.moveAlongLocalAxis(Direction.UP, -1.0, true);

        int WIDTH = 500;
        int HEIGHT = 300;
        Backend b = new Backend();
        b.init(WIDTH, HEIGHT);
        b.start();
        Graphics g = new Graphics();
        g.init(WIDTH, HEIGHT);
        g.start();

        System.out.println(c.getLocation().toString());
        System.out.println(c);
    }
}