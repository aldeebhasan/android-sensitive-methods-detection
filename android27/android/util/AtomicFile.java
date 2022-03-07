package android.util;

import java.io.*;

public class AtomicFile
{
    public AtomicFile(final File baseName) {
        throw new RuntimeException("Stub!");
    }
    
    public File getBaseFile() {
        throw new RuntimeException("Stub!");
    }
    
    public void delete() {
        throw new RuntimeException("Stub!");
    }
    
    public FileOutputStream startWrite() throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    public void finishWrite(final FileOutputStream str) {
        throw new RuntimeException("Stub!");
    }
    
    public void failWrite(final FileOutputStream str) {
        throw new RuntimeException("Stub!");
    }
    
    public FileInputStream openRead() throws FileNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public byte[] readFully() throws IOException {
        throw new RuntimeException("Stub!");
    }
}
