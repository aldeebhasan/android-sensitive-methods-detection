package java.security.cert;

import java.util.*;

public abstract class PKIXCertPathChecker implements CertPathChecker, Cloneable
{
    @Override
    public abstract void init(final boolean p0) throws CertPathValidatorException;
    
    @Override
    public abstract boolean isForwardCheckingSupported();
    
    public abstract Set<String> getSupportedExtensions();
    
    public abstract void check(final Certificate p0, final Collection<String> p1) throws CertPathValidatorException;
    
    @Override
    public void check(final Certificate certificate) throws CertPathValidatorException {
        this.check(certificate, (Collection<String>)Collections.emptySet());
    }
    
    public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException ex) {
            throw new InternalError(ex.toString(), ex);
        }
    }
}
