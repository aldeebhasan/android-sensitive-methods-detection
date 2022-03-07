package android.media;

public class MediaSyncEvent
{
    public static final int SYNC_EVENT_NONE = 0;
    public static final int SYNC_EVENT_PRESENTATION_COMPLETE = 1;
    
    MediaSyncEvent() {
        throw new RuntimeException("Stub!");
    }
    
    public static MediaSyncEvent createEvent(final int eventType) throws IllegalArgumentException {
        throw new RuntimeException("Stub!");
    }
    
    public MediaSyncEvent setAudioSessionId(final int audioSessionId) throws IllegalArgumentException {
        throw new RuntimeException("Stub!");
    }
    
    public int getType() {
        throw new RuntimeException("Stub!");
    }
    
    public int getAudioSessionId() {
        throw new RuntimeException("Stub!");
    }
}
