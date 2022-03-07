package java.security.cert;

import java.security.*;
import java.util.*;

public class PKIXParameters implements CertPathParameters
{
    private Set<TrustAnchor> unmodTrustAnchors;
    private Date date;
    private List<PKIXCertPathChecker> certPathCheckers;
    private String sigProvider;
    private boolean revocationEnabled;
    private Set<String> unmodInitialPolicies;
    private boolean explicitPolicyRequired;
    private boolean policyMappingInhibited;
    private boolean anyPolicyInhibited;
    private boolean policyQualifiersRejected;
    private List<CertStore> certStores;
    private CertSelector certSelector;
    
    public PKIXParameters(final Set<TrustAnchor> trustAnchors) throws InvalidAlgorithmParameterException {
        this.revocationEnabled = true;
        this.explicitPolicyRequired = false;
        this.policyMappingInhibited = false;
        this.anyPolicyInhibited = false;
        this.policyQualifiersRejected = true;
        this.setTrustAnchors(trustAnchors);
        this.unmodInitialPolicies = Collections.emptySet();
        this.certPathCheckers = new ArrayList<PKIXCertPathChecker>();
        this.certStores = new ArrayList<CertStore>();
    }
    
    public PKIXParameters(final KeyStore keyStore) throws KeyStoreException, InvalidAlgorithmParameterException {
        this.revocationEnabled = true;
        this.explicitPolicyRequired = false;
        this.policyMappingInhibited = false;
        this.anyPolicyInhibited = false;
        this.policyQualifiersRejected = true;
        if (keyStore == null) {
            throw new NullPointerException("the keystore parameter must be non-null");
        }
        final HashSet<TrustAnchor> trustAnchors = new HashSet<TrustAnchor>();
        final Enumeration<String> aliases = keyStore.aliases();
        while (aliases.hasMoreElements()) {
            final String s = aliases.nextElement();
            if (keyStore.isCertificateEntry(s)) {
                final Certificate certificate = keyStore.getCertificate(s);
                if (!(certificate instanceof X509Certificate)) {
                    continue;
                }
                trustAnchors.add(new TrustAnchor((X509Certificate)certificate, null));
            }
        }
        this.setTrustAnchors(trustAnchors);
        this.unmodInitialPolicies = Collections.emptySet();
        this.certPathCheckers = new ArrayList<PKIXCertPathChecker>();
        this.certStores = new ArrayList<CertStore>();
    }
    
    public Set<TrustAnchor> getTrustAnchors() {
        return this.unmodTrustAnchors;
    }
    
    public void setTrustAnchors(final Set<TrustAnchor> set) throws InvalidAlgorithmParameterException {
        if (set == null) {
            throw new NullPointerException("the trustAnchors parameters must be non-null");
        }
        if (set.isEmpty()) {
            throw new InvalidAlgorithmParameterException("the trustAnchors parameter must be non-empty");
        }
        final Iterator<Object> iterator = set.iterator();
        while (iterator.hasNext()) {
            if (!(iterator.next() instanceof TrustAnchor)) {
                throw new ClassCastException("all elements of set must be of type java.security.cert.TrustAnchor");
            }
        }
        this.unmodTrustAnchors = Collections.unmodifiableSet((Set<? extends TrustAnchor>)new HashSet<TrustAnchor>(set));
    }
    
    public Set<String> getInitialPolicies() {
        return this.unmodInitialPolicies;
    }
    
    public void setInitialPolicies(final Set<String> set) {
        if (set != null) {
            final Iterator<String> iterator = set.iterator();
            while (iterator.hasNext()) {
                if (!(iterator.next() instanceof String)) {
                    throw new ClassCastException("all elements of set must be of type java.lang.String");
                }
            }
            this.unmodInitialPolicies = Collections.unmodifiableSet((Set<? extends String>)new HashSet<String>(set));
        }
        else {
            this.unmodInitialPolicies = Collections.emptySet();
        }
    }
    
    public void setCertStores(final List<CertStore> list) {
        if (list == null) {
            this.certStores = new ArrayList<CertStore>();
        }
        else {
            final Iterator<CertStore> iterator = list.iterator();
            while (iterator.hasNext()) {
                if (!(iterator.next() instanceof CertStore)) {
                    throw new ClassCastException("all elements of list must be of type java.security.cert.CertStore");
                }
            }
            this.certStores = new ArrayList<CertStore>(list);
        }
    }
    
    public void addCertStore(final CertStore certStore) {
        if (certStore != null) {
            this.certStores.add(certStore);
        }
    }
    
    public List<CertStore> getCertStores() {
        return Collections.unmodifiableList((List<? extends CertStore>)new ArrayList<CertStore>(this.certStores));
    }
    
    public void setRevocationEnabled(final boolean revocationEnabled) {
        this.revocationEnabled = revocationEnabled;
    }
    
    public boolean isRevocationEnabled() {
        return this.revocationEnabled;
    }
    
    public void setExplicitPolicyRequired(final boolean explicitPolicyRequired) {
        this.explicitPolicyRequired = explicitPolicyRequired;
    }
    
    public boolean isExplicitPolicyRequired() {
        return this.explicitPolicyRequired;
    }
    
