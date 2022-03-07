package android.provider;

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
