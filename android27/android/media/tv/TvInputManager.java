package android.media.tv;

import java.util.*;
import android.os.*;

public final class TvInputManager
{
    public static final String ACTION_BLOCKED_RATINGS_CHANGED = "android.media.tv.action.BLOCKED_RATINGS_CHANGED";
    public static final String ACTION_PARENTAL_CONTROLS_ENABLED_CHANGED = "android.media.tv.action.PARENTAL_CONTROLS_ENABLED_CHANGED";
    public static final String ACTION_QUERY_CONTENT_RATING_SYSTEMS = "android.media.tv.action.QUERY_CONTENT_RATING_SYSTEMS";
    public static final String ACTION_SETUP_INPUTS = "android.media.tv.action.SETUP_INPUTS";
    public static final String ACTION_VIEW_RECORDING_SCHEDULES = "android.media.tv.action.VIEW_RECORDING_SCHEDULES";
    public static final int INPUT_STATE_CONNECTED = 0;
    public static final int INPUT_STATE_CONNECTED_STANDBY = 1;
    public static final int INPUT_STATE_DISCONNECTED = 2;
    public static final String META_DATA_CONTENT_RATING_SYSTEMS = "android.media.tv.metadata.CONTENT_RATING_SYSTEMS";
    public static final int RECORDING_ERROR_INSUFFICIENT_SPACE = 1;
    public static final int RECORDING_ERROR_RESOURCE_BUSY = 2;
    public static final int RECORDING_ERROR_UNKNOWN = 0;
    public static final long TIME_SHIFT_INVALID_TIME = Long.MIN_VALUE;
    public static final int TIME_SHIFT_STATUS_AVAILABLE = 3;
    public static final int TIME_SHIFT_STATUS_UNAVAILABLE = 2;
    public static final int TIME_SHIFT_STATUS_UNKNOWN = 0;
    public static final int TIME_SHIFT_STATUS_UNSUPPORTED = 1;
    public static final int VIDEO_UNAVAILABLE_REASON_AUDIO_ONLY = 4;
    public static final int VIDEO_UNAVAILABLE_REASON_BUFFERING = 3;
    public static final int VIDEO_UNAVAILABLE_REASON_TUNING = 1;
    public static final int VIDEO_UNAVAILABLE_REASON_UNKNOWN = 0;
    public static final int VIDEO_UNAVAILABLE_REASON_WEAK_SIGNAL = 2;
    
    TvInputManager() {
        throw new RuntimeException("Stub!");
    }
    
    public List<TvInputInfo> getTvInputList() {
        throw new RuntimeException("Stub!");
    }
    
    public TvInputInfo getTvInputInfo(final String inputId) {
        throw new RuntimeException("Stub!");
    }
    
    public void updateTvInputInfo(final TvInputInfo inputInfo) {
        throw new RuntimeException("Stub!");
    }
    
    public int getInputState(final String inputId) {
        throw new RuntimeException("Stub!");
    }
    
    public void registerCallback(final TvInputCallback callback, final Handler handler) {
        throw new RuntimeException("Stub!");
    }
    
    public void unregisterCallback(final TvInputCallback callback) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isParentalControlsEnabled() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isRatingBlocked(final TvContentRating rating) {
        throw new RuntimeException("Stub!");
    }
    
    public abstract static class TvInputCallback
    {
        public TvInputCallback() {
            throw new RuntimeException("Stub!");
        }
        
        public void onInputStateChanged(final String inputId, final int state) {
            throw new RuntimeException("Stub!");
        }
        
        public void onInputAdded(final String inputId) {
            throw new RuntimeException("Stub!");
        }
        
        public void onInputRemoved(final String inputId) {
            throw new RuntimeException("Stub!");
        }
        
        public void onInputUpdated(final String inputId) {
            throw new RuntimeException("Stub!");
        }
        
        public void onTvInputInfoUpdated(final TvInputInfo inputInfo) {
            throw new RuntimeException("Stub!");
        }
    }
}
