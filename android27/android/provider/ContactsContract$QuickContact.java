package android.provider;

import android.content.*;
import android.view.*;
import android.net.*;
import android.graphics.*;

public static final class QuickContact
{
    public static final String ACTION_QUICK_CONTACT = "android.provider.action.QUICK_CONTACT";
    public static final String EXTRA_EXCLUDE_MIMES = "android.provider.extra.EXCLUDE_MIMES";
    public static final String EXTRA_MODE = "android.provider.extra.MODE";
    public static final String EXTRA_PRIORITIZED_MIMETYPE = "android.provider.extra.PRIORITIZED_MIMETYPE";
    public static final int MODE_LARGE = 3;
    public static final int MODE_MEDIUM = 2;
    public static final int MODE_SMALL = 1;
    
    public QuickContact() {
        throw new RuntimeException("Stub!");
    }
    
    public static void showQuickContact(final Context context, final View target, final Uri lookupUri, final int mode, final String[] excludeMimes) {
        throw new RuntimeException("Stub!");
    }
    
    public static void showQuickContact(final Context context, final Rect target, final Uri lookupUri, final int mode, final String[] excludeMimes) {
        throw new RuntimeException("Stub!");
    }
    
    public static void showQuickContact(final Context context, final View target, final Uri lookupUri, final String[] excludeMimes, final String prioritizedMimeType) {
        throw new RuntimeException("Stub!");
    }
    
    public static void showQuickContact(final Context context, final Rect target, final Uri lookupUri, final String[] excludeMimes, final String prioritizedMimeType) {
        throw new RuntimeException("Stub!");
    }
}
