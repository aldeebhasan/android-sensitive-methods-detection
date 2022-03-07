package android.widget;

import android.content.res.*;

public interface ThemedSpinnerAdapter extends SpinnerAdapter
{
    void setDropDownViewTheme(final Resources.Theme p0);
    
    Resources.Theme getDropDownViewTheme();
}
