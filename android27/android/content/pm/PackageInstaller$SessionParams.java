package android.content.pm;

import android.graphics.*;
import android.net.*;
import android.os.*;

public static class SessionParams implements Parcelable
{
    public static final Creator<SessionParams> CREATOR;
    public static final int MODE_FULL_INSTALL = 1;
    public static final int MODE_INHERIT_EXISTING = 2;
    
    public SessionParams(final int mode) {
        throw new RuntimeException("Stub!");
    }
    
    public void setInstallLocation(final int installLocation) {
        throw new RuntimeException("Stub!");
    }
    
    public void setSize(final long sizeBytes) {
        throw new RuntimeException("Stub!");
    }
    
    public void setAppPackageName(final String appPackageName) {
        throw new RuntimeException("Stub!");
    }
    
    public void setAppIcon(final Bitmap appIcon) {
        throw new RuntimeException("Stub!");
    }
    
    public void setAppLabel(final CharSequence appLabel) {
        throw new RuntimeException("Stub!");
    }
    
    public void setOriginatingUri(final Uri originatingUri) {
        throw new RuntimeException("Stub!");
    }
    
    public void setOriginatingUid(final int originatingUid) {
        throw new RuntimeException("Stub!");
    }
    
    public void setReferrerUri(final Uri referrerUri) {
        throw new RuntimeException("Stub!");
    }
    
    public void setInstallReason(final int installReason) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int describeContents() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
}
