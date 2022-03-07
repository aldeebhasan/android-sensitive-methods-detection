package android.view.autofill;

import android.view.*;

public abstract static class AutofillCallback
{
    public static final int EVENT_INPUT_HIDDEN = 2;
    public static final int EVENT_INPUT_SHOWN = 1;
    public static final int EVENT_INPUT_UNAVAILABLE = 3;
    
    public AutofillCallback() {
        throw new RuntimeException("Stub!");
    }
    
    public void onAutofillEvent(final View view, final int event) {
        throw new RuntimeException("Stub!");
    }
    
    public void onAutofillEvent(final View view, final int virtualId, final int event) {
        throw new RuntimeException("Stub!");
    }
}
