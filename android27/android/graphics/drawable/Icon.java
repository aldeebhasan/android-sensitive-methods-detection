package android.graphics.drawable;

import android.content.*;
import android.net.*;
import android.content.res.*;
import android.graphics.*;
import android.os.*;

public final class Icon implements Parcelable
{
    public static final Creator<Icon> CREATOR;
    
    Icon() {
        throw new RuntimeException("Stub!");
    }
    
    public void loadDrawableAsync(final Context context, final Message andThen) {
        throw new RuntimeException("Stub!");
    }
    
    public void loadDrawableAsync(final Context context, final OnDrawableLoadedListener listener, final Handler handler) {
        throw new RuntimeException("Stub!");
    }
    
    public Drawable loadDrawable(final Context context) {
        throw new RuntimeException("Stub!");
    }
    
    public static Icon createWithResource(final Context context, final int resId) {
        throw new RuntimeException("Stub!");
    }
    
    public static Icon createWithResource(final String resPackage, final int resId) {
        throw new RuntimeException("Stub!");
    }
    
    public static Icon createWithBitmap(final Bitmap bits) {
        throw new RuntimeException("Stub!");
    }
    
    public static Icon createWithAdaptiveBitmap(final Bitmap bits) {
        throw new RuntimeException("Stub!");
    }
    
    public static Icon createWithData(final byte[] data, final int offset, final int length) {
        throw new RuntimeException("Stub!");
    }
    
    public static Icon createWithContentUri(final String uri) {
        throw new RuntimeException("Stub!");
    }
    
    public static Icon createWithContentUri(final Uri uri) {
        throw new RuntimeException("Stub!");
    }
    
    public Icon setTint(final int tint) {
        throw new RuntimeException("Stub!");
    }
    
    public Icon setTintList(final ColorStateList tintList) {
        throw new RuntimeException("Stub!");
    }
    
    public Icon setTintMode(final PorterDuff.Mode mode) {
        throw new RuntimeException("Stub!");
    }
    
    public static Icon createWithFilePath(final String path) {
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
    
    static {
        CREATOR = null;
    }
    
    public interface OnDrawableLoadedListener
    {
        void onDrawableLoaded(final Drawable p0);
    }
}
