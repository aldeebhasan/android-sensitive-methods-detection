package android.media;

public interface OnClientUpdateListener
{
    void onClientChange(final boolean p0);
    
    void onClientPlaybackStateUpdate(final int p0);
    
    void onClientPlaybackStateUpdate(final int p0, final long p1, final long p2, final float p3);
    
    void onClientTransportControlUpdate(final int p0);
    
    void onClientMetadataUpdate(final MetadataEditor p0);
}
