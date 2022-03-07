package android.mtp;

import android.hardware.usb.*;
import java.io.*;
import android.os.*;

public final class MtpDevice
{
    public MtpDevice(final UsbDevice device) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean open(final UsbDeviceConnection connection) {
        throw new RuntimeException("Stub!");
    }
    
    public void close() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void finalize() throws Throwable {
        throw new RuntimeException("Stub!");
    }
    
    public String getDeviceName() {
        throw new RuntimeException("Stub!");
    }
    
    public int getDeviceId() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
    
    public MtpDeviceInfo getDeviceInfo() {
        throw new RuntimeException("Stub!");
    }
    
    public int[] getStorageIds() {
        throw new RuntimeException("Stub!");
    }
    
    public int[] getObjectHandles(final int storageId, final int format, final int objectHandle) {
        throw new RuntimeException("Stub!");
    }
    
    public byte[] getObject(final int objectHandle, final int objectSize) {
        throw new RuntimeException("Stub!");
    }
    
    public long getPartialObject(final int objectHandle, final long offset, final long size, final byte[] buffer) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    public long getPartialObject64(final int objectHandle, final long offset, final long size, final byte[] buffer) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    public byte[] getThumbnail(final int objectHandle) {
        throw new RuntimeException("Stub!");
    }
    
    public MtpStorageInfo getStorageInfo(final int storageId) {
        throw new RuntimeException("Stub!");
    }
    
    public MtpObjectInfo getObjectInfo(final int objectHandle) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean deleteObject(final int objectHandle) {
        throw new RuntimeException("Stub!");
    }
    
    public long getParent(final int objectHandle) {
        throw new RuntimeException("Stub!");
    }
    
    public long getStorageId(final int objectHandle) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean importFile(final int objectHandle, final String destPath) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean importFile(final int objectHandle, final ParcelFileDescriptor descriptor) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean sendObject(final int objectHandle, final long size, final ParcelFileDescriptor descriptor) {
        throw new RuntimeException("Stub!");
    }
    
    public MtpObjectInfo sendObjectInfo(final MtpObjectInfo info) {
        throw new RuntimeException("Stub!");
    }
    
    public MtpEvent readEvent(final CancellationSignal signal) throws IOException {
        throw new RuntimeException("Stub!");
    }
}
