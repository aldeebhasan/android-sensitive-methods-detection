package android.view;

import android.os.*;

public abstract class InputEvent implements Parcelable
{
    public static final Creator<InputEvent> CREATOR;
    
    InputEvent() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract int getDeviceId();
    
    public final InputDevice getDevice() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract int getSource();
    
    public boolean isFromSource(final int source) {
        throw new RuntimeException("Stub!");
    }
    
    public abstract long getEventTime();
    
    @Override
    public int describeContents() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
}
