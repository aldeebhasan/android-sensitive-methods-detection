package android.provider;

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
