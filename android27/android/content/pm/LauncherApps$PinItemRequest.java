package android.content.pm;

import android.content.*;
import android.appwidget.*;
import android.os.*;

public static final class PinItemRequest implements Parcelable
{
    public static final Creator<PinItemRequest> CREATOR;
    public static final int REQUEST_TYPE_APPWIDGET = 2;
    public static final int REQUEST_TYPE_SHORTCUT = 1;
    
    PinItemRequest() {
        throw new RuntimeException("Stub!");
    }
    
    public int getRequestType() {
        throw new RuntimeException("Stub!");
    }
    
    public ShortcutInfo getShortcutInfo() {
        throw new RuntimeException("Stub!");
    }
    
    public AppWidgetProviderInfo getAppWidgetProviderInfo(final Context context) {
        throw new RuntimeException("Stub!");
    }
    
    public Bundle getExtras() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isValid() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean accept(final Bundle options) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean accept() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int describeContents() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
}
