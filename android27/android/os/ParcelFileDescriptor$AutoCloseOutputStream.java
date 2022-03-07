package android.os;

import java.io.*;

public static class AutoCloseOutputStream extends FileOutputStream
{
    public AutoCloseOutputStream(final ParcelFileDescriptor pfd) {
        super((FileDescriptor)null);
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void close() throws IOException {
        throw new RuntimeException("Stub!");
    }
}
