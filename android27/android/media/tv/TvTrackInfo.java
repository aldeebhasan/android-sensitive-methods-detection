package android.media.tv;

import android.os.*;

public final class TvTrackInfo implements Parcelable
{
    public static final Creator<TvTrackInfo> CREATOR;
    public static final int TYPE_AUDIO = 0;
    public static final int TYPE_SUBTITLE = 2;
    public static final int TYPE_VIDEO = 1;
    
    TvTrackInfo() {
        throw new RuntimeException("Stub!");
    }
    
    public final int getType() {
        throw new RuntimeException("Stub!");
    }
    
    public final String getId() {
        throw new RuntimeException("Stub!");
    }
    
    public final String getLanguage() {
        throw new RuntimeException("Stub!");
    }
    
    public final CharSequence getDescription() {
        throw new RuntimeException("Stub!");
    }
    
    public final int getAudioChannelCount() {
        throw new RuntimeException("Stub!");
    }
    
    public final int getAudioSampleRate() {
        throw new RuntimeException("Stub!");
    }
    
    public final int getVideoWidth() {
        throw new RuntimeException("Stub!");
    }
    
    public final int getVideoHeight() {
        throw new RuntimeException("Stub!");
    }
    
    public final float getVideoFrameRate() {
        throw new RuntimeException("Stub!");
    }
    
    public final float getVideoPixelAspectRatio() {
        throw new RuntimeException("Stub!");
    }
    
    public final byte getVideoActiveFormatDescription() {
        throw new RuntimeException("Stub!");
    }
    
    public final Bundle getExtra() {
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
    public int hashCode() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
    
    public static final class Builder
    {
        public Builder(final int type, final String id) {
            throw new RuntimeException("Stub!");
        }
        
        public final Builder setLanguage(final String language) {
            throw new RuntimeException("Stub!");
        }
        
        public final Builder setDescription(final CharSequence description) {
            throw new RuntimeException("Stub!");
        }
        
        public final Builder setAudioChannelCount(final int audioChannelCount) {
            throw new RuntimeException("Stub!");
        }
        
        public final Builder setAudioSampleRate(final int audioSampleRate) {
            throw new RuntimeException("Stub!");
        }
        
        public final Builder setVideoWidth(final int videoWidth) {
            throw new RuntimeException("Stub!");
        }
        
        public final Builder setVideoHeight(final int videoHeight) {
            throw new RuntimeException("Stub!");
        }
        
        public final Builder setVideoFrameRate(final float videoFrameRate) {
            throw new RuntimeException("Stub!");
        }
        
        public final Builder setVideoPixelAspectRatio(final float videoPixelAspectRatio) {
            throw new RuntimeException("Stub!");
        }
        
        public final Builder setVideoActiveFormatDescription(final byte videoActiveFormatDescription) {
            throw new RuntimeException("Stub!");
        }
        
        public final Builder setExtra(final Bundle extra) {
            throw new RuntimeException("Stub!");
        }
        
        public TvTrackInfo build() {
            throw new RuntimeException("Stub!");
        }
    }
}
