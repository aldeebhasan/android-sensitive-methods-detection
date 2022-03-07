package java.nio.channels;

import java.nio.channels.spi.*;
import java.io.*;
import java.util.concurrent.*;

public abstract class AsynchronousChannelGroup
{
    private final AsynchronousChannelProvider provider;
    
    protected AsynchronousChannelGroup(final AsynchronousChannelProvider provider) {
        this.provider = provider;
    }
    
    public final AsynchronousChannelProvider provider() {
        return this.provider;
    }
    
    public static AsynchronousChannelGroup withFixedThreadPool(final int n, final ThreadFactory threadFactory) throws IOException {
        return AsynchronousChannelProvider.provider().openAsynchronousChannelGroup(n, threadFactory);
    }
    
    public static AsynchronousChannelGroup withCachedThreadPool(final ExecutorService executorService, final int n) throws IOException {
        return AsynchronousChannelProvider.provider().openAsynchronousChannelGroup(executorService, n);
    }
    
    public static AsynchronousChannelGroup withThreadPool(final ExecutorService executorService) throws IOException {
        return AsynchronousChannelProvider.provider().openAsynchronousChannelGroup(executorService, 0);
    }
    
    public abstract boolean isShutdown();
    
    public abstract boolean isTerminated();
    
    public abstract void shutdown();
    
    public abstract void shutdownNow() throws IOException;
    
    public abstract boolean awaitTermination(final long p0, final TimeUnit p1) throws InterruptedException;
}
