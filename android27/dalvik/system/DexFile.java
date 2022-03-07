package dalvik.system;

import java.util.*;
import java.io.*;

@Deprecated
public final class DexFile
{
    public DexFile(final File file) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    public DexFile(final String fileName) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public static DexFile loadDex(final String sourcePathName, final String outputPathName, final int flags) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    public String getName() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
    
    public void close() throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    public Class loadClass(final String name, final ClassLoader loader) {
        throw new RuntimeException("Stub!");
    }
    
    public Enumeration<String> entries() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void finalize() throws Throwable {
        throw new RuntimeException("Stub!");
    }
    
    public static native boolean isDexOptNeeded(final String p0) throws FileNotFoundException, IOException;
}
