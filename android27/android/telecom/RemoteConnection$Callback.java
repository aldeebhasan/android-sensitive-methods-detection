package android.telecom;

import android.net.*;
import java.util.*;
import android.os.*;

public abstract static class Callback
{
    public Callback() {
        throw new RuntimeException("Stub!");
    }
    
    public void onStateChanged(final RemoteConnection connection, final int state) {
        throw new RuntimeException("Stub!");
    }
    
    public void onDisconnected(final RemoteConnection connection, final DisconnectCause disconnectCause) {
        throw new RuntimeException("Stub!");
    }
    
    public void onRingbackRequested(final RemoteConnection connection, final boolean ringback) {
        throw new RuntimeException("Stub!");
    }
    
    public void onConnectionCapabilitiesChanged(final RemoteConnection connection, final int connectionCapabilities) {
        throw new RuntimeException("Stub!");
    }
    
    public void onConnectionPropertiesChanged(final RemoteConnection connection, final int connectionProperties) {
        throw new RuntimeException("Stub!");
    }
    
    public void onPostDialWait(final RemoteConnection connection, final String remainingPostDialSequence) {
        throw new RuntimeException("Stub!");
    }
    
    public void onPostDialChar(final RemoteConnection connection, final char nextChar) {
        throw new RuntimeException("Stub!");
    }
    
    public void onVoipAudioChanged(final RemoteConnection connection, final boolean isVoip) {
        throw new RuntimeException("Stub!");
    }
    
    public void onStatusHintsChanged(final RemoteConnection connection, final StatusHints statusHints) {
        throw new RuntimeException("Stub!");
    }
    
    public void onAddressChanged(final RemoteConnection connection, final Uri address, final int presentation) {
        throw new RuntimeException("Stub!");
    }
    
    public void onCallerDisplayNameChanged(final RemoteConnection connection, final String callerDisplayName, final int presentation) {
        throw new RuntimeException("Stub!");
    }
    
    public void onVideoStateChanged(final RemoteConnection connection, final int videoState) {
        throw new RuntimeException("Stub!");
    }
    
    public void onDestroyed(final RemoteConnection connection) {
        throw new RuntimeException("Stub!");
    }
    
    public void onConferenceableConnectionsChanged(final RemoteConnection connection, final List<RemoteConnection> conferenceableConnections) {
        throw new RuntimeException("Stub!");
    }
    
    public void onVideoProviderChanged(final RemoteConnection connection, final VideoProvider videoProvider) {
        throw new RuntimeException("Stub!");
    }
    
    public void onConferenceChanged(final RemoteConnection connection, final RemoteConference conference) {
        throw new RuntimeException("Stub!");
    }
    
    public void onExtrasChanged(final RemoteConnection connection, final Bundle extras) {
        throw new RuntimeException("Stub!");
    }
    
    public void onConnectionEvent(final RemoteConnection connection, final String event, final Bundle extras) {
        throw new RuntimeException("Stub!");
    }
}
