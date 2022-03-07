package android.bluetooth;

public interface ServiceListener
{
    void onServiceConnected(final int p0, final BluetoothProfile p1);
    
    void onServiceDisconnected(final int p0);
}
