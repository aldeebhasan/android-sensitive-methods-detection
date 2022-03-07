package android.widget;

import android.content.*;
import android.util.*;

public class SeekBar extends AbsSeekBar
{
    public SeekBar(final Context context) {
        super(null, null, 0, 0);
        throw new RuntimeException("Stub!");
    }
    
    public SeekBar(final Context context, final AttributeSet attrs) {
        super(null, null, 0, 0);
        throw new RuntimeException("Stub!");
    }
    
    public SeekBar(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(null, null, 0, 0);
        throw new RuntimeException("Stub!");
    }
    
    public SeekBar(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
        super(null, null, 0, 0);
        throw new RuntimeException("Stub!");
    }
    
    public void setOnSeekBarChangeListener(final OnSeekBarChangeListener l) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public CharSequence getAccessibilityClassName() {
        throw new RuntimeException("Stub!");
    }
    
    public interface OnSeekBarChangeListener
    {
        void onProgressChanged(final SeekBar p0, final int p1, final boolean p2);
        
        void onStartTrackingTouch(final SeekBar p0);
        
        void onStopTrackingTouch(final SeekBar p0);
    }
}
