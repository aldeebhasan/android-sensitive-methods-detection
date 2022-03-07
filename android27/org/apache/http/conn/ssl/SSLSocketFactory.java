package org.apache.http.conn.ssl;

import org.apache.http.conn.scheme.*;
import java.security.*;
import java.io.*;
import org.apache.http.params.*;
import java.net.*;

@Deprecated
public class SSLSocketFactory implements LayeredSocketFactory
{
    public static final X509HostnameVerifier ALLOW_ALL_HOSTNAME_VERIFIER;
    public static final X509HostnameVerifier BROWSER_COMPATIBLE_HOSTNAME_VERIFIER;
    public static final String SSL = "SSL";
    public static final String SSLV2 = "SSLv2";
    public static final X509HostnameVerifier STRICT_HOSTNAME_VERIFIER;
    public static final String TLS = "TLS";
    
    public SSLSocketFactory(final String algorithm, final KeyStore keystore, final String keystorePassword, final KeyStore truststore, final SecureRandom random, final HostNameResolver nameResolver) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
        throw new RuntimeException("Stub!");
    }
    
    public SSLSocketFactory(final KeyStore keystore, final String keystorePassword, final KeyStore truststore) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
        throw new RuntimeException("Stub!");
    }
    
    public SSLSocketFactory(final KeyStore keystore, final String keystorePassword) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
        throw new RuntimeException("Stub!");
    }
    
    public SSLSocketFactory(final KeyStore truststore) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
        throw new RuntimeException("Stub!");
    }
    
    public static SSLSocketFactory getSocketFactory() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Socket createSocket() throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Socket connectSocket(final Socket sock, final String host, final int port, final InetAddress localAddress, final int localPort, final HttpParams params) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean isSecure(final Socket sock) throws IllegalArgumentException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Socket createSocket(final Socket socket, final String host, final int port, final boolean autoClose) throws IOException, UnknownHostException {
        throw new RuntimeException("Stub!");
    }
    
    public void setHostnameVerifier(final X509HostnameVerifier hostnameVerifier) {
        throw new RuntimeException("Stub!");
    }
    
    public X509HostnameVerifier getHostnameVerifier() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        ALLOW_ALL_HOSTNAME_VERIFIER = null;
        BROWSER_COMPATIBLE_HOSTNAME_VERIFIER = null;
        STRICT_HOSTNAME_VERIFIER = null;
    }
}
