package android.media;

public interface OnJetEventListener
{
    void onJetEvent(final JetPlayer p0, final short p1, final byte p2, final byte p3, final byte p4, final byte p5);
    
    void onJetUserIdUpdate(final JetPlayer p0, final int p1, final int p2);
    
    void onJetNumQueuedSegmentUpdate(final JetPlayer p0, final int p1);
    
    void onJetPauseUpdate(final JetPlayer p0, final int p1);
}
