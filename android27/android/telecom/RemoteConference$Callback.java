package android.telecom;

import java.util.*;
import android.os.*;

public abstract static class Callback
{
    public Callback() {
        throw new RuntimeException("Stub!");
    }
    
    public void onStateChanged(final RemoteConference conference, final int oldState, final int newState) {
        throw new RuntimeException("Stub!");
    }
    
    public void onDisconnected(final RemoteConference conference, final DisconnectCause disconnectCause) {
        throw new RuntimeException("Stub!");
    }
    
    public void onConnectionAdded(final RemoteConference conference, final RemoteConnection connection) {
        throw new RuntimeException("Stub!");
    }
    
    public void onConnectionRemoved(final RemoteConference conference, final RemoteConnection connection) {
        throw new RuntimeException("Stub!");
    }
    
    public void onConnectionCapabilitiesChanged(final RemoteConference conference, final int connectionCapabilities) {
        throw new RuntimeException("Stub!");
    }
    
    public void onConnectionPropertiesChanged(final RemoteConference conference, final int connectionProperties) {
        throw new RuntimeException("Stub!");
    }
    
    public void onConferenceableConnectionsChanged(final RemoteConference conference, final List<RemoteConnection> conferenceableConnections) {
        throw new RuntimeException("Stub!");
    }
    
    public void onDestroyed(final RemoteConference conference) {
        throw new RuntimeException("Stub!");
    }
    
    public void onExtrasChanged(final RemoteConference conference, final Bundle extras) {
        throw new RuntimeException("Stub!");
    }
}
