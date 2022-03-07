package android.net.http;

import javax.net.ssl.*;
import java.util.*;
import java.security.cert.*;

public class X509TrustManagerExtensions
{
    public X509TrustManagerExtensions(final X509TrustManager tm) throws IllegalArgumentException {
        throw new RuntimeException("Stub!");
    }
    
    public List<X509Certificate> checkServerTrusted(final X509Certificate[] chain, final String authType, final String host) throws CertificateException {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isUserAddedCertificate(final X509Certificate cert) {
        throw new RuntimeException("Stub!");
    }
}
