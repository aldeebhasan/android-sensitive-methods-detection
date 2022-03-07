package java.security.cert;

import java.util.*;
import java.security.*;

public class PKIXBuilderParameters extends PKIXParameters
{
    private int maxPathLength;
    
    public PKIXBuilderParameters(final Set<TrustAnchor> set, final CertSelector targetCertConstraints) throws InvalidAlgorithmParameterException {
        super(set);
        this.maxPathLength = 5;
        this.setTargetCertConstraints(targetCertConstraints);
    }
    
    public PKIXBuilderParameters(final KeyStore keyStore, final CertSelector targetCertConstraints) throws KeyStoreException, InvalidAlgorithmParameterException {
        super(keyStore);
        this.maxPathLength = 5;
        this.setTargetCertConstraints(targetCertConstraints);
    }
    
    public void setMaxPathLength(final int maxPathLength) {
        if (maxPathLength < -1) {
            throw new InvalidParameterException("the maximum path length parameter can not be less than -1");
        }
        this.maxPathLength = maxPathLength;
    }
    
    public int getMaxPathLength() {
        return this.maxPathLength;
    }
    
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("[\n");
        sb.append(super.toString());
        sb.append("  Maximum Path Length: " + this.maxPathLength + "\n");
        sb.append("]\n");
        return sb.toString();
    }
}
