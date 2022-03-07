package android.os;

import java.io.*;

public static class AutoCloseInputStream extends FileInputStream
{
    public AutoCloseInputStream(final ParcelFileDescriptor pfd) {
        super((FileDescriptor)null);
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void close() throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int read() throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int read(final byte[] b) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int read(final byte[] b, final int off, final int len) throws IOException {
        throw new RuntimeException("Stub!");
    }
}
