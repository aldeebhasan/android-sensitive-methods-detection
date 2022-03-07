package android.widget;

public interface MediaPlayerControl
{
    void start();
    
    void pause();
    
    int getDuration();
    
    int getCurrentPosition();
    
    void seekTo(final int p0);
    
    boolean isPlaying();
    
    int getBufferPercentage();
    
    boolean canPause();
    
    boolean canSeekBackward();
    
    boolean canSeekForward();
    
    int getAudioSessionId();
}
