package javax.net.ssl;

import java.util.*;
import java.security.cert.*;
import javax.security.cert.*;
import java.security.*;

public class HandshakeCompletedEvent extends EventObject
{
    private static final long serialVersionUID = 7914963744257769778L;
    private transient SSLSession session;
    
    public HandshakeCompletedEvent(final SSLSocket sslSocket, final SSLSession session) {
        super(sslSocket);
        this.session = session;
    }
    
    public SSLSession getSession() {
        return this.session;
    }
    
    public String getCipherSuite() {
        return this.session.getCipherSuite();
    }
    
    public Certificate[] getLocalCertificates() {
        return this.session.getLocalCertificates();
    }
    
    public Certificate[] getPeerCertificates() throws SSLPeerUnverifiedException {
        return this.session.getPeerCertificates();
    }
    
    public X509Certificate[] getPeerCertificateChain() throws SSLPeerUnverifiedException {
        return this.session.getPeerCertificateChain();
    }
    
    public Principal getPeerPrincipal() throws SSLPeerUnverifiedException {
        Principal principal;
        try {
            principal = this.session.getPeerPrincipal();
        }
        catch (AbstractMethodError abstractMethodError) {
            principal = ((java.security.cert.X509Certificate)this.getPeerCertificates()[0]).getSubjectX500Principal();
        }
        return principal;
    }
    
    public Principal getLocalPrincipal() {
        Principal principal;
        try {
            principal = this.session.getLocalPrincipal();
        }
        catch (AbstractMethodError abstractMethodError) {
            principal = null;
            final Certificate[] localCertificates = this.getLocalCertificates();
            if (localCertificates != null) {
                principal = ((java.security.cert.X509Certificate)localCertificates[0]).getSubjectX500Principal();
            }
        }
        return principal;
    }
    
    public SSLSocket getSocket() {
        return (SSLSocket)this.getSource();
    }
}
