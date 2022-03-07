package android.database;

import android.os.*;

protected static class SelfContentObserver extends ContentObserver
{
    public SelfContentObserver(final AbstractCursor cursor) {
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
