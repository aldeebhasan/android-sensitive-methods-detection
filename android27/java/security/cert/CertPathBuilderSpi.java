package java.security.cert;

import java.security.*;

public abstract class CertPathBuilderSpi
{
    public abstract CertPathBuilderResult engineBuild(final CertPathParameters p0) throws CertPathBuilderException, InvalidAlgorithmParameterException;
    
    public CertPathChecker engineGetRevocationChecker() {
        throw new UnsupportedOperationException();
    }
}
