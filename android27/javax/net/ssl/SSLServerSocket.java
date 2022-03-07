package javax.net.ssl;

import java.io.*;
import java.net.*;

public abstract class SSLServerSocket extends ServerSocket
{
    protected SSLServerSocket() throws IOException {
    }
    
    protected SSLServerSocket(final int n) throws IOException {
        super(n);
    }
    
    protected SSLServerSocket(final int n, final int n2) throws IOException {
        super(n, n2);
    }
    
    protected SSLServerSocket(final int n, final int n2, final InetAddress inetAddress) throws IOException {
        super(n, n2, inetAddress);
    }
    
    public abstract String[] getEnabledCipherSuites();
    
    public abstract void setEnabledCipherSuites(final String[] p0);
    
    public abstract String[] getSupportedCipherSuites();
    
    public abstract String[] getSupportedProtocols();
    
    public abstract String[] getEnabledProtocols();
    
    public abstract void setEnabledProtocols(final String[] p0);
    
    public abstract void setNeedClientAuth(final boolean p0);
    
    public abstract boolean getNeedClientAuth();
    
    public abstract void setWantClientAuth(final boolean p0);
    
    public abstract boolean getWantClientAuth();
    
    public abstract void setUseClientMode(final boolean p0);
    
    public abstract boolean getUseClientMode();
    
    public abstract void setEnableSessionCreation(final boolean p0);
    
    public abstract boolean getEnableSessionCreation();
    
    public SSLParameters getSSLParameters() {
        final SSLParameters sslParameters = new SSLParameters();
        sslParameters.setCipherSuites(this.getEnabledCipherSuites());
        sslParameters.setProtocols(this.getEnabledProtocols());
        if (this.getNeedClientAuth()) {
            sslParameters.setNeedClientAuth(true);
        }
        else if (this.getWantClientAuth()) {
            sslParameters.setWantClientAuth(true);
        }
        return sslParameters;
    }
    
    public void setSSLParameters(final SSLParameters sslParameters) {
        final String[] cipherSuites = sslParameters.getCipherSuites();
        if (cipherSuites != null) {
            this.setEnabledCipherSuites(cipherSuites);
        }
        final String[] protocols = sslParameters.getProtocols();
        if (protocols != null) {
            this.setEnabledProtocols(protocols);
        }
        if (sslParameters.getNeedClientAuth()) {
            this.setNeedClientAuth(true);
        }
        else if (sslParameters.getWantClientAuth()) {
            this.setWantClientAuth(true);
        }
        else {
            this.setWantClientAuth(false);
        }
    }
}
