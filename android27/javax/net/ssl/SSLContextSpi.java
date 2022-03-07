package javax.net.ssl;

import java.security.*;
import java.io.*;

public abstract class SSLContextSpi
{
    protected abstract void engineInit(final KeyManager[] p0, final TrustManager[] p1, final SecureRandom p2) throws KeyManagementException;
    
    protected abstract SSLSocketFactory engineGetSocketFactory();
    
    protected abstract SSLServerSocketFactory engineGetServerSocketFactory();
    
    protected abstract SSLEngine engineCreateSSLEngine();
    
    protected abstract SSLEngine engineCreateSSLEngine(final String p0, final int p1);
    
    protected abstract SSLSessionContext engineGetServerSessionContext();
    
    protected abstract SSLSessionContext engineGetClientSessionContext();
    
    private SSLSocket getDefaultSocket() {
        try {
            return (SSLSocket)this.engineGetSocketFactory().createSocket();
        }
        catch (IOException ex) {
            throw new UnsupportedOperationException("Could not obtain parameters", ex);
        }
    }
    
    protected SSLParameters engineGetDefaultSSLParameters() {
        return this.getDefaultSocket().getSSLParameters();
    }
    
    protected SSLParameters engineGetSupportedSSLParameters() {
        final SSLSocket defaultSocket = this.getDefaultSocket();
        final SSLParameters sslParameters = new SSLParameters();
        sslParameters.setCipherSuites(defaultSocket.getSupportedCipherSuites());
        sslParameters.setProtocols(defaultSocket.getSupportedProtocols());
        return sslParameters;
    }
}
