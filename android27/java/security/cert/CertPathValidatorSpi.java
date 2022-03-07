package java.security.cert;

import java.security.*;

public abstract class CertPathValidatorSpi
{
    public abstract CertPathValidatorResult engineValidate(final CertPath p0, final CertPathParameters p1) throws CertPathValidatorException, InvalidAlgorithmParameterException;
    
    public CertPathChecker engineGetRevocationChecker() {
        throw new UnsupportedOperationException();
    }
}
