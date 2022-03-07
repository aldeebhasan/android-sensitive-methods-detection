package java.nio.channels.spi;

import java.io.*;
import java.util.concurrent.*;
import java.nio.channels.*;
import sun.nio.ch.*;
import java.security.*;
import java.util.*;

public abstract class AsynchronousChannelProvider
{
    private static Void checkPermission() {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new RuntimePermission("asynchronousChannelProvider"));
        }
        return null;
    }
    
    private AsynchronousChannelProvider(final Void void1) {
    }
    
    protected AsynchronousChannelProvider() {
        this(checkPermission());
    }
    
    public static AsynchronousChannelProvider provider() {
        return ProviderHolder.provider;
    }
    
    public abstract AsynchronousChannelGroup openAsynchronousChannelGroup(final int p0, final ThreadFactory p1) throws IOException;
    
    public abstract AsynchronousChannelGroup openAsynchronousChannelGroup(final ExecutorService p0, final int p1) throws IOException;
    
    public abstract AsynchronousServerSocketChannel openAsynchronousServerSocketChannel(final AsynchronousChannelGroup p0) throws IOException;
    
    public abstract AsynchronousSocketChannel openAsynchronousSocketChannel(final AsynchronousChannelGroup p0) throws IOException;
    
    private static class ProviderHolder
    {
        static final AsynchronousChannelProvider provider;
        
        private static AsynchronousChannelProvider load() {
            return AccessController.doPrivileged((PrivilegedAction<AsynchronousChannelProvider>)new PrivilegedAction<AsynchronousChannelProvider>() {
                @Override
                public AsynchronousChannelProvider run() {
                    final AsynchronousChannelProvider access$000 = loadProviderFromProperty();
                    if (access$000 != null) {
                        return access$000;
                    }
                    final AsynchronousChannelProvider access$2 = loadProviderAsService();
                    if (access$2 != null) {
                        return access$2;
                    }
                    return DefaultAsynchronousChannelProvider.create();
                }
            });
        }
        
        private static AsynchronousChannelProvider loadProviderFromProperty() {
            final String property = System.getProperty("java.nio.channels.spi.AsynchronousChannelProvider");
            if (property == null) {
                return null;
            }
            try {
                return (AsynchronousChannelProvider)Class.forName(property, true, ClassLoader.getSystemClassLoader()).newInstance();
            }
            catch (ClassNotFoundException ex) {
                throw new ServiceConfigurationError(null, ex);
            }
            catch (IllegalAccessException ex2) {
                throw new ServiceConfigurationError(null, ex2);
            }
            catch (InstantiationException ex3) {
                throw new ServiceConfigurationError(null, ex3);
            }
            catch (SecurityException ex4) {
                throw new ServiceConfigurationError(null, ex4);
            }
        }
        
        private static AsynchronousChannelProvider loadProviderAsService() {
            final Iterator<AsynchronousChannelProvider> iterator = ServiceLoader.load(AsynchronousChannelProvider.class, ClassLoader.getSystemClassLoader()).iterator();
            try {
                return iterator.hasNext() ? iterator.next() : null;
            }
            catch (ServiceConfigurationError serviceConfigurationError) {
                if (serviceConfigurationError.getCause() instanceof SecurityException) {
                    return iterator.hasNext() ? iterator.next() : null;
                }
                throw serviceConfigurationError;
            }
        }
        
        static {
            provider = load();
        }
    }
}
