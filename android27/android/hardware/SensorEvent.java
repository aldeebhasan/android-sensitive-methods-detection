package android.hardware;

public class SensorEvent
{
    public int accuracy;
    public Sensor sensor;
    public long timestamp;
    public final float[] values;
    
    SensorEvent() {
        this.values = null;
        throw new RuntimeException("Stub!");
    }
}
