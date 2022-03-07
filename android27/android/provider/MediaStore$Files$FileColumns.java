package android.provider;

public interface FileColumns extends MediaColumns
{
    public static final String MEDIA_TYPE = "media_type";
    public static final int MEDIA_TYPE_AUDIO = 2;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_NONE = 0;
    public static final int MEDIA_TYPE_PLAYLIST = 4;
    public static final int MEDIA_TYPE_VIDEO = 3;
    public static final String MIME_TYPE = "mime_type";
    public static final String PARENT = "parent";
    public static final String TITLE = "title";
}
