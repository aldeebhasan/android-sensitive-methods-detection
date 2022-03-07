package android.telecom;

import android.net.*;
import java.util.*;
import android.graphics.drawable.*;
import android.os.*;

public final class PhoneAccount implements Parcelable
{
    public static final int CAPABILITY_CALL_PROVIDER = 2;
    public static final int CAPABILITY_CALL_SUBJECT = 64;
    public static final int CAPABILITY_CONNECTION_MANAGER = 1;
    public static final int CAPABILITY_PLACE_EMERGENCY_CALLS = 16;
    public static final int CAPABILITY_RTT = 4096;
    public static final int CAPABILITY_SELF_MANAGED = 2048;
    public static final int CAPABILITY_SIM_SUBSCRIPTION = 4;
    public static final int CAPABILITY_SUPPORTS_VIDEO_CALLING = 1024;
    public static final int CAPABILITY_VIDEO_CALLING = 8;
    public static final int CAPABILITY_VIDEO_CALLING_RELIES_ON_PRESENCE = 256;
    public static final Creator<PhoneAccount> CREATOR;
    public static final String EXTRA_CALL_SUBJECT_CHARACTER_ENCODING = "android.telecom.extra.CALL_SUBJECT_CHARACTER_ENCODING";
    public static final String EXTRA_CALL_SUBJECT_MAX_LENGTH = "android.telecom.extra.CALL_SUBJECT_MAX_LENGTH";
    public static final int NO_HIGHLIGHT_COLOR = 0;
    public static final int NO_RESOURCE_ID = -1;
    public static final String SCHEME_SIP = "sip";
    public static final String SCHEME_TEL = "tel";
    public static final String SCHEME_VOICEMAIL = "voicemail";
    
    PhoneAccount() {
        throw new RuntimeException("Stub!");
    }
    
    public static Builder builder(final PhoneAccountHandle accountHandle, final CharSequence label) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder toBuilder() {
        throw new RuntimeException("Stub!");
    }
    
    public PhoneAccountHandle getAccountHandle() {
        throw new RuntimeException("Stub!");
    }
    
    public Uri getAddress() {
        throw new RuntimeException("Stub!");
    }
    
    public Uri getSubscriptionAddress() {
        throw new RuntimeException("Stub!");
    }
    
    public int getCapabilities() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean hasCapabilities(final int capability) {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence getLabel() {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence getShortDescription() {
        throw new RuntimeException("Stub!");
    }
    
    public List<String> getSupportedUriSchemes() {
        throw new RuntimeException("Stub!");
    }
    
    public Bundle getExtras() {
        throw new RuntimeException("Stub!");
    }
    
    public Icon getIcon() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isEnabled() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean supportsUriScheme(final String uriScheme) {
        throw new RuntimeException("Stub!");
    }
    
    public int getHighlightColor() {
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
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
    
    public static class Builder
    {
        public Builder(final PhoneAccountHandle accountHandle, final CharSequence label) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder(final PhoneAccount phoneAccount) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setAddress(final Uri value) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setSubscriptionAddress(final Uri value) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setCapabilities(final int value) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setIcon(final Icon icon) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setHighlightColor(final int value) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setShortDescription(final CharSequence value) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder addSupportedUriScheme(final String uriScheme) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setSupportedUriSchemes(final List<String> uriSchemes) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setExtras(final Bundle extras) {
            throw new RuntimeException("Stub!");
        }
        
        public PhoneAccount build() {
            throw new RuntimeException("Stub!");
        }
    }
}
