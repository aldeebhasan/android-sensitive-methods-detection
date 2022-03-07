package android.bluetooth;

public abstract class BluetoothGattCallback
{
    public BluetoothGattCallback() {
        throw new RuntimeException("Stub!");
    }
    
    public void onPhyUpdate(final BluetoothGatt gatt, final int txPhy, final int rxPhy, final int status) {
        throw new RuntimeException("Stub!");
    }
    
    public void onPhyRead(final BluetoothGatt gatt, final int txPhy, final int rxPhy, final int status) {
        throw new RuntimeException("Stub!");
    }
    
    public void onConnectionStateChange(final BluetoothGatt gatt, final int status, final int newState) {
        throw new RuntimeException("Stub!");
    }
    
    public void onServicesDiscovered(final BluetoothGatt gatt, final int status) {
        throw new RuntimeException("Stub!");
    }
    
    public void onCharacteristicRead(final BluetoothGatt gatt, final BluetoothGattCharacteristic characteristic, final int status) {
        throw new RuntimeException("Stub!");
    }
    
    public void onCharacteristicWrite(final BluetoothGatt gatt, final BluetoothGattCharacteristic characteristic, final int status) {
        throw new RuntimeException("Stub!");
    }
    
    public void onCharacteristicChanged(final BluetoothGatt gatt, final BluetoothGattCharacteristic characteristic) {
        throw new RuntimeException("Stub!");
    }
    
    public void onDescriptorRead(final BluetoothGatt gatt, final BluetoothGattDescriptor descriptor, final int status) {
        throw new RuntimeException("Stub!");
    }
    
    public void onDescriptorWrite(final BluetoothGatt gatt, final BluetoothGattDescriptor descriptor, final int status) {
        throw new RuntimeException("Stub!");
    }
    
    public void onReliableWriteCompleted(final BluetoothGatt gatt, final int status) {
        throw new RuntimeException("Stub!");
    }
    
    public void onReadRemoteRssi(final BluetoothGatt gatt, final int rssi, final int status) {
        throw new RuntimeException("Stub!");
    }
    
    public void onMtuChanged(final BluetoothGatt gatt, final int mtu, final int status) {
        throw new RuntimeException("Stub!");
    }
}
