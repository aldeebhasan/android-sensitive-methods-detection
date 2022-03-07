package android.telecom;

import java.util.*;
import android.os.*;

public abstract static class Callback
{
    public Callback() {
        throw new RuntimeException("Stub!");
    }
    
    public void onStateChanged(final Call call, final int state) {
        throw new RuntimeException("Stub!");
    }
    
    public void onParentChanged(final Call call, final Call parent) {
        throw new RuntimeException("Stub!");
    }
    
    public void onChildrenChanged(final Call call, final List<Call> children) {
        throw new RuntimeException("Stub!");
    }
    
    public void onDetailsChanged(final Call call, final Details details) {
        throw new RuntimeException("Stub!");
    }
    
    public void onCannedTextResponsesLoaded(final Call call, final List<String> cannedTextResponses) {
        throw new RuntimeException("Stub!");
    }
    
    public void onPostDialWait(final Call call, final String remainingPostDialSequence) {
        throw new RuntimeException("Stub!");
    }
    
    public void onVideoCallChanged(final Call call, final InCallService.VideoCall videoCall) {
        throw new RuntimeException("Stub!");
    }
    
    public void onCallDestroyed(final Call call) {
        throw new RuntimeException("Stub!");
    }
    
    public void onConferenceableCallsChanged(final Call call, final List<Call> conferenceableCalls) {
        throw new RuntimeException("Stub!");
    }
    
    public void onConnectionEvent(final Call call, final String event, final Bundle extras) {
        throw new RuntimeException("Stub!");
    }
    
    public void onRttModeChanged(final Call call, final int mode) {
        throw new RuntimeException("Stub!");
    }
    
    public void onRttStatusChanged(final Call call, final boolean enabled, final RttCall rttCall) {
        throw new RuntimeException("Stub!");
    }
    
    public void onRttRequest(final Call call, final int id) {
        throw new RuntimeException("Stub!");
    }
    
    public void onRttInitiationFailure(final Call call, final int reason) {
        throw new RuntimeException("Stub!");
    }
}
