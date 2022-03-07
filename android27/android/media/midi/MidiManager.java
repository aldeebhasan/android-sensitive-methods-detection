package android.media.midi;

import android.os.*;
import android.bluetooth.*;

public final class MidiManager
{
    MidiManager() {
        throw new RuntimeException("Stub!");
    }
    
    public void registerDeviceCallback(final DeviceCallback callback, final Handler handler) {
        throw new RuntimeException("Stub!");
    }
    
    public void unregisterDeviceCallback(final DeviceCallback callback) {
        throw new RuntimeException("Stub!");
    }
    
    public MidiDeviceInfo[] getDevices() {
        throw new RuntimeException("Stub!");
    }
    
    public void openDevice(final MidiDeviceInfo deviceInfo, final OnDeviceOpenedListener listener, final Handler handler) {
        throw new RuntimeException("Stub!");
    }
    
    public void openBluetoothDevice(final BluetoothDevice bluetoothDevice, final OnDeviceOpenedListener listener, final Handler handler) {
        throw new RuntimeException("Stub!");
    }
    
    public static class DeviceCallback
    {
        public DeviceCallback() {
            throw new RuntimeException("Stub!");
        }
        
        public void onDeviceAdded(final MidiDeviceInfo device) {
            throw new RuntimeException("Stub!");
        }
        
        public void onDeviceRemoved(final MidiDeviceInfo device) {
            throw new RuntimeException("Stub!");
        }
        
        public void onDeviceStatusChanged(final MidiDeviceStatus status) {
            throw new RuntimeException("Stub!");
        }
    }
    
    public interface OnDeviceOpenedListener
    {
        void onDeviceOpened(final MidiDevice p0);
    }
}
