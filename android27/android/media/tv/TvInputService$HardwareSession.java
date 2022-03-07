package android.media.tv;

import android.content.*;
import android.view.*;

public abstract static class HardwareSession extends Session
{
    public HardwareSession(final Context context) {
        super(null);
        throw new RuntimeException("Stub!");
    }
    
    public abstract String getHardwareInputId();
    
    @Override
    public final boolean onSetSurface(final Surface surface) {
        throw new RuntimeException("Stub!");
    }
    
    public void onHardwareVideoAvailable() {
        throw new RuntimeException("Stub!");
    }
    
    public void onHardwareVideoUnavailable(final int reason) {
        throw new RuntimeException("Stub!");
    }
}
