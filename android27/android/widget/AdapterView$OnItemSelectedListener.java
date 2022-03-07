package android.widget;

import android.view.*;

public interface OnItemSelectedListener
{
    void onItemSelected(final AdapterView<?> p0, final View p1, final int p2, final long p3);
    
    void onNothingSelected(final AdapterView<?> p0);
}
