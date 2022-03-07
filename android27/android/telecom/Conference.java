package android.telecom;

import java.util.*;
import android.os.*;

public abstract class Conference extends Conferenceable
{
    public static final long CONNECT_TIME_NOT_SPECIFIED = 0L;
    
    public Conference(final PhoneAccountHandle phoneAccount) {
        throw new RuntimeException("Stub!");
    }
    
    public final PhoneAccountHandle getPhoneAccountHandle() {
        throw new RuntimeException("Stub!");
    }
    
    public final List<Connection> getConnections() {
        throw new RuntimeException("Stub!");
    }
    
    public final int getState() {
        throw new RuntimeException("Stub!");
    }
    
    public final int getConnectionCapabilities() {
        throw new RuntimeException("Stub!");
    }
    
    public final int getConnectionProperties() {
        throw new RuntimeException("Stub!");
    }
    
    public final CallAudioState getCallAudioState() {
        throw new RuntimeException("Stub!");
    }
    
    public Connection.VideoProvider getVideoProvider() {
        throw new RuntimeException("Stub!");
    }
    
    public int getVideoState() {
        throw new RuntimeException("Stub!");
    }
    
    public void onDisconnect() {
        throw new RuntimeException("Stub!");
    }
    
    public void onSeparate(final Connection connection) {
        throw new RuntimeException("Stub!");
    }
    
    public void onMerge(final Connection connection) {
        throw new RuntimeException("Stub!");
    }
    
    public void onHold() {
        throw new RuntimeException("Stub!");
    }
    
    public void onUnhold() {
        throw new RuntimeException("Stub!");
    }
    
    public void onMerge() {
        throw new RuntimeException("Stub!");
    }
    
    public void onSwap() {
        throw new RuntimeException("Stub!");
    }
    
    public void onPlayDtmfTone(final char c) {
        throw new RuntimeException("Stub!");
    }
    
    public void onStopDtmfTone() {
        throw new RuntimeException("Stub!");
    }
    
    public void onCallAudioStateChanged(final CallAudioState state) {
        throw new RuntimeException("Stub!");
    }
    
    public void onConnectionAdded(final Connection connection) {
        throw new RuntimeException("Stub!");
    }
    
    public final void setOnHold() {
        throw new RuntimeException("Stub!");
    }
    
    public final void setDialing() {
        throw new RuntimeException("Stub!");
    }
    
    public final void setActive() {
        throw new RuntimeException("Stub!");
    }
    
    public final void setDisconnected(final DisconnectCause disconnectCause) {
        throw new RuntimeException("Stub!");
    }
    
    public final DisconnectCause getDisconnectCause() {
        throw new RuntimeException("Stub!");
    }
    
    public final void setConnectionCapabilities(final int connectionCapabilities) {
        throw new RuntimeException("Stub!");
    }
    
    public final void setConnectionProperties(final int connectionProperties) {
        throw new RuntimeException("Stub!");
    }
    
    public final boolean addConnection(final Connection connection) {
        throw new RuntimeException("Stub!");
    }
    
    public final void removeConnection(final Connection connection) {
        throw new RuntimeException("Stub!");
    }
    
    public final void setConferenceableConnections(final List<Connection> conferenceableConnections) {
        throw new RuntimeException("Stub!");
    }
    
    public final void setVideoState(final Connection c, final int videoState) {
        throw new RuntimeException("Stub!");
    }
    
    public final void setVideoProvider(final Connection c, final Connection.VideoProvider videoProvider) {
        throw new RuntimeException("Stub!");
    }
    
    public final List<Connection> getConferenceableConnections() {
        throw new RuntimeException("Stub!");
    }
    
    public final void destroy() {
        throw new RuntimeException("Stub!");
    }
    
    public final void setConnectionTime(final long connectionTimeMillis) {
        throw new RuntimeException("Stub!");
    }
    
    public final long getConnectionTime() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
    
    public final void setStatusHints(final StatusHints statusHints) {
        throw new RuntimeException("Stub!");
    }
    
    public final StatusHints getStatusHints() {
        throw new RuntimeException("Stub!");
    }
    
    public final void setExtras(final Bundle extras) {
        throw new RuntimeException("Stub!");
    }
    
    public final void putExtras(final Bundle extras) {
        throw new RuntimeException("Stub!");
    }
    
    public final void removeExtras(final List<String> keys) {
        throw new RuntimeException("Stub!");
    }
    
    public final void removeExtras(final String... keys) {
        throw new RuntimeException("Stub!");
    }
    
    public final Bundle getExtras() {
        throw new RuntimeException("Stub!");
    }
    
    public void onExtrasChanged(final Bundle extras) {
        throw new RuntimeException("Stub!");
    }
}
