package android.hardware;

public abstract static class DynamicSensorCallback
{
    public DynamicSensorCallback() {
        throw new RuntimeException("Stub!");
    }
    
    public void onDynamicSensorConnected(final Sensor sensor) {
        throw new RuntimeException("Stub!");
    }
    
    public void onDynamicSensorDisconnected(final Sensor sensor) {
        throw new RuntimeException("Stub!");
    }
}
