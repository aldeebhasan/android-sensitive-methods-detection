package android.media;

import android.graphics.*;
import android.net.*;
import android.os.*;

public class MediaDescription implements Parcelable
{
    public static final long BT_FOLDER_TYPE_ALBUMS = 2L;
    public static final long BT_FOLDER_TYPE_ARTISTS = 3L;
    public static final long BT_FOLDER_TYPE_GENRES = 4L;
    public static final long BT_FOLDER_TYPE_MIXED = 0L;
    public static final long BT_FOLDER_TYPE_PLAYLISTS = 5L;
    public static final long BT_FOLDER_TYPE_TITLES = 1L;
    public static final long BT_FOLDER_TYPE_YEARS = 6L;
    public static final Creator<MediaDescription> CREATOR;
    public static final String EXTRA_BT_FOLDER_TYPE = "android.media.extra.BT_FOLDER_TYPE";
    
    MediaDescription() {
        throw new RuntimeException("Stub!");
    }
    
    public String getMediaId() {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence getTitle() {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence getSubtitle() {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence getDescription() {
        throw new RuntimeException("Stub!");
    }
    
    public Bitmap getIconBitmap() {
        throw new RuntimeException("Stub!");
    }
    
    public Uri getIconUri() {
        throw new RuntimeException("Stub!");
    }
    
    public Bundle getExtras() {
        throw new RuntimeException("Stub!");
    }
    
    public Uri getMediaUri() {
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
    public String toString() {
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
        
        public Builder setMediaId(final String mediaId) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setTitle(final CharSequence title) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setSubtitle(final CharSequence subtitle) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setDescription(final CharSequence description) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setIconBitmap(final Bitmap icon) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setIconUri(final Uri iconUri) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setExtras(final Bundle extras) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setMediaUri(final Uri mediaUri) {
            throw new RuntimeException("Stub!");
        }
        
        public MediaDescription build() {
            throw new RuntimeException("Stub!");
        }
    }
}
