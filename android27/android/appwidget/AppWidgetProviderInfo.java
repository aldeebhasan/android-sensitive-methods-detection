package android.appwidget;

import android.content.pm.*;
import android.content.*;
import android.graphics.drawable.*;
import android.os.*;

public class AppWidgetProviderInfo implements Parcelable
{
    public static final Creator<AppWidgetProviderInfo> CREATOR;
    public static final int RESIZE_BOTH = 3;
    public static final int RESIZE_HORIZONTAL = 1;
    public static final int RESIZE_NONE = 0;
    public static final int RESIZE_VERTICAL = 2;
    public static final int WIDGET_CATEGORY_HOME_SCREEN = 1;
    public static final int WIDGET_CATEGORY_KEYGUARD = 2;
    public static final int WIDGET_CATEGORY_SEARCHBOX = 4;
    public int autoAdvanceViewId;
    public ComponentName configure;
    public int icon;
    public int initialKeyguardLayout;
    public int initialLayout;
    @Deprecated
    public String label;
    public int minHeight;
    public int minResizeHeight;
    public int minResizeWidth;
    public int minWidth;
    public int previewImage;
    public ComponentName provider;
    public int resizeMode;
    public int updatePeriodMillis;
    public int widgetCategory;
    
    public AppWidgetProviderInfo() {
        throw new RuntimeException("Stub!");
    }
    
    public AppWidgetProviderInfo(final Parcel in) {
        throw new RuntimeException("Stub!");
    }
    
    public final String loadLabel(final PackageManager packageManager) {
        throw new RuntimeException("Stub!");
    }
    
    public final Drawable loadIcon(final Context context, final int density) {
        throw new RuntimeException("Stub!");
    }
    
    public final Drawable loadPreviewImage(final Context context, final int density) {
        throw new RuntimeException("Stub!");
    }
    
    public final UserHandle getProfile() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void writeToParcel(final Parcel out, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    public AppWidgetProviderInfo clone() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int describeContents() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
}
