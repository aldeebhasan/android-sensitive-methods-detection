package android.provider;

import android.net.*;
import android.content.*;

public static final class Directory implements BaseColumns
{
    public static final String ACCOUNT_NAME = "accountName";
    public static final String ACCOUNT_TYPE = "accountType";
    public static final String CALLER_PACKAGE_PARAM_KEY = "callerPackage";
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/contact_directory";
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/contact_directories";
    public static final Uri CONTENT_URI;
    public static final long DEFAULT = 0L;
    public static final String DIRECTORY_AUTHORITY = "authority";
    public static final String DISPLAY_NAME = "displayName";
    public static final Uri ENTERPRISE_CONTENT_URI;
    public static final long ENTERPRISE_DEFAULT = 1000000000L;
    public static final long ENTERPRISE_LOCAL_INVISIBLE = 1000000001L;
    public static final String EXPORT_SUPPORT = "exportSupport";
    public static final int EXPORT_SUPPORT_ANY_ACCOUNT = 2;
    public static final int EXPORT_SUPPORT_NONE = 0;
    public static final int EXPORT_SUPPORT_SAME_ACCOUNT_ONLY = 1;
    public static final long LOCAL_INVISIBLE = 1L;
    public static final String PACKAGE_NAME = "packageName";
    public static final String PHOTO_SUPPORT = "photoSupport";
    public static final int PHOTO_SUPPORT_FULL = 3;
    public static final int PHOTO_SUPPORT_FULL_SIZE_ONLY = 2;
    public static final int PHOTO_SUPPORT_NONE = 0;
    public static final int PHOTO_SUPPORT_THUMBNAIL_ONLY = 1;
    public static final String SHORTCUT_SUPPORT = "shortcutSupport";
    public static final int SHORTCUT_SUPPORT_DATA_ITEMS_ONLY = 1;
    public static final int SHORTCUT_SUPPORT_FULL = 2;
    public static final int SHORTCUT_SUPPORT_NONE = 0;
    public static final String TYPE_RESOURCE_ID = "typeResourceId";
    
    Directory() {
        throw new RuntimeException("Stub!");
    }
    
    public static boolean isRemoteDirectoryId(final long directoryId) {
        throw new RuntimeException("Stub!");
    }
    
    public static boolean isEnterpriseDirectoryId(final long directoryId) {
        throw new RuntimeException("Stub!");
    }
    
    public static void notifyDirectoryChange(final ContentResolver resolver) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CONTENT_URI = null;
        ENTERPRISE_CONTENT_URI = null;
    }
}
