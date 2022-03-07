package android.provider;

public static final class Root
{
    public static final String COLUMN_AVAILABLE_BYTES = "available_bytes";
    public static final String COLUMN_CAPACITY_BYTES = "capacity_bytes";
    public static final String COLUMN_DOCUMENT_ID = "document_id";
    public static final String COLUMN_FLAGS = "flags";
    public static final String COLUMN_ICON = "icon";
    public static final String COLUMN_MIME_TYPES = "mime_types";
    public static final String COLUMN_ROOT_ID = "root_id";
    public static final String COLUMN_SUMMARY = "summary";
    public static final String COLUMN_TITLE = "title";
    public static final int FLAG_LOCAL_ONLY = 2;
    public static final int FLAG_SUPPORTS_CREATE = 1;
    public static final int FLAG_SUPPORTS_EJECT = 32;
    public static final int FLAG_SUPPORTS_IS_CHILD = 16;
    public static final int FLAG_SUPPORTS_RECENTS = 4;
    public static final int FLAG_SUPPORTS_SEARCH = 8;
    public static final String MIME_TYPE_ITEM = "vnd.android.document/root";
    
    Root() {
        throw new RuntimeException("Stub!");
    }
}
