package android.hardware;

@Deprecated
public interface SensorListener
{
    void onSensorChanged(final int p0, final float[] p1);
    
    void onAccuracyChanged(final int p0, final int p1);
}
