package android.media;

import android.content.*;
import android.os.*;
import android.view.*;

@Deprecated
public final class RemoteController
{
    public static final int POSITION_SYNCHRONIZATION_CHECK = 1;
    public static final int POSITION_SYNCHRONIZATION_NONE = 0;
    
    public RemoteController(final Context context, final OnClientUpdateListener updateListener) throws IllegalArgumentException {
        throw new RuntimeException("Stub!");
    }
    
    public RemoteController(final Context context, final OnClientUpdateListener updateListener, final Looper looper) throws IllegalArgumentException {
        throw new RuntimeException("Stub!");
    }
    
    public long getEstimatedMediaPosition() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean sendMediaKeyEvent(final KeyEvent keyEvent) throws IllegalArgumentException {
        throw new RuntimeException("Stub!");
    }
    
    public boolean seekTo(final long timeMs) throws IllegalArgumentException {
        throw new RuntimeException("Stub!");
    }
    
    public boolean setArtworkConfiguration(final int width, final int height) throws IllegalArgumentException {
        throw new RuntimeException("Stub!");
    }
    
    public boolean clearArtworkConfiguration() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean setSynchronizationMode(final int sync) throws IllegalArgumentException {
        throw new RuntimeException("Stub!");
    }
    
    public MetadataEditor editMetadata() {
        throw new RuntimeException("Stub!");
    }
    
    public class MetadataEditor extends MediaMetadataEditor
    {
        MetadataEditor() {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public synchronized void apply() {
            throw new RuntimeException("Stub!");
        }
    }
    
    public interface OnClientUpdateListener
    {
        void onClientChange(final boolean p0);
        
        void onClientPlaybackStateUpdate(final int p0);
        
        void onClientPlaybackStateUpdate(final int p0, final long p1, final long p2, final float p3);
        
        void onClientTransportControlUpdate(final int p0);
        
        void onClientMetadataUpdate(final MetadataEditor p0);
    }
}
