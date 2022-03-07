package android.net;

import android.os.*;

public class NetworkRequest implements Parcelable
{
    public static final Creator<NetworkRequest> CREATOR;
    
    NetworkRequest() {
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
    
    @Override
    public boolean equals(final Object obj) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int hashCode() {
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
        
        public NetworkRequest build() {
            throw new RuntimeException("Stub!");
        }
        
        public Builder addCapability(final int capability) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder removeCapability(final int capability) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder addTransportType(final int transportType) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder removeTransportType(final int transportType) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setNetworkSpecifier(final String networkSpecifier) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setNetworkSpecifier(final NetworkSpecifier networkSpecifier) {
            throw new RuntimeException("Stub!");
        }
    }
}