    public void setPolicyMappingInhibited(final boolean policyMappingInhibited) {
        this.policyMappingInhibited = policyMappingInhibited;
    }
    
    public boolean isPolicyMappingInhibited() {
        return this.policyMappingInhibited;
    }
    
    public void setAnyPolicyInhibited(final boolean anyPolicyInhibited) {
        this.anyPolicyInhibited = anyPolicyInhibited;
    }
    
    public boolean isAnyPolicyInhibited() {
        return this.anyPolicyInhibited;
    }
    
    public void setPolicyQualifiersRejected(final boolean policyQualifiersRejected) {
        this.policyQualifiersRejected = policyQualifiersRejected;
    }
    
    public boolean getPolicyQualifiersRejected() {
        return this.policyQualifiersRejected;
    }
    
    public Date getDate() {
        if (this.date == null) {
            return null;
        }
        return (Date)this.date.clone();
    }
    
    public void setDate(final Date date) {
        if (date != null) {
            this.date = (Date)date.clone();
        }
    }
    
    public void setCertPathCheckers(final List<PKIXCertPathChecker> list) {
        if (list != null) {
            final ArrayList<PKIXCertPathChecker> certPathCheckers = new ArrayList<PKIXCertPathChecker>();
            final Iterator<PKIXCertPathChecker> iterator = list.iterator();
            while (iterator.hasNext()) {
                certPathCheckers.add((PKIXCertPathChecker)iterator.next().clone());
            }
            this.certPathCheckers = certPathCheckers;
        }
        else {
            this.certPathCheckers = new ArrayList<PKIXCertPathChecker>();
        }
    }
    
    public List<PKIXCertPathChecker> getCertPathCheckers() {
        final ArrayList<PKIXCertPathChecker> list = new ArrayList<PKIXCertPathChecker>();
        final Iterator<PKIXCertPathChecker> iterator = this.certPathCheckers.iterator();
        while (iterator.hasNext()) {
            list.add((PKIXCertPathChecker)iterator.next().clone());
        }
        return (List<PKIXCertPathChecker>)Collections.unmodifiableList((List<?>)list);
    }
    
    public void addCertPathChecker(final PKIXCertPathChecker pkixCertPathChecker) {
        if (pkixCertPathChecker != null) {
            this.certPathCheckers.add((PKIXCertPathChecker)pkixCertPathChecker.clone());
        }
    }
    
    public String getSigProvider() {
        return this.sigProvider;
    }
    
    public void setSigProvider(final String sigProvider) {
        this.sigProvider = sigProvider;
    }
    
    public CertSelector getTargetCertConstraints() {
        if (this.certSelector != null) {
            return (CertSelector)this.certSelector.clone();
        }
        return null;
    }
    
    public void setTargetCertConstraints(final CertSelector certSelector) {
        if (certSelector != null) {
            this.certSelector = (CertSelector)certSelector.clone();
        }
        else {
            this.certSelector = null;
        }
    }
    
    @Override
    public Object clone() {
        try {
            final PKIXParameters pkixParameters = (PKIXParameters)super.clone();
            if (this.certStores != null) {
                pkixParameters.certStores = new ArrayList<CertStore>(this.certStores);
            }
            if (this.certPathCheckers != null) {
                pkixParameters.certPathCheckers = new ArrayList<PKIXCertPathChecker>(this.certPathCheckers.size());
                final Iterator<PKIXCertPathChecker> iterator = this.certPathCheckers.iterator();
                while (iterator.hasNext()) {
                    pkixParameters.certPathCheckers.add((PKIXCertPathChecker)iterator.next().clone());
                }
            }
            return pkixParameters;
        }
        catch (CloneNotSupportedException ex) {
            throw new InternalError(ex.toString(), ex);
        }
    }
    
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("[\n");
        if (this.unmodTrustAnchors != null) {
            sb.append("  Trust Anchors: " + this.unmodTrustAnchors.toString() + "\n");
        }
        if (this.unmodInitialPolicies != null) {
            if (this.unmodInitialPolicies.isEmpty()) {
                sb.append("  Initial Policy OIDs: any\n");
            }
            else {
                sb.append("  Initial Policy OIDs: [" + this.unmodInitialPolicies.toString() + "]\n");
            }
        }
        sb.append("  Validity Date: " + String.valueOf(this.date) + "\n");
        sb.append("  Signature Provider: " + String.valueOf(this.sigProvider) + "\n");
        sb.append("  Default Revocation Enabled: " + this.revocationEnabled + "\n");
        sb.append("  Explicit Policy Required: " + this.explicitPolicyRequired + "\n");
        sb.append("  Policy Mapping Inhibited: " + this.policyMappingInhibited + "\n");
        sb.append("  Any Policy Inhibited: " + this.anyPolicyInhibited + "\n");
        sb.append("  Policy Qualifiers Rejected: " + this.policyQualifiersRejected + "\n");
        sb.append("  Target Cert Constraints: " + String.valueOf(this.certSelector) + "\n");
        if (this.certPathCheckers != null) {
            sb.append("  Certification Path Checkers: [" + this.certPathCheckers.toString() + "]\n");
        }
        if (this.certStores != null) {
            sb.append("  CertStores: [" + this.certStores.toString() + "]\n");
        }
        sb.append("]");
        return sb.toString();
    }
}
