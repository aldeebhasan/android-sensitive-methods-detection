package android.os;

import java.io.*;

public final class MessageQueue
{
    MessageQueue() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void finalize() throws Throwable {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isIdle() {
        throw new RuntimeException("Stub!");
    }
    
    public void addIdleHandler(final IdleHandler handler) {
        throw new RuntimeException("Stub!");
    }
    
    public void removeIdleHandler(final IdleHandler handler) {
        throw new RuntimeException("Stub!");
    }
    
    public void addOnFileDescriptorEventListener(final FileDescriptor fd, final int events, final OnFileDescriptorEventListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public void removeOnFileDescriptorEventListener(final FileDescriptor fd) {
        throw new RuntimeException("Stub!");
    }
    
    public interface OnFileDescriptorEventListener
    {
        public static final int EVENT_ERROR = 4;
        public static final int EVENT_INPUT = 1;
        public static final int EVENT_OUTPUT = 2;
        
        int onFileDescriptorEvents(final FileDescriptor p0, final int p1);
    }
    
    public interface IdleHandler
    {
        boolean queueIdle();
    }
}
