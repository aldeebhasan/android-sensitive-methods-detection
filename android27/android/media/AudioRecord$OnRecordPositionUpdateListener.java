package android.media;

public interface OnRecordPositionUpdateListener
{
    void onMarkerReached(final AudioRecord p0);
    
    void onPeriodicNotification(final AudioRecord p0);
}
