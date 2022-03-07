package android.app;

import android.widget.*;
import android.content.*;
import android.os.*;

public class DatePickerDialog extends AlertDialog implements DialogInterface.OnClickListener, DatePicker.OnDateChangedListener
{
    public DatePickerDialog(final Context context) {
        super(null, 0);
        throw new RuntimeException("Stub!");
    }
    
    public DatePickerDialog(final Context context, final int themeResId) {
        super(null, 0);
        throw new RuntimeException("Stub!");
    }
    
    public DatePickerDialog(final Context context, final OnDateSetListener listener, final int year, final int month, final int dayOfMonth) {
        super(null, 0);
        throw new RuntimeException("Stub!");
    }
    
    public DatePickerDialog(final Context context, final int themeResId, final OnDateSetListener listener, final int year, final int monthOfYear, final int dayOfMonth) {
        super(null, 0);
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onDateChanged(final DatePicker view, final int year, final int month, final int dayOfMonth) {
        throw new RuntimeException("Stub!");
    }
    
    public void setOnDateSetListener(final OnDateSetListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onClick(final DialogInterface dialog, final int which) {
        throw new RuntimeException("Stub!");
    }
    
    public DatePicker getDatePicker() {
        throw new RuntimeException("Stub!");
    }
    
    public void updateDate(final int year, final int month, final int dayOfMonth) {
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
    
    public interface OnDateSetListener
    {
        void onDateSet(final DatePicker p0, final int p1, final int p2, final int p3);
    }
}
