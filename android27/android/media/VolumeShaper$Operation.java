package android.media;

import android.os.*;

public static final class Operation implements Parcelable
{
    public static final Creator<Operation> CREATOR;
    public static final Operation PLAY;
    public static final Operation REVERSE;
    
    Operation() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int hashCode() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean equals(final Object o) {
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
        PLAY = null;
        REVERSE = null;
    }
}
