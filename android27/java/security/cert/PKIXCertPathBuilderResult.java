package java.security.cert;

import java.security.*;

public class PKIXCertPathBuilderResult extends PKIXCertPathValidatorResult implements CertPathBuilderResult
{
    private CertPath certPath;
    
    public PKIXCertPathBuilderResult(final CertPath certPath, final TrustAnchor trustAnchor, final PolicyNode policyNode, final PublicKey publicKey) {
        super(trustAnchor, policyNode, publicKey);
        if (certPath == null) {
            throw new NullPointerException("certPath must be non-null");
        }
        this.certPath = certPath;
    }
    
    @Override
    public CertPath getCertPath() {
        return this.certPath;
    }
    
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("PKIXCertPathBuilderResult: [\n");
        sb.append("  Certification Path: " + this.certPath + "\n");
        sb.append("  Trust Anchor: " + this.getTrustAnchor().toString() + "\n");
        sb.append("  Policy Tree: " + String.valueOf(this.getPolicyTree()) + "\n");
        sb.append("  Subject Public Key: " + this.getPublicKey() + "\n");
        sb.append("]");
        return sb.toString();
    }
}
