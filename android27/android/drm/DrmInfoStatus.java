package android.drm;

public class DrmInfoStatus
{
    public static final int STATUS_ERROR = 2;
    public static final int STATUS_OK = 1;
    public final ProcessedData data;
    public final int infoType;
    public final String mimeType;
    public final int statusCode;
    
    public DrmInfoStatus(final int statusCode, final int infoType, final ProcessedData data, final String mimeType) {
        throw new RuntimeException("Stub!");
    }
}
