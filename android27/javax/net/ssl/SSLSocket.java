package javax.net.ssl;

import java.io.*;
import java.net.*;
import java.util.function.*;
import java.util.*;

public abstract class SSLSocket extends Socket
{
    protected SSLSocket() {
    }
    
    protected SSLSocket(final String s, final int n) throws IOException, UnknownHostException {
        super(s, n);
    }
    
    protected SSLSocket(final InetAddress inetAddress, final int n) throws IOException {
        super(inetAddress, n);
    }
    
    protected SSLSocket(final String s, final int n, final InetAddress inetAddress, final int n2) throws IOException, UnknownHostException {
        super(s, n, inetAddress, n2);
    }
    
    protected SSLSocket(final InetAddress inetAddress, final int n, final InetAddress inetAddress2, final int n2) throws IOException {
        super(inetAddress, n, inetAddress2, n2);
    }
    
    public abstract String[] getSupportedCipherSuites();
    
    public abstract String[] getEnabledCipherSuites();
    
    public abstract void setEnabledCipherSuites(final String[] p0);
    
    public abstract String[] getSupportedProtocols();
    
    public abstract String[] getEnabledProtocols();
    
    public abstract void setEnabledProtocols(final String[] p0);
    
    public abstract SSLSession getSession();
    
    public SSLSession getHandshakeSession() {
        throw new UnsupportedOperationException();
    }
    
    public abstract void addHandshakeCompletedListener(final HandshakeCompletedListener p0);
    
    public abstract void removeHandshakeCompletedListener(final HandshakeCompletedListener p0);
    
    public abstract void startHandshake() throws IOException;
    
    public abstract void setUseClientMode(final boolean p0);
    
    public abstract boolean getUseClientMode();
    
    public abstract void setNeedClientAuth(final boolean p0);
    
    public abstract boolean getNeedClientAuth();
    
    public abstract void setWantClientAuth(final boolean p0);
    
    public abstract boolean getWantClientAuth();
    
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
    
    public String getApplicationProtocol() {
        throw new UnsupportedOperationException();
    }
    
    public String getHandshakeApplicationProtocol() {
        throw new UnsupportedOperationException();
    }
    
    public void setHandshakeApplicationProtocolSelector(final BiFunction<SSLSocket, List<String>, String> biFunction) {
        throw new UnsupportedOperationException();
    }
    
    public BiFunction<SSLSocket, List<String>, String> getHandshakeApplicationProtocolSelector() {
        throw new UnsupportedOperationException();
    }
}
