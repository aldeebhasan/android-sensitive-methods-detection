package android.content.pm;

import android.graphics.*;
import android.content.*;
import android.net.*;
import android.os.*;

public static class SessionInfo implements Parcelable
{
    public static final Creator<SessionInfo> CREATOR;
    
    SessionInfo() {
        throw new RuntimeException("Stub!");
    }
    
    public int getSessionId() {
        throw new RuntimeException("Stub!");
    }
    
    public String getInstallerPackageName() {
        throw new RuntimeException("Stub!");
    }
    
    public float getProgress() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isActive() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isSealed() {
        throw new RuntimeException("Stub!");
    }
    
    public int getInstallReason() {
        throw new RuntimeException("Stub!");
    }
    
    public String getAppPackageName() {
        throw new RuntimeException("Stub!");
    }
    
    public Bitmap getAppIcon() {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence getAppLabel() {
        throw new RuntimeException("Stub!");
    }
    
    public Intent createDetailsIntent() {
        throw new RuntimeException("Stub!");
    }
    
    public int getMode() {
        throw new RuntimeException("Stub!");
    }
    
    public int getInstallLocation() {
        throw new RuntimeException("Stub!");
    }
    
    public long getSize() {
        throw new RuntimeException("Stub!");
    }
    
    public Uri getOriginatingUri() {
        throw new RuntimeException("Stub!");
    }
    
    public int getOriginatingUid() {
        throw new RuntimeException("Stub!");
    }
    
    public Uri getReferrerUri() {
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
