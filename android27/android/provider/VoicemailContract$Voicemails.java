package android.provider;

import android.net.*;

public static final class Voicemails implements BaseColumns, OpenableColumns
{
    public static final String ARCHIVED = "archived";
    public static final String BACKED_UP = "backed_up";
    public static final Uri CONTENT_URI;
    public static final String DATE = "date";
    public static final String DELETED = "deleted";
    public static final String DIRTY = "dirty";
    public static final String DIR_TYPE = "vnd.android.cursor.dir/voicemails";
    public static final String DURATION = "duration";
    public static final String HAS_CONTENT = "has_content";
    public static final String IS_OMTP_VOICEMAIL = "is_omtp_voicemail";
    public static final String IS_READ = "is_read";
    public static final String ITEM_TYPE = "vnd.android.cursor.item/voicemail";
    public static final String LAST_MODIFIED = "last_modified";
    public static final String MIME_TYPE = "mime_type";
    public static final String NUMBER = "number";
    public static final String PHONE_ACCOUNT_COMPONENT_NAME = "subscription_component_name";
    public static final String PHONE_ACCOUNT_ID = "subscription_id";
    public static final String RESTORED = "restored";
    public static final String SOURCE_DATA = "source_data";
    public static final String SOURCE_PACKAGE = "source_package";
    public static final String TRANSCRIPTION = "transcription";
    
    Voicemails() {
        throw new RuntimeException("Stub!");
    }
    
    public static Uri buildSourceUri(final String packageName) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CONTENT_URI = null;
    }
}
