package android.telecom;

import android.content.*;
import android.os.*;

public final class PhoneAccountHandle implements Parcelable
{
    public static final Creator<PhoneAccountHandle> CREATOR;
    
    public PhoneAccountHandle(final ComponentName componentName, final String id) {
        throw new RuntimeException("Stub!");
    }
    
    public PhoneAccountHandle(final ComponentName componentName, final String id, final UserHandle userHandle) {
        throw new RuntimeException("Stub!");
    }
    
    public ComponentName getComponentName() {
        throw new RuntimeException("Stub!");
    }
    
    public String getId() {
        throw new RuntimeException("Stub!");
    }
    
    public UserHandle getUserHandle() {
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
    public boolean equals(final Object other) {
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
    
    static {
        CREATOR = null;
    }
}
