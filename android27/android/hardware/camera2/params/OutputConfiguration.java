package android.hardware.camera2.params;

import android.view.*;
import android.util.*;
import java.util.*;
import android.os.*;

public final class OutputConfiguration implements Parcelable
{
    public static final Creator<OutputConfiguration> CREATOR;
    public static final int SURFACE_GROUP_ID_NONE = -1;
    
    public OutputConfiguration(final Surface surface) {
        throw new RuntimeException("Stub!");
    }
    
    public OutputConfiguration(final int surfaceGroupId, final Surface surface) {
        throw new RuntimeException("Stub!");
    }
    
    public OutputConfiguration(final Size surfaceSize, final Class<T> klass) {
        throw new RuntimeException("Stub!");
    }
    
    public void enableSurfaceSharing() {
        throw new RuntimeException("Stub!");
    }
    
    public void addSurface(final Surface surface) {
        throw new RuntimeException("Stub!");
    }
    
    public Surface getSurface() {
        throw new RuntimeException("Stub!");
    }
    
    public List<Surface> getSurfaces() {
        throw new RuntimeException("Stub!");
    }
    
    public int getSurfaceGroupId() {
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
}
