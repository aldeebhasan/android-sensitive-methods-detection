package android.content;

import android.accounts.*;
import android.os.*;

public class PeriodicSync implements Parcelable
{
    public static final Creator<PeriodicSync> CREATOR;
    public final Account account;
    public final String authority;
    public final Bundle extras;
    public final long period;
    
    public PeriodicSync(final Account account, final String authority, final Bundle extras, final long periodInSeconds) {
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
    
    @Override
    public boolean equals(final Object o) {
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
