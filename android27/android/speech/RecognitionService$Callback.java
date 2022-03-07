package android.speech;

import android.os.*;

public class Callback
{
    Callback() {
        throw new RuntimeException("Stub!");
    }
    
    public void beginningOfSpeech() throws RemoteException {
        throw new RuntimeException("Stub!");
    }
    
    public void bufferReceived(final byte[] buffer) throws RemoteException {
        throw new RuntimeException("Stub!");
    }
    
    public void endOfSpeech() throws RemoteException {
        throw new RuntimeException("Stub!");
    }
    
    public void error(final int error) throws RemoteException {
        throw new RuntimeException("Stub!");
    }
    
    public void partialResults(final Bundle partialResults) throws RemoteException {
        throw new RuntimeException("Stub!");
    }
    
    public void readyForSpeech(final Bundle params) throws RemoteException {
        throw new RuntimeException("Stub!");
    }
    
    public void results(final Bundle results) throws RemoteException {
        throw new RuntimeException("Stub!");
    }
    
    public void rmsChanged(final float rmsdB) throws RemoteException {
        throw new RuntimeException("Stub!");
    }
    
    public int getCallingUid() {
        throw new RuntimeException("Stub!");
    }
}
