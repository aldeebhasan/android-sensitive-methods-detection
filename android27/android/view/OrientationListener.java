package android.view;

import android.hardware.*;
import android.content.*;

@Deprecated
public abstract class OrientationListener implements SensorListener
{
    public static final int ORIENTATION_UNKNOWN = -1;
    
    public OrientationListener(final Context context) {
        throw new RuntimeException("Stub!");
    }
    
    public OrientationListener(final Context context, final int rate) {
        throw new RuntimeException("Stub!");
    }
    
    public void enable() {
        throw new RuntimeException("Stub!");
    }
    
    public void disable() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onAccuracyChanged(final int sensor, final int accuracy) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onSensorChanged(final int sensor, final float[] values) {
        throw new RuntimeException("Stub!");
    }
    
    public abstract void onOrientationChanged(final int p0);
}
