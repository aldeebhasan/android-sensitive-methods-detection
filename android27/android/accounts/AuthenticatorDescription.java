package android.accounts;

import android.os.*;

public class AuthenticatorDescription implements Parcelable
{
    public static final Creator<AuthenticatorDescription> CREATOR;
    public final int accountPreferencesId;
    public final boolean customTokens;
    public final int iconId;
    public final int labelId;
    public final String packageName;
    public final int smallIconId;
    public final String type;
    
    public AuthenticatorDescription(final String type, final String packageName, final int labelId, final int iconId, final int smallIconId, final int prefId, final boolean customTokens) {
        throw new RuntimeException("Stub!");
    }
    
    public AuthenticatorDescription(final String type, final String packageName, final int labelId, final int iconId, final int smallIconId, final int prefId) {
        throw new RuntimeException("Stub!");
    }
    
    public static AuthenticatorDescription newKey(final String type) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int describeContents() {
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
    public String toString() {
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
