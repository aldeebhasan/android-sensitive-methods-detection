package android.provider;

import android.net.*;

public static final class DisplayPhoto
{
    public static final Uri CONTENT_MAX_DIMENSIONS_URI;
    public static final Uri CONTENT_URI;
    public static final String DISPLAY_MAX_DIM = "display_max_dim";
    public static final String THUMBNAIL_MAX_DIM = "thumbnail_max_dim";
    
    DisplayPhoto() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CONTENT_MAX_DIMENSIONS_URI = null;
        CONTENT_URI = null;
    }
}
