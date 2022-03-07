package android.provider;

import android.net.*;

public static final class Profile implements BaseColumns, ContactsColumns, ContactOptionsColumns, ContactNameColumns, ContactStatusColumns
{
    public static final Uri CONTENT_RAW_CONTACTS_URI;
    public static final Uri CONTENT_URI;
    public static final Uri CONTENT_VCARD_URI;
    public static final long MIN_ID = 9223372034707292160L;
    
    Profile() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CONTENT_RAW_CONTACTS_URI = null;
        CONTENT_URI = null;
        CONTENT_VCARD_URI = null;
    }
}
