package android.provider;

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
