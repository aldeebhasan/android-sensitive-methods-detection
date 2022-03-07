package dalvik.system;

import java.nio.*;
import java.io.*;

public final class InMemoryDexClassLoader extends BaseDexClassLoader
{
    public InMemoryDexClassLoader(final ByteBuffer[] dexBuffers, final ClassLoader parent) {
        super(null, null, null, null);
        throw new RuntimeException("Stub!");
    }
    
    public InMemoryDexClassLoader(final ByteBuffer dexBuffer, final ClassLoader parent) {
        super(null, null, null, null);
        throw new RuntimeException("Stub!");
    }
}
