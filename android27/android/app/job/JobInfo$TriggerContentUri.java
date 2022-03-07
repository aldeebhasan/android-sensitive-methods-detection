package android.app.job;

import android.net.*;
import android.os.*;

public static final class TriggerContentUri implements Parcelable
{
    public static final Creator<TriggerContentUri> CREATOR;
    public static final int FLAG_NOTIFY_FOR_DESCENDANTS = 1;
    
    public TriggerContentUri(final Uri uri, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    public Uri getUri() {
        throw new RuntimeException("Stub!");
    }
    
    public int getFlags() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean equals(final Object o) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int hashCode() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int describeContents() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void writeToParcel(final Parcel out, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
}
