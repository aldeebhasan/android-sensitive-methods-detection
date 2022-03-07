package android.hardware.display;

public interface DisplayListener
{
    void onDisplayAdded(final int p0);
    
    void onDisplayRemoved(final int p0);
    
    void onDisplayChanged(final int p0);
}
