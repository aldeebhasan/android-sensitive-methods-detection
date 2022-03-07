package android.provider;

import android.net.*;

public static final class PhoneLookup implements BaseColumns, PhoneLookupColumns, ContactsColumns, ContactOptionsColumns, ContactNameColumns
{
    public static final Uri CONTENT_FILTER_URI;
    public static final Uri ENTERPRISE_CONTENT_FILTER_URI;
    public static final String QUERY_PARAMETER_SIP_ADDRESS = "sip";
    
    PhoneLookup() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CONTENT_FILTER_URI = null;
        ENTERPRISE_CONTENT_FILTER_URI = null;
    }
}
