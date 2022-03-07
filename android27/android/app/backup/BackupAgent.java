package android.app.backup;

import android.content.*;
import android.os.*;
import java.io.*;

public abstract class BackupAgent extends ContextWrapper
{
    public static final int TYPE_DIRECTORY = 2;
    public static final int TYPE_FILE = 1;
    
    public BackupAgent() {
        super(null);
        throw new RuntimeException("Stub!");
    }
    
    public void onCreate() {
        throw new RuntimeException("Stub!");
    }
    
    public void onDestroy() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract void onBackup(final ParcelFileDescriptor p0, final BackupDataOutput p1, final ParcelFileDescriptor p2) throws IOException;
    
    public abstract void onRestore(final BackupDataInput p0, final int p1, final ParcelFileDescriptor p2) throws IOException;
    
    public void onFullBackup(final FullBackupDataOutput data) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    public void onQuotaExceeded(final long backupDataBytes, final long quotaBytes) {
        throw new RuntimeException("Stub!");
    }
    
    public final void fullBackupFile(final File file, final FullBackupDataOutput output) {
        throw new RuntimeException("Stub!");
    }
    
    public void onRestoreFile(final ParcelFileDescriptor data, final long size, final File destination, final int type, final long mode, final long mtime) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    public void onRestoreFinished() {
        throw new RuntimeException("Stub!");
    }
}
