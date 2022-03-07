package android.test.mock;

import android.content.*;
import android.net.*;
import android.database.*;

public class MockContentResolver extends ContentResolver
{
    public MockContentResolver() {
        super(null);
        throw new RuntimeException("Stub!");
    }
    
    public MockContentResolver(final Context context) {
        super(null);
        throw new RuntimeException("Stub!");
    }
    
    public void addProvider(final String name, final ContentProvider provider) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void notifyChange(final Uri uri, final ContentObserver observer, final boolean syncToNetwork) {
        throw new RuntimeException("Stub!");
    }
}
