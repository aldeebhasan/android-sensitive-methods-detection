package android.companion;

import android.os.*;

public final class AssociationRequest implements Parcelable
{
    public static final Creator<AssociationRequest> CREATOR;
    
    AssociationRequest() {
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
    public String toString() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int describeContents() {
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
        
        public Builder setSingleDevice(final boolean singleDevice) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder addDeviceFilter(final DeviceFilter<?> deviceFilter) {
            throw new RuntimeException("Stub!");
        }
        
        public AssociationRequest build() {
            throw new RuntimeException("Stub!");
        }
    }
}
