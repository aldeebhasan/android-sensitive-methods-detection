package android.content.res;

import android.os.*;
import java.io.*;

public static class AutoCloseOutputStream extends ParcelFileDescriptor.AutoCloseOutputStream
{
    public AutoCloseOutputStream(final AssetFileDescriptor fd) throws IOException {
        super((ParcelFileDescriptor)null);
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void write(final byte[] buffer, final int offset, final int count) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void write(final byte[] buffer) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void write(final int oneByte) throws IOException {
        throw new RuntimeException("Stub!");
    }
}
