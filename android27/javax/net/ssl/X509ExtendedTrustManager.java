package javax.net.ssl;

import java.net.*;
import java.security.cert.*;

public abstract class X509ExtendedTrustManager implements X509TrustManager
{
    public abstract void checkClientTrusted(final X509Certificate[] p0, final String p1, final Socket p2) throws CertificateException;
    
    public abstract void checkServerTrusted(final X509Certificate[] p0, final String p1, final Socket p2) throws CertificateException;
    
    public abstract void checkClientTrusted(final X509Certificate[] p0, final String p1, final SSLEngine p2) throws CertificateException;
    
    public abstract void checkServerTrusted(final X509Certificate[] p0, final String p1, final SSLEngine p2) throws CertificateException;
}
