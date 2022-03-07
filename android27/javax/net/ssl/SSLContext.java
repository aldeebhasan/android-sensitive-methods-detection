package javax.net.ssl;

import sun.security.jca.*;
import java.security.*;

public class SSLContext
{
    private final Provider provider;
    private final SSLContextSpi contextSpi;
    private final String protocol;
    private static SSLContext defaultContext;
    
    protected SSLContext(final SSLContextSpi contextSpi, final Provider provider, final String protocol) {
        this.contextSpi = contextSpi;
        this.provider = provider;
        this.protocol = protocol;
    }
    
    public static synchronized SSLContext getDefault() throws NoSuchAlgorithmException {
        if (SSLContext.defaultContext == null) {
            SSLContext.defaultContext = getInstance("Default");
        }
        return SSLContext.defaultContext;
    }
    
    public static synchronized void setDefault(final SSLContext defaultContext) {
        if (defaultContext == null) {
            throw new NullPointerException();
        }
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new SSLPermission("setDefaultSSLContext"));
        }
        SSLContext.defaultContext = defaultContext;
    }
    
    public static SSLContext getInstance(final String s) throws NoSuchAlgorithmException {
        final GetInstance.Instance instance = GetInstance.getInstance("SSLContext", SSLContextSpi.class, s);
        return new SSLContext((SSLContextSpi)instance.impl, instance.provider, s);
    }
    
    public static SSLContext getInstance(final String s, final String s2) throws NoSuchAlgorithmException, NoSuchProviderException {
        final GetInstance.Instance instance = GetInstance.getInstance("SSLContext", SSLContextSpi.class, s, s2);
        return new SSLContext((SSLContextSpi)instance.impl, instance.provider, s);
    }
    
    public static SSLContext getInstance(final String s, final Provider provider) throws NoSuchAlgorithmException {
        final GetInstance.Instance instance = GetInstance.getInstance("SSLContext", SSLContextSpi.class, s, provider);
        return new SSLContext((SSLContextSpi)instance.impl, instance.provider, s);
    }
    
    public final String getProtocol() {
        return this.protocol;
    }
    
    public final Provider getProvider() {
        return this.provider;
    }
    
    public final void init(final KeyManager[] array, final TrustManager[] array2, final SecureRandom secureRandom) throws KeyManagementException {
        this.contextSpi.engineInit(array, array2, secureRandom);
    }
    
    public final SSLSocketFactory getSocketFactory() {
        return this.contextSpi.engineGetSocketFactory();
    }
    
    public final SSLServerSocketFactory getServerSocketFactory() {
        return this.contextSpi.engineGetServerSocketFactory();
    }
    
    public final SSLEngine createSSLEngine() {
        try {
            return this.contextSpi.engineCreateSSLEngine();
        }
        catch (AbstractMethodError abstractMethodError) {
            final UnsupportedOperationException ex = new UnsupportedOperationException("Provider: " + this.getProvider() + " doesn't support this operation");
            ex.initCause(abstractMethodError);
            throw ex;
        }
    }
    
    public final SSLEngine createSSLEngine(final String s, final int n) {
        try {
            return this.contextSpi.engineCreateSSLEngine(s, n);
        }
        catch (AbstractMethodError abstractMethodError) {
            final UnsupportedOperationException ex = new UnsupportedOperationException("Provider: " + this.getProvider() + " does not support this operation");
            ex.initCause(abstractMethodError);
            throw ex;
        }
    }
    
    public final SSLSessionContext getServerSessionContext() {
        return this.contextSpi.engineGetServerSessionContext();
    }
    
    public final SSLSessionContext getClientSessionContext() {
        return this.contextSpi.engineGetClientSessionContext();
    }
    
    public final SSLParameters getDefaultSSLParameters() {
        return this.contextSpi.engineGetDefaultSSLParameters();
    }
    
    public final SSLParameters getSupportedSSLParameters() {
        return this.contextSpi.engineGetSupportedSSLParameters();
    }
}
