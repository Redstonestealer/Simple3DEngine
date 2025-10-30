package useful.keys;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class KeyHandler implements KeyListener {
    private ArrayList<KeyEvent> pressEvents = new ArrayList<>();
    private ArrayList<KeyEvent> releaseEvents = new ArrayList<>();
    private ArrayList<Integer> pressedKeys = new ArrayList<>();
    private LastPressed lastPressedKey = null;
    private KeyEvent lastPressedKeyEvent = null;
    private Boolean shift=false;
    private Boolean alt=false;
    private Boolean control = false;
    private Boolean altGraph = false;
    private ArrayList<KeyPressAction> keyPressActions = new ArrayList<>();
    private static KeyHandler instance;
    public static KeyHandler getInstance(){return instance;}
    public KeyHandler(){instance=this;}

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!this.pressedKeys.contains(e.getKeyCode())){
            this.pressedKeys.add(e.getKeyCode());
            this.pressEvents.add(e);
            this.lastPressedKey=new LastPressed(e.getKeyCode(), e.getKeyChar(), System.nanoTime());
            this.lastPressedKeyEvent =e;

            switch (e.getKeyCode()){
                case KeyEvent.VK_SHIFT -> this.shift=true;
                case KeyEvent.VK_ALT -> this.alt=true;
                case KeyEvent.VK_ALT_GRAPH -> this.altGraph=true;
                case KeyEvent.VK_CONTROL -> this.control=true;
            }

            for (KeyPressAction kpa: this.keyPressActions){
                if (kpa.keycode == e.getKeyCode()){
                    Boolean modifiersPressed=true;
                    //TODO add function Boolean isModifier(KeyModifiers modifier){...}
                    for (KeyModifiers km: kpa.modifiers){
                        switch (km){
                            case ALT -> {if (!isAlt()){modifiersPressed=false;break;}}
                            case SHIFT -> {if (!isShift()){modifiersPressed=false;break;}}
                            case ALT_GRAPH -> {if(!isAltGraph()){modifiersPressed=false;break;}}
                            case CTRL -> {if(!isControl()){modifiersPressed=false;break;}}
                        }
                    }
                    if (modifiersPressed){
                        kpa.action.onAction(new Object[0]);
                    }
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (this.pressedKeys.contains(e.getKeyCode())){
            this.pressedKeys.remove((Object) e.getKeyCode());
            this.releaseEvents.add(e);
            if (this.lastPressedKey!=null){
                if (this.lastPressedKey.keycode==e.getKeyCode()){
                    this.lastPressedKey=null;
                    this.lastPressedKeyEvent=null;
                }
            }
            switch (e.getKeyCode()){
                case KeyEvent.VK_SHIFT -> this.shift=false;
                case KeyEvent.VK_ALT -> this.alt=false;
                case KeyEvent.VK_ALT_GRAPH -> this.altGraph=false;
                case KeyEvent.VK_CONTROL -> this.control=false;
            }
        }
    }
    public Boolean isKeyPressed(Integer keycode){
        return this.pressedKeys.contains(keycode);
    }
    public Boolean isShift(){
        return shift;
    }
    public Boolean isAlt(){return alt;}
    public Boolean isControl(){return control;}
    public Boolean isAltGraph(){return altGraph;}
    public void registerKeyPressEvent(KeyPressAction kpa){
        this.keyPressActions.add(kpa);
    }
    public ArrayList<KeyEvent> getPressEvents(){
        ArrayList<KeyEvent> events = (ArrayList<KeyEvent>) this.pressEvents.clone();
        this.pressEvents.clear();
        return events;
    }
    public ArrayList<KeyEvent> getReleaseEvents(){
        ArrayList<KeyEvent> events = (ArrayList<KeyEvent>) this.releaseEvents.clone();
        this.releaseEvents.clear();
        return events;
    }
    public Integer getTypedKeyCode(){
        if (this.lastPressedKeyEvent!=null){
            int code = this.lastPressedKeyEvent.getKeyCode();
            this.lastPressedKeyEvent=null;
            return code;
        }
        Long now = System.nanoTime();
        if (this.lastPressedKey!=null){
            if (now-lastPressedKey.sinceWhen>=500000000L){
                return lastPressedKey.keycode;
            }
        }
        return null;
    }
    public Character getTypedKeyChar(){
        if (this.lastPressedKeyEvent!=null){
            char code = this.lastPressedKeyEvent.getKeyChar();
            this.lastPressedKeyEvent=null;
            return code;
        }
        Long now = System.nanoTime();
        if (this.lastPressedKey!=null){
            if (now-lastPressedKey.sinceWhen>=500000000L){
                return lastPressedKey.Char;
            }
        }
        return null;
    }
}
class LastPressed{
    public Integer keycode;
    public Long sinceWhen;
    public Character Char;
    public LastPressed(Integer keycode, char Char, Long sinceWhen){
        this.keycode=keycode;this.sinceWhen=sinceWhen; this.Char=Char;
    }

    @Override
    public String toString() {
        return "LastPressed{" +
                "keycode=" + keycode +
                ", sinceWhen=" + sinceWhen +
                '}';
    }
}
