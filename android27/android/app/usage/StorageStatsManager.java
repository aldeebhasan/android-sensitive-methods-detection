package android.app.usage;

import java.util.*;
import java.io.*;
import android.os.*;
import android.content.pm.*;

public class StorageStatsManager
{
    StorageStatsManager() {
        throw new RuntimeException("Stub!");
    }
    
    public long getTotalBytes(final UUID storageUuid) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    public long getFreeBytes(final UUID storageUuid) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    public StorageStats queryStatsForPackage(final UUID storageUuid, final String packageName, final UserHandle user) throws PackageManager.NameNotFoundException, IOException {
        throw new RuntimeException("Stub!");
    }
    
    public StorageStats queryStatsForUid(final UUID storageUuid, final int uid) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    public StorageStats queryStatsForUser(final UUID storageUuid, final UserHandle user) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    public ExternalStorageStats queryExternalStatsForUser(final UUID storageUuid, final UserHandle user) throws IOException {
        throw new RuntimeException("Stub!");
    }
}
