package android.hardware.input;

import android.view.*;
import android.os.*;

public final class InputManager
{
    public static final String ACTION_QUERY_KEYBOARD_LAYOUTS = "android.hardware.input.action.QUERY_KEYBOARD_LAYOUTS";
    public static final String META_DATA_KEYBOARD_LAYOUTS = "android.hardware.input.metadata.KEYBOARD_LAYOUTS";
    
    InputManager() {
        throw new RuntimeException("Stub!");
    }
    
    public InputDevice getInputDevice(final int id) {
        throw new RuntimeException("Stub!");
    }
    
    public int[] getInputDeviceIds() {
        throw new RuntimeException("Stub!");
    }
    
    public void registerInputDeviceListener(final InputDeviceListener listener, final Handler handler) {
        throw new RuntimeException("Stub!");
    }
    
    public void unregisterInputDeviceListener(final InputDeviceListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public interface InputDeviceListener
    {
        void onInputDeviceAdded(final int p0);
        
        void onInputDeviceRemoved(final int p0);
        
        void onInputDeviceChanged(final int p0);
    }
}
