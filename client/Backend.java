package client;

import math.objects.enums.Axis;
import math.objects.enums.Direction;
import objects.Camera;
import useful.Convertor;
import useful.FontMGR;
import useful.MouseInput;
import useful.cryptics.Key;
import useful.keys.KeyHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

public class Backend extends Thread {
    public ArrayList<String> consoleLog = new ArrayList<String>();

    private static Backend instance;
    public Integer width;
    public Integer height;
    public Camera camera;
    public Boolean running = true;
    public FontMGR fonts;

    public Backend(){
        instance=this;
    }
    public static Backend getInstance(){
        return instance;
    }
    public KeyHandler keyEvents;
    public MouseInput mouseEvents;
    public String defaultFontName = "Consolas";
    public Font defaultFont = Font.getFont(defaultFontName);
    public final String version = "v1.0.0";

    public void checkInstallation(){

    }
    public void log(String... logs){
        for (String l: logs){
            this.consoleLog.add(l); //TODO Add timestamp
            System.out.println(l);
        }
    }
    public Dimension getWindowSize(){
        return new Dimension(this.width, this.height);
    }

    /**Set up the program*/
    public void init(int x, int y){
        //TODO Set up client
        this.camera = new Camera();
        this.keyEvents=new KeyHandler();
        this.mouseEvents=new MouseInput();
        checkInstallation();
    }

    @Override
    public void run() {
        Long lastExecution = 0L;
        camera.calculateDirection();
        while (true) {
            // Making sure the program runs at 30.5 fps
            Long now = System.nanoTime();
            if (now - lastExecution <= 16000000) {
                continue;
            }
            lastExecution = now;

            final double speed = 0.05;
            if (keyEvents.isKeyPressed(KeyEvent.VK_W)) camera.moveAlongLocalAxis(Direction.FORWARD, speed, true);
            if (keyEvents.isKeyPressed(KeyEvent.VK_S)) camera.moveAlongLocalAxis(Direction.FORWARD, -speed, true);
            if (keyEvents.isKeyPressed(KeyEvent.VK_A)) camera.moveAlongLocalAxis(Direction.RIGHT, -speed, true);
            if (keyEvents.isKeyPressed(KeyEvent.VK_D)) camera.moveAlongLocalAxis(Direction.RIGHT, speed, true);
            if (keyEvents.isKeyPressed(KeyEvent.VK_SPACE)) camera.moveAlongLocalAxis(Direction.UP, speed, true);
            if (keyEvents.isKeyPressed(KeyEvent.VK_C)) camera.moveAlongLocalAxis(Direction.UP, -speed, true);

            final double rotation = 0.004*Math.PI;
            if (keyEvents.isKeyPressed(KeyEvent.VK_Q)) camera.rotateAroundLocalAxis(Axis.Y, rotation);
            if (keyEvents.isKeyPressed(KeyEvent.VK_E)) camera.rotateAroundLocalAxis(Axis.Y, -rotation);
            if (keyEvents.isKeyPressed(KeyEvent.VK_R)) camera.rotateAroundLocalAxis(Axis.X, rotation);
            if (keyEvents.isKeyPressed(KeyEvent.VK_F)) camera.rotateAroundLocalAxis(Axis.X, -rotation);

            if (keyEvents.isKeyPressed(KeyEvent.VK_F2)) System.out.println(camera.getLocation().toString());
            if (keyEvents.isKeyPressed(KeyEvent.VK_F3)) System.out.println(camera.getRot().toString());

            if (keyEvents.isKeyPressed(KeyEvent.VK_F4)) {
                Point mp =  Graphics.getInstance().frame.getMousePosition();
                System.out.println("X: "+mp.x/10 + " Y: "+mp.y/10);
            }

        }
    }
    public void resize(){
        this.defaultFont = this.fonts.get(this.defaultFontName, this.height*0.03);

    }
}
