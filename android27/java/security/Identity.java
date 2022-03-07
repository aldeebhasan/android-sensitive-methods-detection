package java.security;

import java.io.*;
import java.util.*;

@Deprecated
public abstract class Identity implements Principal, Serializable
{
    private static final long serialVersionUID = 3609922007826600659L;
    private String name;
    private PublicKey publicKey;
    String info;
    IdentityScope scope;
    Vector<Certificate> certificates;
    
    protected Identity() {
        this("restoring...");
    }
    
    public Identity(final String s, final IdentityScope scope) throws KeyManagementException {
        this(s);
        if (scope != null) {
            scope.addIdentity(this);
        }
        this.scope = scope;
    }
    
    public Identity(final String name) {
        this.info = "No further information available.";
        this.name = name;
    }
    
    @Override
    public final String getName() {
        return this.name;
    }
    
    public final IdentityScope getScope() {
        return this.scope;
    }
    
    public PublicKey getPublicKey() {
        return this.publicKey;
    }
    
    public void setPublicKey(final PublicKey publicKey) throws KeyManagementException {
        check("setIdentityPublicKey");
        this.publicKey = publicKey;
        this.certificates = new Vector<Certificate>();
    }
    
    public void setInfo(final String info) {
        check("setIdentityInfo");
        this.info = info;
    }
    
    public String getInfo() {
        return this.info;
    }
    
    public void addCertificate(final Certificate certificate) throws KeyManagementException {
        check("addIdentityCertificate");
        if (this.certificates == null) {
            this.certificates = new Vector<Certificate>();
        }
        if (this.publicKey != null) {
            if (!this.keyEquals(this.publicKey, certificate.getPublicKey())) {
                throw new KeyManagementException("public key different from cert public key");
            }
        }
        else {
            this.publicKey = certificate.getPublicKey();
        }
        this.certificates.addElement(certificate);
    }
    
    private boolean keyEquals(final PublicKey publicKey, final PublicKey publicKey2) {
        final String format = publicKey.getFormat();
        final String format2 = publicKey2.getFormat();
        return !(format == null ^ format2 == null) && (format == null || format2 == null || format.equalsIgnoreCase(format2)) && Arrays.equals(publicKey.getEncoded(), publicKey2.getEncoded());
    }
    
    public void removeCertificate(final Certificate certificate) throws KeyManagementException {
        check("removeIdentityCertificate");
        if (this.certificates != null) {
            this.certificates.removeElement(certificate);
        }
    }
    
    public Certificate[] certificates() {
        if (this.certificates == null) {
            return new Certificate[0];
        }
        final Certificate[] array = new Certificate[this.certificates.size()];
        this.certificates.copyInto(array);
        return array;
    }
    
    @Override
    public final boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof Identity) {
            final Identity identity = (Identity)o;
            return this.fullName().equals(identity.fullName()) || this.identityEquals(identity);
        }
        return false;
    }
    
    protected boolean identityEquals(final Identity identity) {
        return this.name.equalsIgnoreCase(identity.name) && !(this.publicKey == null ^ identity.publicKey == null) && (this.publicKey == null || identity.publicKey == null || this.publicKey.equals(identity.publicKey));
    }
    
    String fullName() {
        String s = this.name;
        if (this.scope != null) {
            s = s + "." + this.scope.getName();
        }
        return s;
    }
    
    @Override
    public String toString() {
        check("printIdentity");
        String s = this.name;
        if (this.scope != null) {
            s = s + "[" + this.scope.getName() + "]";
        }
        return s;
    }
    
    public String toString(final boolean b) {
        String s = this.toString();
        if (b) {
            final String string = s + "\n" + this.printKeys() + "\n" + this.printCertificates();
            if (this.info != null) {
                s = string + "\n\t" + this.info;
            }
            else {
                s = string + "\n\tno additional information available.";
            }
        }
        return s;
    }
    
    String printKeys() {
        String s;
        if (this.publicKey != null) {
            s = "\tpublic key initialized";
        }
        else {
            s = "\tno public key";
        }
        return s;
    }
    
    String printCertificates() {
        final String s = "";
        if (this.certificates == null) {
            return "\tno certificates";
        }
        String s2 = s + "\tcertificates: \n";
        int n = 1;
        for (final Certificate certificate : this.certificates) {
            s2 = s2 + "\tcertificate " + n++ + "\tfor  : " + certificate.getPrincipal() + "\n" + "\t\t\tfrom : " + certificate.getGuarantor() + "\n";
        }
        return s2;
    }
    
    @Override
    public int hashCode() {
        return this.name.hashCode();
    }
    
    private static void check(final String s) {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkSecurityAccess(s);
        }
    }
}
