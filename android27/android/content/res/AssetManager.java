package android.content.res;

import java.io.*;

public final class AssetManager implements AutoCloseable
{
    public static final int ACCESS_BUFFER = 3;
    public static final int ACCESS_RANDOM = 1;
    public static final int ACCESS_STREAMING = 2;
    public static final int ACCESS_UNKNOWN = 0;
    
    AssetManager() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void close() {
        throw new RuntimeException("Stub!");
    }
    
    public final InputStream open(final String fileName) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    public final InputStream open(final String fileName, final int accessMode) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    public final AssetFileDescriptor openFd(final String fileName) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    public final native String[] list(final String p0) throws IOException;
    
    public final AssetFileDescriptor openNonAssetFd(final String fileName) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    public final AssetFileDescriptor openNonAssetFd(final int cookie, final String fileName) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    public final XmlResourceParser openXmlResourceParser(final String fileName) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    public final XmlResourceParser openXmlResourceParser(final int cookie, final String fileName) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void finalize() throws Throwable {
        throw new RuntimeException("Stub!");
    }
    
    public final native String[] getLocales();
    
    public final class AssetInputStream extends InputStream
    {
        AssetInputStream() {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public final int read() throws IOException {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public final boolean markSupported() {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public final int available() throws IOException {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public final void close() throws IOException {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public final void mark(final int readlimit) {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public final void reset() throws IOException {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public final int read(final byte[] b) throws IOException {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public final int read(final byte[] b, final int off, final int len) throws IOException {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public final long skip(final long n) throws IOException {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        protected void finalize() throws Throwable {
            throw new RuntimeException("Stub!");
        }
    }
}
