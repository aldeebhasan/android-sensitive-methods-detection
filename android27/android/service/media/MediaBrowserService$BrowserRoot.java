package android.service.media;

import android.os.*;

public static final class BrowserRoot
{
    public static final String EXTRA_OFFLINE = "android.service.media.extra.OFFLINE";
    public static final String EXTRA_RECENT = "android.service.media.extra.RECENT";
    public static final String EXTRA_SUGGESTED = "android.service.media.extra.SUGGESTED";
    
    public BrowserRoot(final String rootId, final Bundle extras) {
        throw new RuntimeException("Stub!");
    }
    
    public String getRootId() {
        throw new RuntimeException("Stub!");
    }
    
    public Bundle getExtras() {
        throw new RuntimeException("Stub!");
    }
}
