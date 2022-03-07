package android.media;

import android.os.*;

public static class TrackInfo implements Parcelable
{
    public static final int MEDIA_TRACK_TYPE_AUDIO = 2;
    public static final int MEDIA_TRACK_TYPE_METADATA = 5;
    public static final int MEDIA_TRACK_TYPE_SUBTITLE = 4;
    public static final int MEDIA_TRACK_TYPE_TIMEDTEXT = 3;
    public static final int MEDIA_TRACK_TYPE_UNKNOWN = 0;
    public static final int MEDIA_TRACK_TYPE_VIDEO = 1;
    
    TrackInfo() {
        throw new RuntimeException("Stub!");
    }
    
    public int getTrackType() {
        throw new RuntimeException("Stub!");
    }
    
    public String getLanguage() {
        throw new RuntimeException("Stub!");
    }
    
    public MediaFormat getFormat() {
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
}
