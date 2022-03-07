package android.provider;

import android.net.*;

public static final class Draft implements BaseColumns, TextBasedSmsColumns
{
    public static final Uri CONTENT_URI;
    public static final String DEFAULT_SORT_ORDER = "date DESC";
    
    Draft() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CONTENT_URI = null;
    }
}
