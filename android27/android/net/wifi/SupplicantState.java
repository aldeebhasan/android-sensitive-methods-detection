package android.net.wifi;

import android.os.*;

public enum SupplicantState implements Parcelable
{
    ASSOCIATED, 
    ASSOCIATING, 
    AUTHENTICATING, 
    COMPLETED, 
    DISCONNECTED, 
    DORMANT, 
    FOUR_WAY_HANDSHAKE, 
    GROUP_HANDSHAKE, 
    INACTIVE, 
    INTERFACE_DISABLED, 
    INVALID, 
    SCANNING, 
    UNINITIALIZED;
    
    public static boolean isValidState(final SupplicantState state) {
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
}
