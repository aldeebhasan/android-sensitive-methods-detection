package android.telephony;

import java.util.*;
import android.os.*;

public final class VisualVoicemailSmsFilterSettings implements Parcelable
{
    public static final Creator<VisualVoicemailSmsFilterSettings> CREATOR;
    public static final int DESTINATION_PORT_ANY = -1;
    public static final int DESTINATION_PORT_DATA_SMS = -2;
    public final String clientPrefix;
    public final int destinationPort;
    public final List<String> originatingNumbers;
    
    VisualVoicemailSmsFilterSettings() {
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
    
    public static class Builder
    {
        public Builder() {
            throw new RuntimeException("Stub!");
        }
        
        public VisualVoicemailSmsFilterSettings build() {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setClientPrefix(final String clientPrefix) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setOriginatingNumbers(final List<String> originatingNumbers) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setDestinationPort(final int destinationPort) {
            throw new RuntimeException("Stub!");
        }
    }
}
