package android.provider;

import android.net.*;

public static final class Settings implements SettingsColumns
{
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/setting";
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/setting";
    public static final Uri CONTENT_URI;
    
    Settings() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CONTENT_URI = null;
    }
}
