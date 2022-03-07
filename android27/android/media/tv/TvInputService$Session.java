package android.media.tv;

import android.content.*;
import android.net.*;
import java.util.*;
import android.os.*;
import android.media.*;
import android.view.*;

public abstract static class Session implements KeyEvent.Callback
{
    public Session(final Context context) {
        throw new RuntimeException("Stub!");
    }
    
    public void setOverlayViewEnabled(final boolean enable) {
        throw new RuntimeException("Stub!");
    }
    
    public void notifyChannelRetuned(final Uri channelUri) {
        throw new RuntimeException("Stub!");
    }
    
    public void notifyTracksChanged(final List<TvTrackInfo> tracks) {
        throw new RuntimeException("Stub!");
    }
    
    public void notifyTrackSelected(final int type, final String trackId) {
        throw new RuntimeException("Stub!");
    }
    
    public void notifyVideoAvailable() {
        throw new RuntimeException("Stub!");
    }
    
    public void notifyVideoUnavailable(final int reason) {
        throw new RuntimeException("Stub!");
    }
    
    public void notifyContentAllowed() {
        throw new RuntimeException("Stub!");
    }
    
    public void notifyContentBlocked(final TvContentRating rating) {
        throw new RuntimeException("Stub!");
    }
    
    public void notifyTimeShiftStatusChanged(final int status) {
        throw new RuntimeException("Stub!");
    }
    
    public void layoutSurface(final int left, final int top, final int right, final int bottom) {
        throw new RuntimeException("Stub!");
    }
    
    public abstract void onRelease();
    
    public abstract boolean onSetSurface(final Surface p0);
    
    public void onSurfaceChanged(final int format, final int width, final int height) {
        throw new RuntimeException("Stub!");
    }
    
    public void onOverlayViewSizeChanged(final int width, final int height) {
        throw new RuntimeException("Stub!");
    }
    
    public abstract void onSetStreamVolume(final float p0);
    
    public abstract boolean onTune(final Uri p0);
    
    public boolean onTune(final Uri channelUri, final Bundle params) {
        throw new RuntimeException("Stub!");
    }
    
    public abstract void onSetCaptionEnabled(final boolean p0);
    
    public void onUnblockContent(final TvContentRating unblockedRating) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean onSelectTrack(final int type, final String trackId) {
        throw new RuntimeException("Stub!");
    }
    
    public void onAppPrivateCommand(final String action, final Bundle data) {
        throw new RuntimeException("Stub!");
    }
    
    public View onCreateOverlayView() {
        throw new RuntimeException("Stub!");
    }
    
    public void onTimeShiftPlay(final Uri recordedProgramUri) {
        throw new RuntimeException("Stub!");
    }
    
    public void onTimeShiftPause() {
        throw new RuntimeException("Stub!");
    }
    
    public void onTimeShiftResume() {
        throw new RuntimeException("Stub!");
    }
    
    public void onTimeShiftSeekTo(final long timeMs) {
        throw new RuntimeException("Stub!");
    }
    
    public void onTimeShiftSetPlaybackParams(final PlaybackParams params) {
        throw new RuntimeException("Stub!");
    }
    
    public long onTimeShiftGetStartPosition() {
        throw new RuntimeException("Stub!");
    }
    
    public long onTimeShiftGetCurrentPosition() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean onKeyDown(final int keyCode, final KeyEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean onKeyLongPress(final int keyCode, final KeyEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean onKeyMultiple(final int keyCode, final int count, final KeyEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean onKeyUp(final int keyCode, final KeyEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean onTouchEvent(final MotionEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean onTrackballEvent(final MotionEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean onGenericMotionEvent(final MotionEvent event) {
        throw new RuntimeException("Stub!");
    }
}
