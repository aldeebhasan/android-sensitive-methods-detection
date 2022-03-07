package android.net.http;

import java.io.*;
import java.util.*;
import java.net.*;

public final class HttpResponseCache extends ResponseCache implements Closeable
{
    HttpResponseCache() {
        throw new RuntimeException("Stub!");
    }
    
    public static HttpResponseCache getInstalled() {
        throw new RuntimeException("Stub!");
    }
    
    public static synchronized HttpResponseCache install(final File directory, final long maxSize) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public CacheResponse get(final URI uri, final String requestMethod, final Map<String, List<String>> requestHeaders) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public CacheRequest put(final URI uri, final URLConnection urlConnection) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    public long size() {
        throw new RuntimeException("Stub!");
    }
    
    public long maxSize() {
        throw new RuntimeException("Stub!");
    }
    
    public void flush() {
        throw new RuntimeException("Stub!");
    }
    
    public int getNetworkCount() {
        throw new RuntimeException("Stub!");
    }
    
    public int getHitCount() {
        throw new RuntimeException("Stub!");
    }
    
    public int getRequestCount() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void close() throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    public void delete() throws IOException {
        throw new RuntimeException("Stub!");
    }
}
