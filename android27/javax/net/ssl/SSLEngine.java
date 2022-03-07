package javax.net.ssl;

import java.nio.*;
import java.util.function.*;
import java.util.*;

public abstract class SSLEngine
{
    private String peerHost;
    private int peerPort;
    
    protected SSLEngine() {
        this.peerHost = null;
        this.peerPort = -1;
    }
    
    protected SSLEngine(final String peerHost, final int peerPort) {
        this.peerHost = null;
        this.peerPort = -1;
        this.peerHost = peerHost;
        this.peerPort = peerPort;
    }
    
    public String getPeerHost() {
        return this.peerHost;
    }
    
    public int getPeerPort() {
        return this.peerPort;
    }
    
    public SSLEngineResult wrap(final ByteBuffer byteBuffer, final ByteBuffer byteBuffer2) throws SSLException {
        return this.wrap(new ByteBuffer[] { byteBuffer }, 0, 1, byteBuffer2);
    }
    
    public SSLEngineResult wrap(final ByteBuffer[] array, final ByteBuffer byteBuffer) throws SSLException {
        if (array == null) {
            throw new IllegalArgumentException("src == null");
        }
        return this.wrap(array, 0, array.length, byteBuffer);
    }
    
    public abstract SSLEngineResult wrap(final ByteBuffer[] p0, final int p1, final int p2, final ByteBuffer p3) throws SSLException;
    
    public SSLEngineResult unwrap(final ByteBuffer byteBuffer, final ByteBuffer byteBuffer2) throws SSLException {
        return this.unwrap(byteBuffer, new ByteBuffer[] { byteBuffer2 }, 0, 1);
    }
    
    public SSLEngineResult unwrap(final ByteBuffer byteBuffer, final ByteBuffer[] array) throws SSLException {
        if (array == null) {
            throw new IllegalArgumentException("dsts == null");
        }
        return this.unwrap(byteBuffer, array, 0, array.length);
    }
    
    public abstract SSLEngineResult unwrap(final ByteBuffer p0, final ByteBuffer[] p1, final int p2, final int p3) throws SSLException;
    
    public abstract Runnable getDelegatedTask();
    
    public abstract void closeInbound() throws SSLException;
    
    public abstract boolean isInboundDone();
    
    public abstract void closeOutbound();
    
    public abstract boolean isOutboundDone();
    
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
    
    public abstract void beginHandshake() throws SSLException;
    
    public abstract SSLEngineResult.HandshakeStatus getHandshakeStatus();
    
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
    
    public void setHandshakeApplicationProtocolSelector(final BiFunction<SSLEngine, List<String>, String> biFunction) {
        throw new UnsupportedOperationException();
    }
    
    public BiFunction<SSLEngine, List<String>, String> getHandshakeApplicationProtocolSelector() {
        throw new UnsupportedOperationException();
    }
}
