package android.speech;

import android.app.*;
import android.content.*;
import android.os.*;

public abstract class RecognitionService extends Service
{
    public static final String SERVICE_INTERFACE = "android.speech.RecognitionService";
    public static final String SERVICE_META_DATA = "android.speech";
    
    public RecognitionService() {
        throw new RuntimeException("Stub!");
    }
    
    protected abstract void onStartListening(final Intent p0, final Callback p1);
    
    protected abstract void onCancel(final Callback p0);
    
    protected abstract void onStopListening(final Callback p0);
    
    @Override
    public final IBinder onBind(final Intent intent) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onDestroy() {
        throw new RuntimeException("Stub!");
    }
    
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
}
