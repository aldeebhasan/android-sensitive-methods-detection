package android.test.mock;

import android.content.*;

@Deprecated
public class MockDialogInterface implements DialogInterface
{
    public MockDialogInterface() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void cancel() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void dismiss() {
        throw new RuntimeException("Stub!");
    }
}
