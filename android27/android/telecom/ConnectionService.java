package android.telecom;

import android.app.*;
import android.content.*;
import android.os.*;
import java.util.*;

public abstract class ConnectionService extends Service
{
    public static final String SERVICE_INTERFACE = "android.telecom.ConnectionService";
    
    public ConnectionService() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public final IBinder onBind(final Intent intent) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean onUnbind(final Intent intent) {
        throw new RuntimeException("Stub!");
    }
    
    public final RemoteConnection createRemoteIncomingConnection(final PhoneAccountHandle connectionManagerPhoneAccount, final ConnectionRequest request) {
        throw new RuntimeException("Stub!");
    }
    
    public final RemoteConnection createRemoteOutgoingConnection(final PhoneAccountHandle connectionManagerPhoneAccount, final ConnectionRequest request) {
        throw new RuntimeException("Stub!");
    }
    
    public final void conferenceRemoteConnections(final RemoteConnection remoteConnection1, final RemoteConnection remoteConnection2) {
        throw new RuntimeException("Stub!");
    }
    
    public final void addConference(final Conference conference) {
        throw new RuntimeException("Stub!");
    }
    
    public final void addExistingConnection(final PhoneAccountHandle phoneAccountHandle, final Connection connection) {
        throw new RuntimeException("Stub!");
    }
    
    public final Collection<Connection> getAllConnections() {
        throw new RuntimeException("Stub!");
    }
    
    public final Collection<Conference> getAllConferences() {
        throw new RuntimeException("Stub!");
    }
    
    public Connection onCreateIncomingConnection(final PhoneAccountHandle connectionManagerPhoneAccount, final ConnectionRequest request) {
        throw new RuntimeException("Stub!");
    }
    
    public void onCreateIncomingConnectionFailed(final PhoneAccountHandle connectionManagerPhoneAccount, final ConnectionRequest request) {
        throw new RuntimeException("Stub!");
    }
    
    public void onCreateOutgoingConnectionFailed(final PhoneAccountHandle connectionManagerPhoneAccount, final ConnectionRequest request) {
        throw new RuntimeException("Stub!");
    }
    
    public Connection onCreateOutgoingConnection(final PhoneAccountHandle connectionManagerPhoneAccount, final ConnectionRequest request) {
        throw new RuntimeException("Stub!");
    }
    
    public void onConference(final Connection connection1, final Connection connection2) {
        throw new RuntimeException("Stub!");
    }
    
    public void onRemoteConferenceAdded(final RemoteConference conference) {
        throw new RuntimeException("Stub!");
    }
    
    public void onRemoteExistingConnectionAdded(final RemoteConnection connection) {
        throw new RuntimeException("Stub!");
    }
}
