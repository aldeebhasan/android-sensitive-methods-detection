package android.media;

public interface OnPlaybackPositionUpdateListener
{
    void onMarkerReached(final AudioTrack p0);
    
    void onPeriodicNotification(final AudioTrack p0);
}
