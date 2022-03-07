package java.security.cert;

import java.security.*;

public class PKIXCertPathValidatorResult implements CertPathValidatorResult
{
    private TrustAnchor trustAnchor;
    private PolicyNode policyTree;
    private PublicKey subjectPublicKey;
    
    public PKIXCertPathValidatorResult(final TrustAnchor trustAnchor, final PolicyNode policyTree, final PublicKey subjectPublicKey) {
        if (subjectPublicKey == null) {
            throw new NullPointerException("subjectPublicKey must be non-null");
        }
        if (trustAnchor == null) {
            throw new NullPointerException("trustAnchor must be non-null");
        }
        this.trustAnchor = trustAnchor;
        this.policyTree = policyTree;
        this.subjectPublicKey = subjectPublicKey;
    }
    
    public TrustAnchor getTrustAnchor() {
        return this.trustAnchor;
    }
    
    public PolicyNode getPolicyTree() {
        return this.policyTree;
    }
    
    public PublicKey getPublicKey() {
        return this.subjectPublicKey;
    }
    
    @Override
    public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException ex) {
            throw new InternalError(ex.toString(), ex);
        }
    }
    
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("PKIXCertPathValidatorResult: [\n");
        sb.append("  Trust Anchor: " + this.trustAnchor.toString() + "\n");
        sb.append("  Policy Tree: " + String.valueOf(this.policyTree) + "\n");
        sb.append("  Subject Public Key: " + this.subjectPublicKey + "\n");
        sb.append("]");
        return sb.toString();
    }
}
