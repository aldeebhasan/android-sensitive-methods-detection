package android.app.admin;

import android.os.*;

public static final class SecurityEvent implements Parcelable
{
    public static final Creator<SecurityEvent> CREATOR;
    
    SecurityEvent() {
        throw new RuntimeException("Stub!");
    }
    
    public long getTimeNanos() {
        throw new RuntimeException("Stub!");
    }
    
    public int getTag() {
        throw new RuntimeException("Stub!");
    }
    
    public Object getData() {
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
