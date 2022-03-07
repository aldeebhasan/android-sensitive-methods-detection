package android.bluetooth;

import android.os.*;
import java.util.*;

public final class BluetoothHealth implements BluetoothProfile
{
    public static final int APP_CONFIG_REGISTRATION_FAILURE = 1;
    public static final int APP_CONFIG_REGISTRATION_SUCCESS = 0;
    public static final int APP_CONFIG_UNREGISTRATION_FAILURE = 3;
    public static final int APP_CONFIG_UNREGISTRATION_SUCCESS = 2;
    public static final int CHANNEL_TYPE_RELIABLE = 10;
    public static final int CHANNEL_TYPE_STREAMING = 11;
    public static final int SINK_ROLE = 2;
    public static final int SOURCE_ROLE = 1;
    public static final int STATE_CHANNEL_CONNECTED = 2;
    public static final int STATE_CHANNEL_CONNECTING = 1;
    public static final int STATE_CHANNEL_DISCONNECTED = 0;
    public static final int STATE_CHANNEL_DISCONNECTING = 3;
    
    BluetoothHealth() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean registerSinkAppConfiguration(final String name, final int dataType, final BluetoothHealthCallback callback) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean unregisterAppConfiguration(final BluetoothHealthAppConfiguration config) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean connectChannelToSource(final BluetoothDevice device, final BluetoothHealthAppConfiguration config) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean disconnectChannel(final BluetoothDevice device, final BluetoothHealthAppConfiguration config, final int channelId) {
        throw new RuntimeException("Stub!");
    }
    
    public ParcelFileDescriptor getMainChannelFd(final BluetoothDevice device, final BluetoothHealthAppConfiguration config) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int getConnectionState(final BluetoothDevice device) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public List<BluetoothDevice> getConnectedDevices() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public List<BluetoothDevice> getDevicesMatchingConnectionStates(final int[] states) {
        throw new RuntimeException("Stub!");
    }
}
