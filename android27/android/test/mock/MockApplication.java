package android.test.mock;

import android.app.*;
import android.content.res.*;

@Deprecated
public class MockApplication extends Application
{
    public MockApplication() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onCreate() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onTerminate() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onConfigurationChanged(final Configuration newConfig) {
        throw new RuntimeException("Stub!");
    }
}
