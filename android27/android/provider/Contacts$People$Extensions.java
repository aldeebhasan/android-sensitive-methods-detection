package android.provider;

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
