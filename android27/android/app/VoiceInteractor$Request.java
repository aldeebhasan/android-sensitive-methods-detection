package android.app;

import android.content.*;

public abstract static class Request
{
    Request() {
        throw new RuntimeException("Stub!");
    }
    
    public String getName() {
        throw new RuntimeException("Stub!");
    }
    
    public void cancel() {
        throw new RuntimeException("Stub!");
    }
    
    public Context getContext() {
        throw new RuntimeException("Stub!");
    }
    
    public Activity getActivity() {
        throw new RuntimeException("Stub!");
    }
    
    public void onCancel() {
        throw new RuntimeException("Stub!");
    }
    
    public void onAttached(final Activity activity) {
        throw new RuntimeException("Stub!");
    }
    
    public void onDetached() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
}
