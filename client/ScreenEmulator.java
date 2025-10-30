package client;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class ScreenEmulator {
    Graphics2D g2d;
    int width;
    int height;
    int hw;
    int hh;
    Canvas c;
    int scale;
    public ScreenEmulator(Canvas c, int scale){
        this.c=c;
        this.width=c.getWidth()/scale;
        this.height=c.getHeight()/scale;
        this.hw=width/2;
        this.hh=height/2;
        this.scale=scale;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getHw() {
        return hw;
    }

    public int getHh() {
        return hh;
    }

    public void prepare(){
        BufferStrategy bs = this.c.getBufferStrategy();
        this.g2d = (Graphics2D) bs.getDrawGraphics();
    }
    public void setColor(Color c){
        g2d.setColor(c);
    }
    public void draw(int x, int y){
        g2d.fillRect(x*scale, c.getHeight()-y*scale, scale, scale);
        // c.getHeight() - y*scale because in our worldspace, positive y is upwards,
        // but in screenspace, positive y is downwards
    }
    public void dispose(){
        g2d.dispose();
    }


}
