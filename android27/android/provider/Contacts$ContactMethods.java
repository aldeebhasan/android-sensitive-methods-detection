package android.provider;

import android.net.*;
import android.content.*;

@Deprecated
public static final class ContactMethods implements BaseColumns, ContactMethodsColumns, PeopleColumns
{
    @Deprecated
    public static final String CONTENT_EMAIL_ITEM_TYPE = "vnd.android.cursor.item/email";
    @Deprecated
    public static final String CONTENT_EMAIL_TYPE = "vnd.android.cursor.dir/email";
    @Deprecated
    public static final Uri CONTENT_EMAIL_URI;
    @Deprecated
    public static final String CONTENT_IM_ITEM_TYPE = "vnd.android.cursor.item/jabber-im";
    @Deprecated
    public static final String CONTENT_POSTAL_ITEM_TYPE = "vnd.android.cursor.item/postal-address";
    @Deprecated
    public static final String CONTENT_POSTAL_TYPE = "vnd.android.cursor.dir/postal-address";
    @Deprecated
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/contact-methods";
    @Deprecated
    public static final Uri CONTENT_URI;
    @Deprecated
    public static final String DEFAULT_SORT_ORDER = "name ASC";
    @Deprecated
    public static final String PERSON_ID = "person";
    @Deprecated
    public static final String POSTAL_LOCATION_LATITUDE = "data";
    @Deprecated
    public static final String POSTAL_LOCATION_LONGITUDE = "aux_data";
    @Deprecated
    public static final int PROTOCOL_AIM = 0;
    @Deprecated
    public static final int PROTOCOL_GOOGLE_TALK = 5;
    @Deprecated
    public static final int PROTOCOL_ICQ = 6;
    @Deprecated
    public static final int PROTOCOL_JABBER = 7;
    @Deprecated
    public static final int PROTOCOL_MSN = 1;
    @Deprecated
    public static final int PROTOCOL_QQ = 4;
    @Deprecated
    public static final int PROTOCOL_SKYPE = 3;
    @Deprecated
    public static final int PROTOCOL_YAHOO = 2;
    
    ContactMethods() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public static String encodePredefinedImProtocol(final int protocol) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public static String encodeCustomImProtocol(final String protocolString) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public static Object decodeImProtocol(final String encodedString) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public static final CharSequence getDisplayLabel(final Context context, final int kind, final int type, final CharSequence label) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void addPostalLocation(final Context context, final long postalId, final double latitude, final double longitude) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CONTENT_EMAIL_URI = null;
        CONTENT_URI = null;
    }
}
