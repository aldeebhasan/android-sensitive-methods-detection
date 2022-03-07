package java.security;

import java.security.cert.*;
import java.util.*;

public static final class TrustedCertificateEntry implements Entry
{
    private final Certificate cert;
    private final Set<Attribute> attributes;
    
    public TrustedCertificateEntry(final Certificate cert) {
        if (cert == null) {
            throw new NullPointerException("invalid null input");
        }
        this.cert = cert;
        this.attributes = Collections.emptySet();
    }
    
    public TrustedCertificateEntry(final Certificate cert, final Set<Attribute> set) {
        if (cert == null || set == null) {
            throw new NullPointerException("invalid null input");
        }
        this.cert = cert;
        this.attributes = Collections.unmodifiableSet((Set<? extends Attribute>)new HashSet<Attribute>(set));
    }
    
    public Certificate getTrustedCertificate() {
        return this.cert;
    }
    
    @Override
    public Set<Attribute> getAttributes() {
        return this.attributes;
    }
    
    @Override
    public String toString() {
        return "Trusted certificate entry:\r\n" + this.cert.toString();
    }
}
