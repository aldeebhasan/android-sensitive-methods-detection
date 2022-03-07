package android.content.pm;

import java.io.*;
import android.content.*;

public static class Session implements Closeable
{
    Session() {
        throw new RuntimeException("Stub!");
    }
    
    public void setStagingProgress(final float progress) {
        throw new RuntimeException("Stub!");
    }
    
    public OutputStream openWrite(final String name, final long offsetBytes, final long lengthBytes) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    public void fsync(final OutputStream out) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    public String[] getNames() throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    public InputStream openRead(final String name) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    public void removeSplit(final String splitName) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    public void commit(final IntentSender statusReceiver) {
        throw new RuntimeException("Stub!");
    }
    
    public void transfer(final String packageName) throws PackageManager.NameNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void close() {
        throw new RuntimeException("Stub!");
    }
    
    public void abandon() {
        throw new RuntimeException("Stub!");
    }
}
