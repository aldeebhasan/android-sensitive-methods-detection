package android.widget;

public interface OnSeekBarChangeListener
{
    void onProgressChanged(final SeekBar p0, final int p1, final boolean p2);
    
    void onStartTrackingTouch(final SeekBar p0);
    
    void onStopTrackingTouch(final SeekBar p0);
}
