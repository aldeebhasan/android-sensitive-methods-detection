package android.provider;

import android.net.*;

public static final class ProviderStatus
{
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/provider_status";
    public static final Uri CONTENT_URI;
    public static final String DATABASE_CREATION_TIMESTAMP = "database_creation_timestamp";
    public static final String STATUS = "status";
    public static final int STATUS_BUSY = 1;
    public static final int STATUS_EMPTY = 2;
    public static final int STATUS_NORMAL = 0;
    
    ProviderStatus() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CONTENT_URI = null;
    }
}
