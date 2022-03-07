package android.provider;

import android.net.*;

public static final class ExtendedProperties implements BaseColumns, ExtendedPropertiesColumns, EventsColumns
{
    public static final Uri CONTENT_URI;
    
    ExtendedProperties() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CONTENT_URI = null;
    }
}
