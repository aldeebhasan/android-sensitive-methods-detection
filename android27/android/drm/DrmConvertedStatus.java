package android.drm;

public class DrmConvertedStatus
{
    public static final int STATUS_ERROR = 3;
    public static final int STATUS_INPUTDATA_ERROR = 2;
    public static final int STATUS_OK = 1;
    public final byte[] convertedData;
    public final int offset;
    public final int statusCode;
    
    public DrmConvertedStatus(final int statusCode, final byte[] convertedData, final int offset) {
        this.convertedData = null;
        throw new RuntimeException("Stub!");
    }
}
