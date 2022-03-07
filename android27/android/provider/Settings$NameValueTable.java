package android.provider;

import android.content.*;
import android.net.*;

public static class NameValueTable implements BaseColumns
{
    public static final String NAME = "name";
    public static final String VALUE = "value";
    
    public NameValueTable() {
        throw new RuntimeException("Stub!");
    }
    
    protected static boolean putString(final ContentResolver resolver, final Uri uri, final String name, final String value) {
        throw new RuntimeException("Stub!");
    }
    
    public static Uri getUriFor(final Uri uri, final String name) {
        throw new RuntimeException("Stub!");
    }
}
