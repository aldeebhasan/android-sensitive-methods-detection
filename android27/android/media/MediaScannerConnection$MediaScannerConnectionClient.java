package android.media;

import android.net.*;

public interface MediaScannerConnectionClient extends OnScanCompletedListener
{
    void onMediaScannerConnected();
    
    void onScanCompleted(final String p0, final Uri p1);
}
