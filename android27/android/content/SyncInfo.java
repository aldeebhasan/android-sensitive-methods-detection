package android.content;

import android.accounts.*;
import android.os.*;

public class SyncInfo implements Parcelable
{
    public final Account account;
    public final String authority;
    public final long startTime;
    
    SyncInfo() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int describeContents() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void writeToParcel(final Parcel parcel, final int flags) {
        throw new RuntimeException("Stub!");
    }
}
