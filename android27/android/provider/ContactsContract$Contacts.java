package android.provider;

import android.net.*;
import android.content.*;
import java.io.*;

public static class Contacts implements BaseColumns, ContactsColumns, ContactOptionsColumns, ContactNameColumns, ContactStatusColumns
{
    public static final Uri CONTENT_FILTER_URI;
    public static final Uri CONTENT_FREQUENT_URI;
    public static final Uri CONTENT_GROUP_URI;
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/contact";
    public static final Uri CONTENT_LOOKUP_URI;
    public static final Uri CONTENT_MULTI_VCARD_URI;
    public static final Uri CONTENT_STREQUENT_FILTER_URI;
    public static final Uri CONTENT_STREQUENT_URI;
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/contact";
    public static final Uri CONTENT_URI;
    public static final String CONTENT_VCARD_TYPE = "text/x-vcard";
    public static final Uri CONTENT_VCARD_URI;
    public static final Uri ENTERPRISE_CONTENT_FILTER_URI;
    public static final String EXTRA_ADDRESS_BOOK_INDEX = "android.provider.extra.ADDRESS_BOOK_INDEX";
    public static final String EXTRA_ADDRESS_BOOK_INDEX_COUNTS = "android.provider.extra.ADDRESS_BOOK_INDEX_COUNTS";
    public static final String EXTRA_ADDRESS_BOOK_INDEX_TITLES = "android.provider.extra.ADDRESS_BOOK_INDEX_TITLES";
    public static final String QUERY_PARAMETER_VCARD_NO_PHOTO = "no_photo";
    
    Contacts() {
        throw new RuntimeException("Stub!");
    }
    
    public static Uri getLookupUri(final ContentResolver resolver, final Uri contactUri) {
        throw new RuntimeException("Stub!");
    }
    
    public static Uri getLookupUri(final long contactId, final String lookupKey) {
        throw new RuntimeException("Stub!");
    }
    
    public static Uri lookupContact(final ContentResolver resolver, final Uri lookupUri) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public static void markAsContacted(final ContentResolver resolver, final long contactId) {
        throw new RuntimeException("Stub!");
    }
    
    public static boolean isEnterpriseContactId(final long contactId) {
        throw new RuntimeException("Stub!");
    }
    
    public static InputStream openContactPhotoInputStream(final ContentResolver cr, final Uri contactUri, final boolean preferHighres) {
        throw new RuntimeException("Stub!");
    }
    
    public static InputStream openContactPhotoInputStream(final ContentResolver cr, final Uri contactUri) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CONTENT_FILTER_URI = null;
        CONTENT_FREQUENT_URI = null;
        CONTENT_GROUP_URI = null;
        CONTENT_LOOKUP_URI = null;
        CONTENT_MULTI_VCARD_URI = null;
        CONTENT_STREQUENT_FILTER_URI = null;
        CONTENT_STREQUENT_URI = null;
        CONTENT_URI = null;
        CONTENT_VCARD_URI = null;
        ENTERPRISE_CONTENT_FILTER_URI = null;
    }
    
    public static final class Data implements BaseColumns, DataColumns
    {
        public static final String CONTENT_DIRECTORY = "data";
        
        Data() {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static final class Entity implements BaseColumns, ContactsColumns, ContactNameColumns, RawContactsColumns, BaseSyncColumns, SyncColumns, DataColumns, StatusColumns, ContactOptionsColumns, ContactStatusColumns, DataUsageStatColumns
    {
        public static final String CONTENT_DIRECTORY = "entities";
        public static final String DATA_ID = "data_id";
        public static final String RAW_CONTACT_ID = "raw_contact_id";
        
        Entity() {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static final class AggregationSuggestions implements BaseColumns, ContactsColumns, ContactOptionsColumns, ContactStatusColumns
    {
        public static final String CONTENT_DIRECTORY = "suggestions";
        
        AggregationSuggestions() {
            throw new RuntimeException("Stub!");
        }
        
        public static final class Builder
        {
            public Builder() {
                throw new RuntimeException("Stub!");
            }
            
            public Builder setContactId(final long contactId) {
                throw new RuntimeException("Stub!");
            }
            
            public Builder addNameParameter(final String name) {
                throw new RuntimeException("Stub!");
            }
            
            public Builder setLimit(final int limit) {
                throw new RuntimeException("Stub!");
            }
            
            public Uri build() {
                throw new RuntimeException("Stub!");
            }
        }
    }
    
    public static final class Photo implements BaseColumns, DataColumnsWithJoins
    {
        public static final String CONTENT_DIRECTORY = "photo";
        public static final String DISPLAY_PHOTO = "display_photo";
        public static final String PHOTO = "data15";
        public static final String PHOTO_FILE_ID = "data14";
        
        Photo() {
            throw new RuntimeException("Stub!");
        }
    }
}
