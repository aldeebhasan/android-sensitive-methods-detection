package android.app;

import android.os.*;
import android.util.*;
import java.util.*;
import android.graphics.*;

public final class PictureInPictureParams implements Parcelable
{
    public static final Creator<PictureInPictureParams> CREATOR;
    
    PictureInPictureParams() {
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
    
    public static class Builder
    {
        public Builder() {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setAspectRatio(final Rational aspectRatio) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setActions(final List<RemoteAction> actions) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setSourceRectHint(final Rect launchBounds) {
            throw new RuntimeException("Stub!");
        }
        
        public PictureInPictureParams build() {
            throw new RuntimeException("Stub!");
        }
    }
}
