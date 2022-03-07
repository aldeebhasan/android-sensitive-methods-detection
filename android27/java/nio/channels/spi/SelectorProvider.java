package java.nio.channels.spi;

import java.util.*;
import sun.nio.ch.*;
import java.security.*;
import java.io.*;
import java.net.*;
import java.nio.channels.*;

public abstract class SelectorProvider
{
    private static final Object lock;
    private static SelectorProvider provider;
    
    protected SelectorProvider() {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new RuntimePermission("selectorProvider"));
        }
    }
    
    private static boolean loadProviderFromProperty() {
        final String property = System.getProperty("java.nio.channels.spi.SelectorProvider");
        if (property == null) {
            return false;
        }
        try {
            SelectorProvider.provider = (SelectorProvider)Class.forName(property, true, ClassLoader.getSystemClassLoader()).newInstance();
            return true;
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
    
    private static boolean loadProviderAsService() {
        final Iterator<SelectorProvider> iterator = ServiceLoader.load(SelectorProvider.class, ClassLoader.getSystemClassLoader()).iterator();
        while (true) {
            try {
                if (!iterator.hasNext()) {
                    return false;
                }
                SelectorProvider.provider = iterator.next();
                return true;
            }
            catch (ServiceConfigurationError serviceConfigurationError) {
                if (serviceConfigurationError.getCause() instanceof SecurityException) {
                    continue;
                }
                throw serviceConfigurationError;
            }
            break;
        }
    }
    
    public static SelectorProvider provider() {
        synchronized (SelectorProvider.lock) {
            if (SelectorProvider.provider != null) {
                return SelectorProvider.provider;
            }
            return AccessController.doPrivileged((PrivilegedAction<SelectorProvider>)new PrivilegedAction<SelectorProvider>() {
                @Override
                public SelectorProvider run() {
                    if (loadProviderFromProperty()) {
                        return SelectorProvider.provider;
                    }
                    if (loadProviderAsService()) {
                        return SelectorProvider.provider;
                    }
                    SelectorProvider.provider = DefaultSelectorProvider.create();
                    return SelectorProvider.provider;
                }
            });
        }
    }
    
    public abstract DatagramChannel openDatagramChannel() throws IOException;
    
    public abstract DatagramChannel openDatagramChannel(final ProtocolFamily p0) throws IOException;
    
    public abstract Pipe openPipe() throws IOException;
    
    public abstract AbstractSelector openSelector() throws IOException;
    
    public abstract ServerSocketChannel openServerSocketChannel() throws IOException;
    
    public abstract SocketChannel openSocketChannel() throws IOException;
    
    public Channel inheritedChannel() throws IOException {
        return null;
    }
    
    static {
        lock = new Object();
        SelectorProvider.provider = null;
    }
}
