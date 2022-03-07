package android.accounts;

import android.os.*;

public class Account implements Parcelable
{
    public static final Creator<Account> CREATOR;
    public final String name;
    public final String type;
    
    public Account(final String name, final String type) {
        throw new RuntimeException("Stub!");
    }
    
    public Account(final Parcel in) {
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
    public void writeToParcel(final Parcel dest, final int flags) {
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
