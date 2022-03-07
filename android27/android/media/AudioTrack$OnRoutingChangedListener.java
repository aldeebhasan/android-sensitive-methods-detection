package android.media;

@Deprecated
public interface OnRoutingChangedListener extends AudioRouting.OnRoutingChangedListener
{
    void onRoutingChanged(final AudioTrack p0);
    
    default void onRoutingChanged(final AudioRouting router) {
        throw new RuntimeException("Stub!");
    }
}
