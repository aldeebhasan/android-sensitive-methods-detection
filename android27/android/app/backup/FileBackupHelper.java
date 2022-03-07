package android.app.backup;

import android.content.*;
import android.os.*;

public class FileBackupHelper extends FileBackupHelperBase implements BackupHelper
{
    public FileBackupHelper(final Context context, final String... files) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void performBackup(final ParcelFileDescriptor oldState, final BackupDataOutput data, final ParcelFileDescriptor newState) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void restoreEntity(final BackupDataInputStream data) {
        throw new RuntimeException("Stub!");
    }
}
