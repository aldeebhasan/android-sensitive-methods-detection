package android.net;

import javax.net.*;
import javax.net.ssl.*;
import java.io.*;
import java.net.*;

public class SSLCertificateSocketFactory extends SSLSocketFactory
{
    public SSLCertificateSocketFactory(final int handshakeTimeoutMillis) {
        throw new RuntimeException("Stub!");
    }
    
    public static SocketFactory getDefault(final int handshakeTimeoutMillis) {
        throw new RuntimeException("Stub!");
    }
    
    public static SSLSocketFactory getDefault(final int handshakeTimeoutMillis, final SSLSessionCache cache) {
        throw new RuntimeException("Stub!");
    }
    
    public static SSLSocketFactory getInsecure(final int handshakeTimeoutMillis, final SSLSessionCache cache) {
        throw new RuntimeException("Stub!");
    }
    
    public void setTrustManagers(final TrustManager[] trustManager) {
        throw new RuntimeException("Stub!");
    }
    
    public void setNpnProtocols(final byte[][] npnProtocols) {
        throw new RuntimeException("Stub!");
    }
    
    public byte[] getNpnSelectedProtocol(final Socket socket) {
        throw new RuntimeException("Stub!");
    }
    
    public void setKeyManagers(final KeyManager[] keyManagers) {
        throw new RuntimeException("Stub!");
    }
    
    public void setUseSessionTickets(final Socket socket, final boolean useSessionTickets) {
        throw new RuntimeException("Stub!");
    }
    
    public void setHostname(final Socket socket, final String hostName) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Socket createSocket(final Socket k, final String host, final int port, final boolean close) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Socket createSocket() throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Socket createSocket(final InetAddress addr, final int port, final InetAddress localAddr, final int localPort) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Socket createSocket(final InetAddress addr, final int port) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Socket createSocket(final String host, final int port, final InetAddress localAddr, final int localPort) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Socket createSocket(final String host, final int port) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String[] getDefaultCipherSuites() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String[] getSupportedCipherSuites() {
        throw new RuntimeException("Stub!");
    }
}
