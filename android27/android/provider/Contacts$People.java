package android.provider;

import android.net.*;
import android.database.*;
import java.io.*;
import android.content.*;
import android.graphics.*;

@Deprecated
public static final class People implements BaseColumns, PeopleColumns, PhonesColumns, PresenceColumns
{
    @Deprecated
    public static final Uri CONTENT_FILTER_URI;
    @Deprecated
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/person";
    @Deprecated
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/person";
    @Deprecated
    public static final Uri CONTENT_URI;
    @Deprecated
    public static final String DEFAULT_SORT_ORDER = "name ASC";
    @Deprecated
    public static final Uri DELETED_CONTENT_URI;
    @Deprecated
    public static final String PRIMARY_EMAIL_ID = "primary_email";
    @Deprecated
    public static final String PRIMARY_ORGANIZATION_ID = "primary_organization";
    @Deprecated
    public static final String PRIMARY_PHONE_ID = "primary_phone";
    
    People() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public static void markAsContacted(final ContentResolver resolver, final long personId) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public static Uri addToMyContactsGroup(final ContentResolver resolver, final long personId) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public static Uri addToGroup(final ContentResolver resolver, final long personId, final String groupName) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public static Uri addToGroup(final ContentResolver resolver, final long personId, final long groupId) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public static Uri createPersonInMyContactsGroup(final ContentResolver resolver, final ContentValues values) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public static Cursor queryGroups(final ContentResolver resolver, final long person) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public static void setPhotoData(final ContentResolver cr, final Uri person, final byte[] data) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public static InputStream openContactPhotoInputStream(final ContentResolver cr, final Uri person) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public static Bitmap loadContactPhoto(final Context context, final Uri person, final int placeholderImageResource, final BitmapFactory.Options options) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CONTENT_FILTER_URI = null;
        CONTENT_URI = null;
        DELETED_CONTENT_URI = null;
    }
    
    @Deprecated
    public static final class Phones implements BaseColumns, PhonesColumns, PeopleColumns
    {
        @Deprecated
        public static final String CONTENT_DIRECTORY = "phones";
        @Deprecated
        public static final String DEFAULT_SORT_ORDER = "number ASC";
        
        Phones() {
            throw new RuntimeException("Stub!");
        }
    }
    
    @Deprecated
    public static final class ContactMethods implements BaseColumns, ContactMethodsColumns, PeopleColumns
    {
        @Deprecated
        public static final String CONTENT_DIRECTORY = "contact_methods";
        @Deprecated
        public static final String DEFAULT_SORT_ORDER = "data ASC";
        
        ContactMethods() {
            throw new RuntimeException("Stub!");
        }
    }
    
    @Deprecated
    public static class Extensions implements BaseColumns, ExtensionsColumns
    {
        @Deprecated
        public static final String CONTENT_DIRECTORY = "extensions";
        @Deprecated
        public static final String DEFAULT_SORT_ORDER = "name ASC";
        @Deprecated
        public static final String PERSON_ID = "person";
        
        Extensions() {
            throw new RuntimeException("Stub!");
        }
    }
}
