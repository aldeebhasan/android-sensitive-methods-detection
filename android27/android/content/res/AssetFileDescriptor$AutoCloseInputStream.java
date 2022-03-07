package android.content.res;

import android.os.*;
import java.io.*;

public static class AutoCloseInputStream extends ParcelFileDescriptor.AutoCloseInputStream
{
    public AutoCloseInputStream(final AssetFileDescriptor fd) throws IOException {
        super((ParcelFileDescriptor)null);
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int available() throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int read() throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int read(final byte[] buffer, final int offset, final int count) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int read(final byte[] buffer) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public long skip(final long count) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void mark(final int readlimit) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean markSupported() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public synchronized void reset() throws IOException {
        throw new RuntimeException("Stub!");
    }
}
