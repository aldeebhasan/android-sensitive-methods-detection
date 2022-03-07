package android.renderscript;

import android.content.*;

public class RenderScript
{
    public static final int CREATE_FLAG_LOW_LATENCY = 2;
    public static final int CREATE_FLAG_LOW_POWER = 4;
    public static final int CREATE_FLAG_NONE = 0;
    
    RenderScript() {
        throw new RuntimeException("Stub!");
    }
    
    public static long getMinorVersion() {
        throw new RuntimeException("Stub!");
    }
    
    public void setMessageHandler(final RSMessageHandler msg) {
        throw new RuntimeException("Stub!");
    }
    
    public RSMessageHandler getMessageHandler() {
        throw new RuntimeException("Stub!");
    }
    
    public void sendMessage(final int id, final int[] data) {
        throw new RuntimeException("Stub!");
    }
    
    public void setErrorHandler(final RSErrorHandler msg) {
        throw new RuntimeException("Stub!");
    }
    
    public RSErrorHandler getErrorHandler() {
        throw new RuntimeException("Stub!");
    }
    
    public void setPriority(final Priority p) {
        throw new RuntimeException("Stub!");
    }
    
    public final Context getApplicationContext() {
        throw new RuntimeException("Stub!");
    }
    
    public static RenderScript create(final Context ctx) {
        throw new RuntimeException("Stub!");
    }
    
    public static RenderScript create(final Context ctx, final ContextType ct) {
        throw new RuntimeException("Stub!");
    }
    
    public static RenderScript create(final Context ctx, final ContextType ct, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    public static void releaseAllContexts() {
        throw new RuntimeException("Stub!");
    }
    
    public static RenderScript createMultiContext(final Context ctx, final ContextType ct, final int flags, final int API_number) {
        throw new RuntimeException("Stub!");
    }
    
    public void contextDump() {
        throw new RuntimeException("Stub!");
    }
    
    public void finish() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void finalize() throws Throwable {
        throw new RuntimeException("Stub!");
    }
    
    public void destroy() {
        throw new RuntimeException("Stub!");
    }
    
    public enum ContextType
    {
        DEBUG, 
        NORMAL, 
        PROFILE;
    }
    
    public static class RSMessageHandler implements Runnable
    {
        protected int[] mData;
        protected int mID;
        protected int mLength;
        
        public RSMessageHandler() {
            this.mData = null;
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public void run() {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static class RSErrorHandler implements Runnable
    {
        protected String mErrorMessage;
        protected int mErrorNum;
        
        public RSErrorHandler() {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public void run() {
            throw new RuntimeException("Stub!");
        }
    }
    
    public enum Priority
    {
        LOW, 
        NORMAL;
    }
}
