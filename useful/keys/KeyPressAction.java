package useful.keys;

import useful.Action;

public class KeyPressAction {
    public Integer keycode;
    public KeyModifiers[] modifiers;
    public Action action;
    public KeyPressAction(Integer keycode, KeyModifiers[] modifiers, Action action){
        this.keycode = keycode;
        this.modifiers = modifiers;
        this.action=action;
    }
}
