package javax.security.cert;

import java.security.*;

public abstract class Certificate
{
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Certificate)) {
            return false;
        }
        try {
            final byte[] encoded = this.getEncoded();
            final byte[] encoded2 = ((Certificate)o).getEncoded();
            if (encoded.length != encoded2.length) {
                return false;
            }
            for (int i = 0; i < encoded.length; ++i) {
                if (encoded[i] != encoded2[i]) {
                    return false;
                }
            }
            return true;
        }
        catch (CertificateException ex) {
            return false;
        }
    }
    
    @Override
    public int hashCode() {
        byte b = 0;
        try {
            final byte[] encoded = this.getEncoded();
            for (byte b2 = 1; b2 < encoded.length; ++b2) {
                b += (byte)(encoded[b2] * b2);
            }
            return b;
        }
        catch (CertificateException ex) {
            return b;
        }
    }
    
    public abstract byte[] getEncoded() throws CertificateEncodingException;
    
    public abstract void verify(final PublicKey p0) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException;
    
    public abstract void verify(final PublicKey p0, final String p1) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException;
    
    @Override
    public abstract String toString();
    
    public abstract PublicKey getPublicKey();
}
