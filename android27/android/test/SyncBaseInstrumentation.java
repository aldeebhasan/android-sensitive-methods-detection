package android.test;

import android.net.*;

@Deprecated
public class SyncBaseInstrumentation extends InstrumentationTestCase
{
    public SyncBaseInstrumentation() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void setUp() throws Exception {
        throw new RuntimeException("Stub!");
    }
    
    protected void syncProvider(final Uri uri, final String accountName, final String authority) throws Exception {
        throw new RuntimeException("Stub!");
    }
    
    protected void cancelSyncsandDisableAutoSync() {
        throw new RuntimeException("Stub!");
    }
}
