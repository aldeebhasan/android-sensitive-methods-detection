package android.media;

import android.os.*;

public final class MediaCas implements AutoCloseable
{
    public MediaCas(final int CA_system_id) throws MediaCasException.UnsupportedCasException {
        throw new RuntimeException("Stub!");
    }
    
    public static boolean isSystemIdSupported(final int CA_system_id) {
        throw new RuntimeException("Stub!");
    }
    
    public static PluginDescriptor[] enumeratePlugins() {
        throw new RuntimeException("Stub!");
    }
    
    public void setEventListener(final EventListener listener, final Handler handler) {
        throw new RuntimeException("Stub!");
    }
    
    public void setPrivateData(final byte[] data) throws MediaCasException {
        throw new RuntimeException("Stub!");
    }
    
    public Session openSession() throws MediaCasException {
        throw new RuntimeException("Stub!");
    }
    
    public void processEmm(final byte[] data, final int offset, final int length) throws MediaCasException {
        throw new RuntimeException("Stub!");
    }
    
    public void processEmm(final byte[] data) throws MediaCasException {
        throw new RuntimeException("Stub!");
    }
    
    public void sendEvent(final int event, final int arg, final byte[] data) throws MediaCasException {
        throw new RuntimeException("Stub!");
    }
    
    public void provision(final String provisionString) throws MediaCasException {
        throw new RuntimeException("Stub!");
    }
    
    public void refreshEntitlements(final int refreshType, final byte[] refreshData) throws MediaCasException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void close() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void finalize() {
        throw new RuntimeException("Stub!");
    }
    
    public static class PluginDescriptor
    {
        PluginDescriptor() {
            throw new RuntimeException("Stub!");
        }
        
        public int getSystemId() {
            throw new RuntimeException("Stub!");
        }
        
        public String getName() {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public String toString() {
            throw new RuntimeException("Stub!");
        }
    }
    
    public final class Session implements AutoCloseable
    {
        Session() {
            throw new RuntimeException("Stub!");
        }
        
        public void setPrivateData(final byte[] data) throws MediaCasException {
            throw new RuntimeException("Stub!");
        }
        
        public void processEcm(final byte[] data, final int offset, final int length) throws MediaCasException {
            throw new RuntimeException("Stub!");
        }
        
        public void processEcm(final byte[] data) throws MediaCasException {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public void close() {
            throw new RuntimeException("Stub!");
        }
    }
    
    public interface EventListener
    {
        void onEvent(final MediaCas p0, final int p1, final int p2, final byte[] p3);
    }
}
