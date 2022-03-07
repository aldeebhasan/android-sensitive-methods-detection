package android.accounts;

import android.os.*;

public class AccountAuthenticatorResponse implements Parcelable
{
    public static final Creator<AccountAuthenticatorResponse> CREATOR;
    
    public AccountAuthenticatorResponse(final Parcel parcel) {
        throw new RuntimeException("Stub!");
    }
    
    public void onResult(final Bundle result) {
        throw new RuntimeException("Stub!");
    }
    
    public void onRequestContinued() {
        throw new RuntimeException("Stub!");
    }
    
    public void onError(final int errorCode, final String errorMessage) {
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
