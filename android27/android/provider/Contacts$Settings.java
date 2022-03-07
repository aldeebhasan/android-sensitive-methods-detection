package android.provider;

import android.net.*;
import android.content.*;

@Deprecated
public static final class Settings implements BaseColumns, SettingsColumns
{
    @Deprecated
    public static final String CONTENT_DIRECTORY = "settings";
    @Deprecated
    public static final Uri CONTENT_URI;
    @Deprecated
    public static final String DEFAULT_SORT_ORDER = "key ASC";
    @Deprecated
    public static final String SYNC_EVERYTHING = "syncEverything";
    
    Settings() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public static String getSetting(final ContentResolver cr, final String account, final String key) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public static void setSetting(final ContentResolver cr, final String account, final String key, final String value) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CONTENT_URI = null;
    }
}
