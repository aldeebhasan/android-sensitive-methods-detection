package android.companion;

import android.content.*;

public abstract static class Callback
{
    public Callback() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract void onDeviceFound(final IntentSender p0);
    
    public abstract void onFailure(final CharSequence p0);
}
