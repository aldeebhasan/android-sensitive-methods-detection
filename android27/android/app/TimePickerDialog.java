package android.app;

import android.widget.*;
import android.content.*;
import android.os.*;

public class TimePickerDialog extends AlertDialog implements DialogInterface.OnClickListener, TimePicker.OnTimeChangedListener
{
    public TimePickerDialog(final Context context, final OnTimeSetListener listener, final int hourOfDay, final int minute, final boolean is24HourView) {
        super(null, 0);
        throw new RuntimeException("Stub!");
    }
    
    public TimePickerDialog(final Context context, final int themeResId, final OnTimeSetListener listener, final int hourOfDay, final int minute, final boolean is24HourView) {
        super(null, 0);
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onTimeChanged(final TimePicker view, final int hourOfDay, final int minute) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void show() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onClick(final DialogInterface dialog, final int which) {
        throw new RuntimeException("Stub!");
    }
    
    public void updateTime(final int hourOfDay, final int minuteOfHour) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Bundle onSaveInstanceState() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onRestoreInstanceState(final Bundle savedInstanceState) {
        throw new RuntimeException("Stub!");
    }
    
    public interface OnTimeSetListener
    {
        void onTimeSet(final TimePicker p0, final int p1, final int p2);
    }
}
