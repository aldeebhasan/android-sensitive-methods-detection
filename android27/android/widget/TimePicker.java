package android.widget;

import android.content.*;
import android.util.*;
import android.os.*;
import android.view.*;
import android.view.autofill.*;

public class TimePicker extends FrameLayout
{
    public TimePicker(final Context context) {
        super(null, null, 0, 0);
        throw new RuntimeException("Stub!");
    }
    
    public TimePicker(final Context context, final AttributeSet attrs) {
        super(null, null, 0, 0);
        throw new RuntimeException("Stub!");
    }
    
    public TimePicker(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(null, null, 0, 0);
        throw new RuntimeException("Stub!");
    }
    
    public TimePicker(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
        super(null, null, 0, 0);
        throw new RuntimeException("Stub!");
    }
    
    public void setHour(final int hour) {
        throw new RuntimeException("Stub!");
    }
    
    public int getHour() {
        throw new RuntimeException("Stub!");
    }
    
    public void setMinute(final int minute) {
        throw new RuntimeException("Stub!");
    }
    
    public int getMinute() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void setCurrentHour(final Integer currentHour) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public Integer getCurrentHour() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void setCurrentMinute(final Integer currentMinute) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public Integer getCurrentMinute() {
        throw new RuntimeException("Stub!");
    }
    
    public void setIs24HourView(final Boolean is24HourView) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean is24HourView() {
        throw new RuntimeException("Stub!");
    }
    
    public void setOnTimeChangedListener(final OnTimeChangedListener onTimeChangedListener) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void setEnabled(final boolean enabled) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean isEnabled() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int getBaseline() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean validateInput() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected Parcelable onSaveInstanceState() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onRestoreInstanceState(final Parcelable state) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public CharSequence getAccessibilityClassName() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void dispatchProvideAutofillStructure(final ViewStructure structure, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void autofill(final AutofillValue value) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int getAutofillType() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public AutofillValue getAutofillValue() {
        throw new RuntimeException("Stub!");
    }
    
    public interface OnTimeChangedListener
    {
        void onTimeChanged(final TimePicker p0, final int p1, final int p2);
    }
}
