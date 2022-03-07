package android.media.browse;

import android.media.*;
import android.os.*;

public static class MediaItem implements Parcelable
{
    public static final Creator<MediaItem> CREATOR;
    public static final int FLAG_BROWSABLE = 1;
    public static final int FLAG_PLAYABLE = 2;
    
    public MediaItem(final MediaDescription description, final int flags) {
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
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
    
    public int getFlags() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isBrowsable() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isPlayable() {
        throw new RuntimeException("Stub!");
    }
    
    public MediaDescription getDescription() {
        throw new RuntimeException("Stub!");
    }
    
    public String getMediaId() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
}
