package android.provider;

public static final class Document
{
    public static final String COLUMN_DISPLAY_NAME = "_display_name";
    public static final String COLUMN_DOCUMENT_ID = "document_id";
    public static final String COLUMN_FLAGS = "flags";
    public static final String COLUMN_ICON = "icon";
    public static final String COLUMN_LAST_MODIFIED = "last_modified";
    public static final String COLUMN_MIME_TYPE = "mime_type";
    public static final String COLUMN_SIZE = "_size";
    public static final String COLUMN_SUMMARY = "summary";
    public static final int FLAG_DIR_PREFERS_GRID = 16;
    public static final int FLAG_DIR_PREFERS_LAST_MODIFIED = 32;
    public static final int FLAG_DIR_SUPPORTS_CREATE = 8;
    public static final int FLAG_SUPPORTS_COPY = 128;
    public static final int FLAG_SUPPORTS_DELETE = 4;
    public static final int FLAG_SUPPORTS_MOVE = 256;
    public static final int FLAG_SUPPORTS_REMOVE = 1024;
    public static final int FLAG_SUPPORTS_RENAME = 64;
    public static final int FLAG_SUPPORTS_SETTINGS = 2048;
    public static final int FLAG_SUPPORTS_THUMBNAIL = 1;
    public static final int FLAG_SUPPORTS_WRITE = 2;
    public static final int FLAG_VIRTUAL_DOCUMENT = 512;
    public static final int FLAG_WEB_LINKABLE = 4096;
    public static final String MIME_TYPE_DIR = "vnd.android.document/directory";
    
    Document() {
        throw new RuntimeException("Stub!");
    }
}
