package android.provider;

import android.content.*;
import android.os.*;
import android.content.pm.*;
import android.graphics.*;
import android.net.*;
import android.graphics.fonts.*;

public class FontsContract
{
    FontsContract() {
        throw new RuntimeException("Stub!");
    }
    
    public static void requestFonts(final Context context, final FontRequest request, final Handler handler, final CancellationSignal cancellationSignal, final FontRequestCallback callback) {
        throw new RuntimeException("Stub!");
    }
    
    public static FontFamilyResult fetchFonts(final Context context, final CancellationSignal cancellationSignal, final FontRequest request) throws PackageManager.NameNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public static Typeface buildTypeface(final Context context, final CancellationSignal cancellationSignal, final FontInfo[] fonts) {
        throw new RuntimeException("Stub!");
    }
    
    public static final class Columns implements BaseColumns
    {
        public static final String FILE_ID = "file_id";
        public static final String ITALIC = "font_italic";
        public static final String RESULT_CODE = "result_code";
        public static final int RESULT_CODE_FONT_NOT_FOUND = 1;
        public static final int RESULT_CODE_FONT_UNAVAILABLE = 2;
        public static final int RESULT_CODE_MALFORMED_QUERY = 3;
        public static final int RESULT_CODE_OK = 0;
        public static final String TTC_INDEX = "font_ttc_index";
        public static final String VARIATION_SETTINGS = "font_variation_settings";
        public static final String WEIGHT = "font_weight";
        
        Columns() {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static class FontInfo
    {
        FontInfo() {
            throw new RuntimeException("Stub!");
        }
        
        public Uri getUri() {
            throw new RuntimeException("Stub!");
        }
        
        public int getTtcIndex() {
            throw new RuntimeException("Stub!");
        }
        
        public FontVariationAxis[] getAxes() {
            throw new RuntimeException("Stub!");
        }
        
        public int getWeight() {
            throw new RuntimeException("Stub!");
        }
        
        public boolean isItalic() {
            throw new RuntimeException("Stub!");
        }
        
        public int getResultCode() {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static class FontFamilyResult
    {
        public static final int STATUS_OK = 0;
        public static final int STATUS_REJECTED = 3;
        public static final int STATUS_UNEXPECTED_DATA_PROVIDED = 2;
        public static final int STATUS_WRONG_CERTIFICATES = 1;
        
        FontFamilyResult() {
            throw new RuntimeException("Stub!");
        }
        
        public int getStatusCode() {
            throw new RuntimeException("Stub!");
        }
        
        public FontInfo[] getFonts() {
            throw new RuntimeException("Stub!");
        }
    }
    
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
}
