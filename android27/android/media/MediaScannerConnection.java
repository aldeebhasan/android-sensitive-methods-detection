package android.media;

import android.content.*;
import android.os.*;
import android.net.*;

public class MediaScannerConnection implements ServiceConnection
{
    public MediaScannerConnection(final Context context, final MediaScannerConnectionClient client) {
        throw new RuntimeException("Stub!");
    }
    
    public void connect() {
        throw new RuntimeException("Stub!");
    }
    
    public void disconnect() {
        throw new RuntimeException("Stub!");
    }
    
    public synchronized boolean isConnected() {
        throw new RuntimeException("Stub!");
    }
    
    public void scanFile(final String path, final String mimeType) {
        throw new RuntimeException("Stub!");
    }
    
    public static void scanFile(final Context context, final String[] paths, final String[] mimeTypes, final OnScanCompletedListener callback) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onServiceConnected(final ComponentName className, final IBinder service) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onServiceDisconnected(final ComponentName className) {
        throw new RuntimeException("Stub!");
    }
    
    public interface MediaScannerConnectionClient extends OnScanCompletedListener
    {
        void onMediaScannerConnected();
        
        void onScanCompleted(final String p0, final Uri p1);
    }
    
    public interface OnScanCompletedListener
    {
        void onScanCompleted(final String p0, final Uri p1);
    }
}
