package android.os.storage;

import java.io.*;
import java.util.*;
import android.os.*;

public class StorageManager
{
    public static final String ACTION_MANAGE_STORAGE = "android.os.storage.action.MANAGE_STORAGE";
    public static final String EXTRA_REQUESTED_BYTES = "android.os.storage.extra.REQUESTED_BYTES";
    public static final String EXTRA_UUID = "android.os.storage.extra.UUID";
    public static final UUID UUID_DEFAULT;
    
    StorageManager() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean mountObb(final String rawPath, final String key, final OnObbStateChangeListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean unmountObb(final String rawPath, final boolean force, final OnObbStateChangeListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isObbMounted(final String rawPath) {
        throw new RuntimeException("Stub!");
    }
    
    public String getMountedObbPath(final String rawPath) {
        throw new RuntimeException("Stub!");
    }
    
    public UUID getUuidForPath(final File path) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isAllocationSupported(final FileDescriptor fd) {
        throw new RuntimeException("Stub!");
    }
    
    public StorageVolume getStorageVolume(final File file) {
        throw new RuntimeException("Stub!");
    }
    
    public List<StorageVolume> getStorageVolumes() {
        throw new RuntimeException("Stub!");
    }
    
    public StorageVolume getPrimaryStorageVolume() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isEncrypted(final File file) {
        throw new RuntimeException("Stub!");
    }
    
    public ParcelFileDescriptor openProxyFileDescriptor(final int mode, final ProxyFileDescriptorCallback callback, final Handler handler) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    public long getCacheQuotaBytes(final UUID storageUuid) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    public long getCacheSizeBytes(final UUID storageUuid) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    public long getAllocatableBytes(final UUID storageUuid) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    public void allocateBytes(final UUID storageUuid, final long bytes) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    public void allocateBytes(final FileDescriptor fd, final long bytes) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    public void setCacheBehaviorGroup(final File path, final boolean group) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isCacheBehaviorGroup(final File path) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    public void setCacheBehaviorTombstone(final File path, final boolean tombstone) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isCacheBehaviorTombstone(final File path) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    static {
        UUID_DEFAULT = null;
    }
}
