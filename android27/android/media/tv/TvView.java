package android.media.tv;

import android.content.*;
import android.util.*;
import android.net.*;
import android.os.*;
import java.util.*;
import android.media.*;
import android.graphics.*;
import android.view.*;

public class TvView extends ViewGroup
{
    public TvView(final Context context) {
        super(null, null, 0, 0);
        throw new RuntimeException("Stub!");
    }
    
    public TvView(final Context context, final AttributeSet attrs) {
        super(null, null, 0, 0);
        throw new RuntimeException("Stub!");
    }
    
    public TvView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(null, null, 0, 0);
        throw new RuntimeException("Stub!");
    }
    
    public void setCallback(final TvInputCallback callback) {
        throw new RuntimeException("Stub!");
    }
    
    public void setZOrderMediaOverlay(final boolean isMediaOverlay) {
        throw new RuntimeException("Stub!");
    }
    
    public void setZOrderOnTop(final boolean onTop) {
        throw new RuntimeException("Stub!");
    }
    
    public void setStreamVolume(final float volume) {
        throw new RuntimeException("Stub!");
    }
    
    public void tune(final String inputId, final Uri channelUri) {
        throw new RuntimeException("Stub!");
    }
    
    public void tune(final String inputId, final Uri channelUri, final Bundle params) {
        throw new RuntimeException("Stub!");
    }
    
    public void reset() {
        throw new RuntimeException("Stub!");
    }
    
    public void setCaptionEnabled(final boolean enabled) {
        throw new RuntimeException("Stub!");
    }
    
    public void selectTrack(final int type, final String trackId) {
        throw new RuntimeException("Stub!");
    }
    
    public List<TvTrackInfo> getTracks(final int type) {
        throw new RuntimeException("Stub!");
    }
    
    public String getSelectedTrack(final int type) {
        throw new RuntimeException("Stub!");
    }
    
    public void timeShiftPlay(final String inputId, final Uri recordedProgramUri) {
        throw new RuntimeException("Stub!");
    }
    
    public void timeShiftPause() {
        throw new RuntimeException("Stub!");
    }
    
    public void timeShiftResume() {
        throw new RuntimeException("Stub!");
    }
    
    public void timeShiftSeekTo(final long timeMs) {
        throw new RuntimeException("Stub!");
    }
    
    public void timeShiftSetPlaybackParams(final PlaybackParams params) {
        throw new RuntimeException("Stub!");
    }
    
    public void setTimeShiftPositionCallback(final TimeShiftPositionCallback callback) {
        throw new RuntimeException("Stub!");
    }
    
    public void sendAppPrivateCommand(final String action, final Bundle data) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean dispatchUnhandledInputEvent(final InputEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean onUnhandledInputEvent(final InputEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    public void setOnUnhandledInputEventListener(final OnUnhandledInputEventListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean dispatchKeyEvent(final KeyEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean dispatchTouchEvent(final MotionEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean dispatchTrackballEvent(final MotionEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean dispatchGenericMotionEvent(final MotionEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void dispatchWindowFocusChanged(final boolean hasFocus) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onAttachedToWindow() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onDetachedFromWindow() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onLayout(final boolean changed, final int left, final int top, final int right, final int bottom) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean gatherTransparentRegion(final Region region) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void draw(final Canvas canvas) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void dispatchDraw(final Canvas canvas) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onVisibilityChanged(final View changedView, final int visibility) {
        throw new RuntimeException("Stub!");
    }
    
    public abstract static class TimeShiftPositionCallback
    {
        public TimeShiftPositionCallback() {
            throw new RuntimeException("Stub!");
        }
        
        public void onTimeShiftStartPositionChanged(final String inputId, final long timeMs) {
            throw new RuntimeException("Stub!");
        }
        
        public void onTimeShiftCurrentPositionChanged(final String inputId, final long timeMs) {
            throw new RuntimeException("Stub!");
        }
    }
    
    public abstract static class TvInputCallback
    {
        public TvInputCallback() {
            throw new RuntimeException("Stub!");
        }
        
        public void onConnectionFailed(final String inputId) {
            throw new RuntimeException("Stub!");
        }
        
        public void onDisconnected(final String inputId) {
            throw new RuntimeException("Stub!");
        }
        
        public void onChannelRetuned(final String inputId, final Uri channelUri) {
            throw new RuntimeException("Stub!");
        }
        
        public void onTracksChanged(final String inputId, final List<TvTrackInfo> tracks) {
            throw new RuntimeException("Stub!");
        }
        
        public void onTrackSelected(final String inputId, final int type, final String trackId) {
            throw new RuntimeException("Stub!");
        }
        
        public void onVideoSizeChanged(final String inputId, final int width, final int height) {
            throw new RuntimeException("Stub!");
        }
        
        public void onVideoAvailable(final String inputId) {
            throw new RuntimeException("Stub!");
        }
        
        public void onVideoUnavailable(final String inputId, final int reason) {
            throw new RuntimeException("Stub!");
        }
        
        public void onContentAllowed(final String inputId) {
            throw new RuntimeException("Stub!");
        }
        
        public void onContentBlocked(final String inputId, final TvContentRating rating) {
            throw new RuntimeException("Stub!");
        }
        
        public void onTimeShiftStatusChanged(final String inputId, final int status) {
            throw new RuntimeException("Stub!");
        }
    }
    
    public interface OnUnhandledInputEventListener
    {
        boolean onUnhandledInputEvent(final InputEvent p0);
    }
}
