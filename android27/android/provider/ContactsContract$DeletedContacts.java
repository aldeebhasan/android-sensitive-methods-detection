package android.provider;

import android.net.*;

public static final class DeletedContacts implements DeletedContactsColumns
{
    public static final Uri CONTENT_URI;
    public static final long DAYS_KEPT_MILLISECONDS = 2592000000L;
    
    DeletedContacts() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CONTENT_URI = null;
    }
}
