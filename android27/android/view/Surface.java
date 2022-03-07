package android.view;

import android.graphics.*;
import android.os.*;

public class Surface implements Parcelable
{
    public static final Creator<Surface> CREATOR;
    public static final int ROTATION_0 = 0;
    public static final int ROTATION_180 = 2;
    public static final int ROTATION_270 = 3;
    public static final int ROTATION_90 = 1;
    
    public Surface(final SurfaceTexture surfaceTexture) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void finalize() throws Throwable {
        throw new RuntimeException("Stub!");
    }
    
    public void release() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isValid() {
        throw new RuntimeException("Stub!");
    }
    
    public Canvas lockCanvas(final Rect inOutDirty) throws OutOfResourcesException, IllegalArgumentException {
        throw new RuntimeException("Stub!");
    }
    
    public void unlockCanvasAndPost(final Canvas canvas) {
        throw new RuntimeException("Stub!");
    }
    
    public Canvas lockHardwareCanvas() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void unlockCanvas(final Canvas canvas) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int describeContents() {
        throw new RuntimeException("Stub!");
    }
    
    public void readFromParcel(final Parcel source) {
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
    
    public static class OutOfResourcesException extends RuntimeException
    {
        public OutOfResourcesException() {
            throw new RuntimeException("Stub!");
        }
        
        public OutOfResourcesException(final String name) {
            throw new RuntimeException("Stub!");
        }
    }
}
