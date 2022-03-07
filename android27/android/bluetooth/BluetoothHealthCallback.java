package android.bluetooth;

import android.os.*;

public abstract class BluetoothHealthCallback
{
    public BluetoothHealthCallback() {
        throw new RuntimeException("Stub!");
    }
    
    public void onHealthAppConfigurationStatusChange(final BluetoothHealthAppConfiguration config, final int status) {
        throw new RuntimeException("Stub!");
    }
    
    public void onHealthChannelStateChange(final BluetoothHealthAppConfiguration config, final BluetoothDevice device, final int prevState, final int newState, final ParcelFileDescriptor fd, final int channelId) {
        throw new RuntimeException("Stub!");
    }
}
