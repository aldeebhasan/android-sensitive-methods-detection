package android.net.wifi.aware;

import android.os.*;
import java.util.*;

public final class PublishConfig implements Parcelable
{
    public static final Creator<PublishConfig> CREATOR;
    public static final int PUBLISH_TYPE_SOLICITED = 1;
    public static final int PUBLISH_TYPE_UNSOLICITED = 0;
    
    PublishConfig() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
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
    public int hashCode() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
    
    public static final class Builder
    {
        public Builder() {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setServiceName(final String serviceName) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setServiceSpecificInfo(final byte[] serviceSpecificInfo) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setMatchFilter(final List<byte[]> matchFilter) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setPublishType(final int publishType) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setTtlSec(final int ttlSec) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setTerminateNotificationEnabled(final boolean enable) {
            throw new RuntimeException("Stub!");
        }
        
        public PublishConfig build() {
            throw new RuntimeException("Stub!");
        }
    }
}
