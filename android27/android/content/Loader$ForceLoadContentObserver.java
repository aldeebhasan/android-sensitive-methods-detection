package android.content;

import android.database.*;
import android.os.*;

public final class ForceLoadContentObserver extends ContentObserver
{
    public ForceLoadContentObserver() {
        super(null);
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean deliverSelfNotifications() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onChange(final boolean selfChange) {
        throw new RuntimeException("Stub!");
    }
}
