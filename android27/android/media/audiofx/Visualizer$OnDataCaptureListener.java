package android.media.audiofx;

public interface OnDataCaptureListener
{
    void onWaveFormDataCapture(final Visualizer p0, final byte[] p1, final int p2);
    
    void onFftDataCapture(final Visualizer p0, final byte[] p1, final int p2);
}
