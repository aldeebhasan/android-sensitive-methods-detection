package android.view.autofill;

import android.view.*;
import android.graphics.*;

public final class AutofillManager
{
    public static final String EXTRA_ASSIST_STRUCTURE = "android.view.autofill.extra.ASSIST_STRUCTURE";
    public static final String EXTRA_AUTHENTICATION_RESULT = "android.view.autofill.extra.AUTHENTICATION_RESULT";
    public static final String EXTRA_CLIENT_STATE = "android.view.autofill.extra.CLIENT_STATE";
    
    AutofillManager() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isEnabled() {
        throw new RuntimeException("Stub!");
    }
    
    public void requestAutofill(final View view) {
        throw new RuntimeException("Stub!");
    }
    
    public void requestAutofill(final View view, final int virtualId, final Rect absBounds) {
        throw new RuntimeException("Stub!");
    }
    
    public void notifyViewEntered(final View view) {
        throw new RuntimeException("Stub!");
    }
    
    public void notifyViewExited(final View view) {
        throw new RuntimeException("Stub!");
    }
    
    public void notifyViewVisibilityChanged(final View view, final boolean isVisible) {
        throw new RuntimeException("Stub!");
    }
    
    public void notifyViewVisibilityChanged(final View view, final int virtualId, final boolean isVisible) {
        throw new RuntimeException("Stub!");
    }
    
    public void notifyViewEntered(final View view, final int virtualId, final Rect absBounds) {
        throw new RuntimeException("Stub!");
    }
    
    public void notifyViewExited(final View view, final int virtualId) {
        throw new RuntimeException("Stub!");
    }
    
    public void notifyValueChanged(final View view) {
        throw new RuntimeException("Stub!");
    }
    
    public void notifyValueChanged(final View view, final int virtualId, final AutofillValue value) {
        throw new RuntimeException("Stub!");
    }
    
    public void commit() {
        throw new RuntimeException("Stub!");
    }
    
    public void cancel() {
        throw new RuntimeException("Stub!");
    }
    
    public void disableAutofillServices() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean hasEnabledAutofillServices() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isAutofillSupported() {
        throw new RuntimeException("Stub!");
    }
    
    public void registerCallback(final AutofillCallback callback) {
        throw new RuntimeException("Stub!");
    }
    
    public void unregisterCallback(final AutofillCallback callback) {
        throw new RuntimeException("Stub!");
    }
    
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
}
