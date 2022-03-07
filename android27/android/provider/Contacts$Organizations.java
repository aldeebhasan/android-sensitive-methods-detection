package android.provider;

import android.net.*;
import android.content.*;

@Deprecated
public static final class Organizations implements BaseColumns, OrganizationColumns
{
    @Deprecated
    public static final String CONTENT_DIRECTORY = "organizations";
    @Deprecated
    public static final Uri CONTENT_URI;
    @Deprecated
    public static final String DEFAULT_SORT_ORDER = "company, title, isprimary ASC";
    
    Organizations() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public static final CharSequence getDisplayLabel(final Context context, final int type, final CharSequence label) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CONTENT_URI = null;
    }
}
