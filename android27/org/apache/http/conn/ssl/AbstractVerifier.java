package org.apache.http.conn.ssl;

import java.io.*;
import java.security.cert.*;
import javax.net.ssl.*;

@Deprecated
public abstract class AbstractVerifier implements X509HostnameVerifier
{
    public AbstractVerifier() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public final void verify(final String host, final SSLSocket ssl) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public final boolean verify(final String host, final SSLSession session) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public final void verify(final String host, final X509Certificate cert) throws SSLException {
        throw new RuntimeException("Stub!");
    }
    
    public final void verify(final String host, final String[] cns, final String[] subjectAlts, final boolean strictWithSubDomains) throws SSLException {
        throw new RuntimeException("Stub!");
    }
    
    public static boolean acceptableCountryWildcard(final String cn) {
        throw new RuntimeException("Stub!");
    }
    
    public static String[] getCNs(final X509Certificate cert) {
        throw new RuntimeException("Stub!");
    }
    
    public static String[] getDNSSubjectAlts(final X509Certificate cert) {
        throw new RuntimeException("Stub!");
    }
    
    public static int countDots(final String s) {
        throw new RuntimeException("Stub!");
    }
}
