package android.media;

import android.content.*;
import android.graphics.drawable.*;
import android.view.*;

public static class RouteInfo
{
    public static final int DEVICE_TYPE_BLUETOOTH = 3;
    public static final int DEVICE_TYPE_SPEAKER = 2;
    public static final int DEVICE_TYPE_TV = 1;
    public static final int DEVICE_TYPE_UNKNOWN = 0;
    public static final int PLAYBACK_TYPE_LOCAL = 0;
    public static final int PLAYBACK_TYPE_REMOTE = 1;
    public static final int PLAYBACK_VOLUME_FIXED = 0;
    public static final int PLAYBACK_VOLUME_VARIABLE = 1;
    
    RouteInfo() {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence getName() {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence getName(final Context context) {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence getDescription() {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence getStatus() {
        throw new RuntimeException("Stub!");
    }
    
    public int getSupportedTypes() {
        throw new RuntimeException("Stub!");
    }
    
    public int getDeviceType() {
        throw new RuntimeException("Stub!");
    }
    
    public RouteGroup getGroup() {
        throw new RuntimeException("Stub!");
    }
    
    public RouteCategory getCategory() {
        throw new RuntimeException("Stub!");
    }
    
    public Drawable getIconDrawable() {
        throw new RuntimeException("Stub!");
    }
    
    public void setTag(final Object tag) {
        throw new RuntimeException("Stub!");
    }
    
    public Object getTag() {
        throw new RuntimeException("Stub!");
    }
    
    public int getPlaybackType() {
        throw new RuntimeException("Stub!");
    }
    
    public int getPlaybackStream() {
        throw new RuntimeException("Stub!");
    }
    
    public int getVolume() {
        throw new RuntimeException("Stub!");
    }
    
    public void requestSetVolume(final int volume) {
        throw new RuntimeException("Stub!");
    }
    
    public void requestUpdateVolume(final int direction) {
        throw new RuntimeException("Stub!");
    }
    
    public int getVolumeMax() {
        throw new RuntimeException("Stub!");
    }
    
    public int getVolumeHandling() {
        throw new RuntimeException("Stub!");
    }
    
    public Display getPresentationDisplay() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isEnabled() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isConnecting() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
}
