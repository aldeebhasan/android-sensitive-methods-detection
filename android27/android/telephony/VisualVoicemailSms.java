package android.telephony;

import android.telecom.*;
import android.os.*;

public final class VisualVoicemailSms implements Parcelable
{
    public static final Creator<VisualVoicemailSms> CREATOR;
    
    VisualVoicemailSms() {
        throw new RuntimeException("Stub!");
    }
    
    public PhoneAccountHandle getPhoneAccountHandle() {
        throw new RuntimeException("Stub!");
    }
    
    public String getPrefix() {
        throw new RuntimeException("Stub!");
    }
    
    public Bundle getFields() {
        throw new RuntimeException("Stub!");
    }
    
    public String getMessageBody() {
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
