package android.provider;

public static final class Entity implements BaseColumns, ContactsColumns, ContactNameColumns, RawContactsColumns, BaseSyncColumns, SyncColumns, DataColumns, StatusColumns, ContactOptionsColumns, ContactStatusColumns, DataUsageStatColumns
{
    public static final String CONTENT_DIRECTORY = "entities";
    public static final String DATA_ID = "data_id";
    public static final String RAW_CONTACT_ID = "raw_contact_id";
    
    Entity() {
        throw new RuntimeException("Stub!");
    }
}
