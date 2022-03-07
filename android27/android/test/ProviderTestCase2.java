package android.test;

import android.test.mock.*;
import android.content.*;

public abstract class ProviderTestCase2<T extends ContentProvider> extends AndroidTestCase
{
    public ProviderTestCase2(final Class<T> providerClass, final String providerAuthority) {
        throw new RuntimeException("Stub!");
    }
    
    public T getProvider() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void setUp() throws Exception {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void tearDown() throws Exception {
        throw new RuntimeException("Stub!");
    }
    
    public MockContentResolver getMockContentResolver() {
        throw new RuntimeException("Stub!");
    }
    
    public IsolatedContext getMockContext() {
        throw new RuntimeException("Stub!");
    }
    
    public static <T extends ContentProvider> ContentResolver newResolverWithContentProviderFromSql(final Context targetContext, final String filenamePrefix, final Class<T> providerClass, final String authority, final String databaseName, final int databaseVersion, final String sql) throws IllegalAccessException, InstantiationException {
        throw new RuntimeException("Stub!");
    }
}
