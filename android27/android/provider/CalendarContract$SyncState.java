package android.provider;

import android.net.*;

public static final class SyncState implements SyncStateContract.Columns
{
    public static final Uri CONTENT_URI;
    
    SyncState() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CONTENT_URI = null;
    }
}
