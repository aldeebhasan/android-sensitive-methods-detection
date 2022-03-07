package android.view.accessibility;

import android.view.*;
import android.os.*;

public abstract class AccessibilityRequestPreparer
{
    public static final int REQUEST_TYPE_EXTRA_DATA = 1;
    
    public AccessibilityRequestPreparer(final View view, final int requestTypes) {
        throw new RuntimeException("Stub!");
    }
    
    public abstract void onPrepareExtraData(final int p0, final String p1, final Bundle p2, final Message p3);
    
    public View getView() {
        throw new RuntimeException("Stub!");
    }
}
