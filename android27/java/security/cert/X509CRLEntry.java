package java.security.cert;

import java.math.*;
import javax.security.auth.x500.*;
import java.util.*;
import sun.security.x509.*;

public abstract class X509CRLEntry implements X509Extension
{
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof X509CRLEntry)) {
            return false;
        }
        try {
            final byte[] encoded = this.getEncoded();
            final byte[] encoded2 = ((X509CRLEntry)o).getEncoded();
            if (encoded.length != encoded2.length) {
                return false;
            }
            for (int i = 0; i < encoded.length; ++i) {
                if (encoded[i] != encoded2[i]) {
                    return false;
                }
            }
        }
        catch (CRLException ex) {
            return false;
        }
        return true;
    }
    
    @Override
    public int hashCode() {
        byte b = 0;
        try {
            final byte[] encoded = this.getEncoded();
            for (byte b2 = 1; b2 < encoded.length; ++b2) {
                b += (byte)(encoded[b2] * b2);
            }
        }
        catch (CRLException ex) {
            return b;
        }
        return b;
    }
    
    public abstract byte[] getEncoded() throws CRLException;
    
    public abstract BigInteger getSerialNumber();
    
    public X500Principal getCertificateIssuer() {
        return null;
    }
    
    public abstract Date getRevocationDate();
    
    public abstract boolean hasExtensions();
    
    @Override
    public abstract String toString();
    
    public CRLReason getRevocationReason() {
        if (!this.hasExtensions()) {
            return null;
        }
        return X509CRLEntryImpl.getRevocationReason(this);
    }
}
