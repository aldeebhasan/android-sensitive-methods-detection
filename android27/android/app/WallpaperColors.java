package android.app;

import android.os.*;
import android.graphics.drawable.*;
import android.graphics.*;

public final class WallpaperColors implements Parcelable
{
    public static final Creator<WallpaperColors> CREATOR;
    
    public WallpaperColors(final Parcel parcel) {
        throw new RuntimeException("Stub!");
    }
    
    public WallpaperColors(final Color primaryColor, final Color secondaryColor, final Color tertiaryColor) {
        throw new RuntimeException("Stub!");
    }
    
    public static WallpaperColors fromDrawable(final Drawable drawable) {
        throw new RuntimeException("Stub!");
    }
    
    public static WallpaperColors fromBitmap(final Bitmap bitmap) {
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
    
    public Color getPrimaryColor() {
        throw new RuntimeException("Stub!");
    }
    
    public Color getSecondaryColor() {
        throw new RuntimeException("Stub!");
    }
    
    public Color getTertiaryColor() {
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
    
    static {
        CREATOR = null;
    }
}
