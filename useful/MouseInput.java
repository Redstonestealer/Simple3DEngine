package useful;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

public class MouseInput implements MouseListener, MouseWheelListener {
    private ArrayList<MouseEvent> pressEvent = new ArrayList<>();
    private ArrayList<MouseEvent> releaseEvent = new ArrayList<>();
    private static MouseInput instance;
    public MouseInput(){
        instance=this;
    }
    public static MouseInput getInstance(){return instance;}
    @Override
    public void mouseClicked(MouseEvent e) {
        // Fires when clicked and released
        System.out.println(e.getButton());
        System.out.println(e.getClickCount());

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Fires when clicked
        this.pressEvent.add(e);

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Fires when released
        this.releaseEvent.add(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Fires when mouse enters the Windows
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Fires when mouse leaves the window
    }
    public ArrayList<MouseEvent> getPressEvent(){
        ArrayList<MouseEvent> events = (ArrayList<MouseEvent>) this.pressEvent.clone();
        this.pressEvent.clear();
        return events;
    }
    public ArrayList<MouseEvent> getReleaseEvent(){
        ArrayList<MouseEvent> events = (ArrayList<MouseEvent>) this.releaseEvent.clone();
        this.releaseEvent.clear();
        return events;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        System.out.println( e.getWheelRotation());
    }
}
