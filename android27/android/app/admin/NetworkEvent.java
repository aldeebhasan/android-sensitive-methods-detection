package android.app.admin;

import android.os.*;

public abstract class NetworkEvent implements Parcelable
{
    public static final Creator<NetworkEvent> CREATOR;
    
    NetworkEvent() {
        throw new RuntimeException("Stub!");
    }
    
    public String getPackageName() {
        throw new RuntimeException("Stub!");
    }
    
    public long getTimestamp() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int describeContents() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public abstract void writeToParcel(final Parcel p0, final int p1);
    
    static {
        CREATOR = null;
    }
}
