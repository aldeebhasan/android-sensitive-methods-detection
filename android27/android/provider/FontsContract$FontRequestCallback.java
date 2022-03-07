package android.provider;

import android.graphics.*;

public static class FontRequestCallback
{
    public static final int FAIL_REASON_FONT_LOAD_ERROR = -3;
    public static final int FAIL_REASON_FONT_NOT_FOUND = 1;
    public static final int FAIL_REASON_FONT_UNAVAILABLE = 2;
    public static final int FAIL_REASON_MALFORMED_QUERY = 3;
    public static final int FAIL_REASON_PROVIDER_NOT_FOUND = -1;
    public static final int FAIL_REASON_WRONG_CERTIFICATES = -2;
    
    public FontRequestCallback() {
        throw new RuntimeException("Stub!");
    }
    
    public void onTypefaceRetrieved(final Typeface typeface) {
        throw new RuntimeException("Stub!");
    }
    
    public void onTypefaceRequestFailed(final int reason) {
        throw new RuntimeException("Stub!");
    }
}
