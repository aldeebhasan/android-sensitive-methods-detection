package android.test;

import android.test.mock.*;
import android.content.*;

@Deprecated
public abstract class ProviderTestCase<T extends ContentProvider> extends InstrumentationTestCase
{
    public ProviderTestCase(final Class<T> providerClass, final String providerAuthority) {
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
    
    public static <T extends ContentProvider> ContentResolver newResolverWithContentProviderFromSql(final Context targetContext, final Class<T> providerClass, final String authority, final String databaseName, final int databaseVersion, final String sql) throws IllegalAccessException, InstantiationException {
        throw new RuntimeException("Stub!");
    }
}
