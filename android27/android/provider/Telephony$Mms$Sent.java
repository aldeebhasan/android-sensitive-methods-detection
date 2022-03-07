package android.provider;

import android.net.*;

public static final class Sent implements BaseMmsColumns
{
    public static final Uri CONTENT_URI;
    public static final String DEFAULT_SORT_ORDER = "date DESC";
    
    Sent() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CONTENT_URI = null;
    }
}
