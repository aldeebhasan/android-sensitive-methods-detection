package android.provider;

import android.content.res.*;

public static final class Im implements DataColumnsWithJoins, CommonColumns
{
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/im";
    public static final String CUSTOM_PROTOCOL = "data6";
    public static final String EXTRA_ADDRESS_BOOK_INDEX = "android.provider.extra.ADDRESS_BOOK_INDEX";
    public static final String EXTRA_ADDRESS_BOOK_INDEX_COUNTS = "android.provider.extra.ADDRESS_BOOK_INDEX_COUNTS";
    public static final String EXTRA_ADDRESS_BOOK_INDEX_TITLES = "android.provider.extra.ADDRESS_BOOK_INDEX_TITLES";
    public static final String PROTOCOL = "data5";
    public static final int PROTOCOL_AIM = 0;
    public static final int PROTOCOL_CUSTOM = -1;
    public static final int PROTOCOL_GOOGLE_TALK = 5;
    public static final int PROTOCOL_ICQ = 6;
    public static final int PROTOCOL_JABBER = 7;
    public static final int PROTOCOL_MSN = 1;
    public static final int PROTOCOL_NETMEETING = 8;
    public static final int PROTOCOL_QQ = 4;
    public static final int PROTOCOL_SKYPE = 3;
    public static final int PROTOCOL_YAHOO = 2;
    public static final int TYPE_HOME = 1;
    public static final int TYPE_OTHER = 3;
    public static final int TYPE_WORK = 2;
    
    Im() {
        throw new RuntimeException("Stub!");
    }
    
    public static final int getTypeLabelResource(final int type) {
        throw new RuntimeException("Stub!");
    }
    
    public static final CharSequence getTypeLabel(final Resources res, final int type, final CharSequence label) {
        throw new RuntimeException("Stub!");
    }
    
    public static final int getProtocolLabelResource(final int type) {
        throw new RuntimeException("Stub!");
    }
    
    public static final CharSequence getProtocolLabel(final Resources res, final int type, final CharSequence label) {
        throw new RuntimeException("Stub!");
    }
}
