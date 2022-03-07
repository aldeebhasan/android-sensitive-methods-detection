package java.security.cert;

import java.math.*;
import javax.security.auth.x500.*;
import java.security.*;
import java.io.*;
import sun.misc.*;
import sun.security.util.*;
import java.util.*;
import sun.security.x509.*;

public class X509CertSelector implements CertSelector
{
    private static final Debug debug;
    private static final ObjectIdentifier ANY_EXTENDED_KEY_USAGE;
    private BigInteger serialNumber;
    private X500Principal issuer;
    private X500Principal subject;
    private byte[] subjectKeyID;
    private byte[] authorityKeyID;
    private Date certificateValid;
    private Date privateKeyValid;
    private ObjectIdentifier subjectPublicKeyAlgID;
    private PublicKey subjectPublicKey;
    private byte[] subjectPublicKeyBytes;
    private boolean[] keyUsage;
    private Set<String> keyPurposeSet;
    private Set<ObjectIdentifier> keyPurposeOIDSet;
    private Set<List<?>> subjectAlternativeNames;
    private Set<GeneralNameInterface> subjectAlternativeGeneralNames;
    private CertificatePolicySet policy;
    private Set<String> policySet;
    private Set<List<?>> pathToNames;
    private Set<GeneralNameInterface> pathToGeneralNames;
    private NameConstraintsExtension nc;
    private byte[] ncBytes;
    private int basicConstraints;
    private X509Certificate x509Cert;
    private boolean matchAllSubjectAltNames;
    private static final Boolean FALSE;
    private static final int PRIVATE_KEY_USAGE_ID = 0;
    private static final int SUBJECT_ALT_NAME_ID = 1;
    private static final int NAME_CONSTRAINTS_ID = 2;
    private static final int CERT_POLICIES_ID = 3;
    private static final int EXTENDED_KEY_USAGE_ID = 4;
    private static final int NUM_OF_EXTENSIONS = 5;
    private static final String[] EXTENSION_OIDS;
    static final int NAME_ANY = 0;
    static final int NAME_RFC822 = 1;
    static final int NAME_DNS = 2;
    static final int NAME_X400 = 3;
    static final int NAME_DIRECTORY = 4;
    static final int NAME_EDI = 5;
    static final int NAME_URI = 6;
    static final int NAME_IP = 7;
    static final int NAME_OID = 8;
    
    public X509CertSelector() {
        this.basicConstraints = -1;
        this.matchAllSubjectAltNames = true;
    }
    
    public void setCertificate(final X509Certificate x509Cert) {
        this.x509Cert = x509Cert;
    }
    
    public void setSerialNumber(final BigInteger serialNumber) {
        this.serialNumber = serialNumber;
    }
    
    public void setIssuer(final X500Principal issuer) {
        this.issuer = issuer;
    }
    
    public void setIssuer(final String s) throws IOException {
        if (s == null) {
            this.issuer = null;
        }
        else {
            this.issuer = new X500Name(s).asX500Principal();
        }
    }
    
    public void setIssuer(final byte[] array) throws IOException {
        try {
            this.issuer = ((array == null) ? null : new X500Principal(array));
        }
        catch (IllegalArgumentException ex) {
            throw new IOException("Invalid name", ex);
        }
    }
    
    public void setSubject(final X500Principal subject) {
        this.subject = subject;
    }
    
    public void setSubject(final String s) throws IOException {
        if (s == null) {
            this.subject = null;
        }
        else {
            this.subject = new X500Name(s).asX500Principal();
        }
    }
    
    public void setSubject(final byte[] array) throws IOException {
        try {
            this.subject = ((array == null) ? null : new X500Principal(array));
        }
        catch (IllegalArgumentException ex) {
            throw new IOException("Invalid name", ex);
        }
    }
    
    public void setSubjectKeyIdentifier(final byte[] array) {
        if (array == null) {
            this.subjectKeyID = null;
        }
        else {
            this.subjectKeyID = array.clone();
        }
    }
    
    public void setAuthorityKeyIdentifier(final byte[] array) {
        if (array == null) {
            this.authorityKeyID = null;
        }
        else {
            this.authorityKeyID = array.clone();
        }
    }
    
    public void setCertificateValid(final Date date) {
        if (date == null) {
            this.certificateValid = null;
        }
        else {
            this.certificateValid = (Date)date.clone();
        }
    }
    
    public void setPrivateKeyValid(final Date date) {
        if (date == null) {
            this.privateKeyValid = null;
        }
        else {
            this.privateKeyValid = (Date)date.clone();
        }
    }
    
    public void setSubjectPublicKeyAlgID(final String s) throws IOException {
        if (s == null) {
            this.subjectPublicKeyAlgID = null;
        }
        else {
            this.subjectPublicKeyAlgID = new ObjectIdentifier(s);
        }
    }
    
    public void setSubjectPublicKey(final PublicKey subjectPublicKey) {
        if (subjectPublicKey == null) {
            this.subjectPublicKey = null;
            this.subjectPublicKeyBytes = null;
        }
        else {
            this.subjectPublicKey = subjectPublicKey;
            this.subjectPublicKeyBytes = subjectPublicKey.getEncoded();
        }
    }
    
    public void setSubjectPublicKey(final byte[] array) throws IOException {
        if (array == null) {
            this.subjectPublicKey = null;
            this.subjectPublicKeyBytes = null;
        }
        else {
            this.subjectPublicKeyBytes = array.clone();
            this.subjectPublicKey = X509Key.parse(new DerValue(this.subjectPublicKeyBytes));
        }
    }
    
    public void setKeyUsage(final boolean[] array) {
        if (array == null) {
            this.keyUsage = null;
        }
        else {
            this.keyUsage = array.clone();
        }
    }
    
    public void setExtendedKeyUsage(final Set<String> set) throws IOException {
        if (set == null || set.isEmpty()) {
            this.keyPurposeSet = null;
            this.keyPurposeOIDSet = null;
        }
        else {
            this.keyPurposeSet = Collections.unmodifiableSet((Set<? extends String>)new HashSet<String>(set));
            this.keyPurposeOIDSet = new HashSet<ObjectIdentifier>();
            final Iterator<String> iterator = this.keyPurposeSet.iterator();
            while (iterator.hasNext()) {
                this.keyPurposeOIDSet.add(new ObjectIdentifier(iterator.next()));
            }
        }
    }
    
    public void setMatchAllSubjectAltNames(final boolean matchAllSubjectAltNames) {
        this.matchAllSubjectAltNames = matchAllSubjectAltNames;
    }
    
    public void setSubjectAlternativeNames(final Collection<List<?>> collection) throws IOException {
        if (collection == null) {
            this.subjectAlternativeNames = null;
            this.subjectAlternativeGeneralNames = null;
        }
        else {
            if (collection.isEmpty()) {
                this.subjectAlternativeNames = null;
                this.subjectAlternativeGeneralNames = null;
                return;
            }
            final Set<List<?>> cloneAndCheckNames = cloneAndCheckNames(collection);
            this.subjectAlternativeGeneralNames = parseNames(cloneAndCheckNames);
            this.subjectAlternativeNames = cloneAndCheckNames;
        }
    }
    
    public void addSubjectAlternativeName(final int n, final String s) throws IOException {
        this.addSubjectAlternativeNameInternal(n, s);
    }
    
    public void addSubjectAlternativeName(final int n, final byte[] array) throws IOException {
        this.addSubjectAlternativeNameInternal(n, array.clone());
    }
    
    private void addSubjectAlternativeNameInternal(final int n, final Object o) throws IOException {
        final GeneralNameInterface generalNameInterface = makeGeneralNameInterface(n, o);
        if (this.subjectAlternativeNames == null) {
            this.subjectAlternativeNames = new HashSet<List<?>>();
        }
        if (this.subjectAlternativeGeneralNames == null) {
            this.subjectAlternativeGeneralNames = new HashSet<GeneralNameInterface>();
        }
        final ArrayList<Integer> list = new ArrayList<Integer>(2);
        list.add(n);
        list.add((Integer)o);
        this.subjectAlternativeNames.add(list);
        this.subjectAlternativeGeneralNames.add(generalNameInterface);
    }
    
    private static Set<GeneralNameInterface> parseNames(final Collection<List<?>> collection) throws IOException {
        final HashSet<GeneralNameInterface> set = new HashSet<GeneralNameInterface>();
        for (final List<Object> list : collection) {
            if (list.size() != 2) {
                throw new IOException("name list size not 2");
            }
            final Object value = list.get(0);
            if (!(value instanceof Integer)) {
                throw new IOException("expected an Integer");
            }
            set.add(makeGeneralNameInterface((int)value, list.get(1)));
        }
        return set;
    }
    
    static boolean equalNames(final Collection<?> collection, final Collection<?> collection2) {
        if (collection == null || collection2 == null) {
            return collection == collection2;
        }
        return collection.equals(collection2);
    }
    
    static GeneralNameInterface makeGeneralNameInterface(final int n, final Object o) throws IOException {
        if (X509CertSelector.debug != null) {
            X509CertSelector.debug.println("X509CertSelector.makeGeneralNameInterface(" + n + ")...");
        }
        GeneralNameInterface generalNameInterface = null;
        if (o instanceof String) {
            if (X509CertSelector.debug != null) {
                X509CertSelector.debug.println("X509CertSelector.makeGeneralNameInterface() name is String: " + o);
            }
            switch (n) {
                case 1: {
                    generalNameInterface = new RFC822Name((String)o);
                    break;
                }
                case 2: {
                    generalNameInterface = new DNSName((String)o);
                    break;
                }
                case 4: {
                    generalNameInterface = new X500Name((String)o);
                    break;
                }
                case 6: {
                    generalNameInterface = new URIName((String)o);
                    break;
                }
                case 7: {
                    generalNameInterface = new IPAddressName((String)o);
                    break;
                }
                case 8: {
                    generalNameInterface = new OIDName((String)o);
                    break;
                }
                default: {
                    throw new IOException("unable to parse String names of type " + n);
                }
            }
            if (X509CertSelector.debug != null) {
                X509CertSelector.debug.println("X509CertSelector.makeGeneralNameInterface() result: " + generalNameInterface.toString());
            }
        }
        else {
            if (!(o instanceof byte[])) {
                if (X509CertSelector.debug != null) {
                    X509CertSelector.debug.println("X509CertSelector.makeGeneralName() input name not String or byte array");
                }
                throw new IOException("name not String or byte array");
            }
            final DerValue derValue = new DerValue((byte[])o);
            if (X509CertSelector.debug != null) {
                X509CertSelector.debug.println("X509CertSelector.makeGeneralNameInterface() is byte[]");
            }
            switch (n) {
                case 0: {
                    generalNameInterface = new OtherName(derValue);
                    break;
                }
                case 1: {
                    generalNameInterface = new RFC822Name(derValue);
                    break;
                }
                case 2: {
                    generalNameInterface = new DNSName(derValue);
                    break;
                }
                case 3: {
                    generalNameInterface = new X400Address(derValue);
                    break;
                }
                case 4: {
                    generalNameInterface = new X500Name(derValue);
                    break;
                }
                case 5: {
                    generalNameInterface = new EDIPartyName(derValue);
                    break;
                }
                case 6: {
                    generalNameInterface = new URIName(derValue);
                    break;
                }
                case 7: {
                    generalNameInterface = new IPAddressName(derValue);
                    break;
                }
                case 8: {
                    generalNameInterface = new OIDName(derValue);
                    break;
                }
                default: {
                    throw new IOException("unable to parse byte array names of type " + n);
                }
            }
            if (X509CertSelector.debug != null) {
                X509CertSelector.debug.println("X509CertSelector.makeGeneralNameInterface() result: " + generalNameInterface.toString());
            }
        }
        return generalNameInterface;
    }
    
    public void setNameConstraints(final byte[] array) throws IOException {
        if (array == null) {
            this.ncBytes = null;
            this.nc = null;
        }
        else {
            this.ncBytes = array.clone();
            this.nc = new NameConstraintsExtension(X509CertSelector.FALSE, array);
        }
    }
    
    public void setBasicConstraints(final int basicConstraints) {
        if (basicConstraints < -2) {
            throw new IllegalArgumentException("basic constraints less than -2");
        }
        this.basicConstraints = basicConstraints;
    }
    
    public void setPolicy(final Set<String> set) throws IOException {
        if (set == null) {
            this.policySet = null;
            this.policy = null;
        }
        else {
            final Set<String> unmodifiableSet = Collections.unmodifiableSet((Set<? extends String>)new HashSet<String>(set));
            final Iterator<String> iterator = unmodifiableSet.iterator();
            final Vector<CertificatePolicyId> vector = new Vector<CertificatePolicyId>();
            while (iterator.hasNext()) {
                final String next = iterator.next();
                if (!(next instanceof String)) {
                    throw new IOException("non String in certPolicySet");
                }
                vector.add(new CertificatePolicyId(new ObjectIdentifier(next)));
            }
            this.policySet = unmodifiableSet;
            this.policy = new CertificatePolicySet(vector);
        }
    }
    
    public void setPathToNames(final Collection<List<?>> collection) throws IOException {
        if (collection == null || collection.isEmpty()) {
            this.pathToNames = null;
            this.pathToGeneralNames = null;
        }
        else {
            final Set<List<?>> cloneAndCheckNames = cloneAndCheckNames(collection);
            this.pathToGeneralNames = parseNames(cloneAndCheckNames);
            this.pathToNames = cloneAndCheckNames;
        }
    }
    
    void setPathToNamesInternal(final Set<GeneralNameInterface> pathToGeneralNames) {
        this.pathToNames = Collections.emptySet();
        this.pathToGeneralNames = pathToGeneralNames;
    }
    
    public void addPathToName(final int n, final String s) throws IOException {
        this.addPathToNameInternal(n, s);
    }
    
    public void addPathToName(final int n, final byte[] array) throws IOException {
        this.addPathToNameInternal(n, array.clone());
    }
    
    private void addPathToNameInternal(final int n, final Object o) throws IOException {
        final GeneralNameInterface generalNameInterface = makeGeneralNameInterface(n, o);
        if (this.pathToGeneralNames == null) {
            this.pathToNames = new HashSet<List<?>>();
            this.pathToGeneralNames = new HashSet<GeneralNameInterface>();
        }
        final ArrayList<Integer> list = new ArrayList<Integer>(2);
        list.add(n);
        list.add((Integer)o);
        this.pathToNames.add(list);
        this.pathToGeneralNames.add(generalNameInterface);
    }
    
    public X509Certificate getCertificate() {
        return this.x509Cert;
    }
    
    public BigInteger getSerialNumber() {
        return this.serialNumber;
    }
    
    public X500Principal getIssuer() {
        return this.issuer;
    }
    
    public String getIssuerAsString() {
        return (this.issuer == null) ? null : this.issuer.getName();
    }
    
    public byte[] getIssuerAsBytes() throws IOException {
        return (byte[])((this.issuer == null) ? null : this.issuer.getEncoded());
    }
    
    public X500Principal getSubject() {
        return this.subject;
    }
    
    public String getSubjectAsString() {
        return (this.subject == null) ? null : this.subject.getName();
    }
    
    public byte[] getSubjectAsBytes() throws IOException {
        return (byte[])((this.subject == null) ? null : this.subject.getEncoded());
    }
    
    public byte[] getSubjectKeyIdentifier() {
        if (this.subjectKeyID == null) {
            return null;
        }
        return this.subjectKeyID.clone();
    }
    
    public byte[] getAuthorityKeyIdentifier() {
        if (this.authorityKeyID == null) {
            return null;
        }
        return this.authorityKeyID.clone();
    }
    
    public Date getCertificateValid() {
        if (this.certificateValid == null) {
            return null;
        }
        return (Date)this.certificateValid.clone();
    }
    
    public Date getPrivateKeyValid() {
        if (this.privateKeyValid == null) {
            return null;
        }
        return (Date)this.privateKeyValid.clone();
    }
    
    public String getSubjectPublicKeyAlgID() {
        if (this.subjectPublicKeyAlgID == null) {
            return null;
        }
        return this.subjectPublicKeyAlgID.toString();
    }
    
    public PublicKey getSubjectPublicKey() {
        return this.subjectPublicKey;
    }
    
    public boolean[] getKeyUsage() {
        if (this.keyUsage == null) {
            return null;
        }
        return this.keyUsage.clone();
    }
    
    public Set<String> getExtendedKeyUsage() {
        return this.keyPurposeSet;
    }
    
    public boolean getMatchAllSubjectAltNames() {
        return this.matchAllSubjectAltNames;
    }
    
    public Collection<List<?>> getSubjectAlternativeNames() {
        if (this.subjectAlternativeNames == null) {
            return null;
        }
        return cloneNames(this.subjectAlternativeNames);
    }
    
    private static Set<List<?>> cloneNames(final Collection<List<?>> collection) {
        try {
            return cloneAndCheckNames(collection);
        }
        catch (IOException ex) {
            throw new RuntimeException("cloneNames encountered IOException: " + ex.getMessage());
        }
    }
    
    private static Set<List<?>> cloneAndCheckNames(final Collection<List<?>> collection) throws IOException {
        final HashSet<ArrayList<Object>> set = new HashSet<ArrayList<Object>>();
        final Iterator<List<?>> iterator = collection.iterator();
        while (iterator.hasNext()) {
            set.add(new ArrayList<Object>(iterator.next()));
        }
        for (final ArrayList<Object> list : set) {
            if (list.size() != 2) {
                throw new IOException("name list size not 2");
            }
            final Object value = list.get(0);
            if (!(value instanceof Integer)) {
                throw new IOException("expected an Integer");
            }
            final int intValue = (int)value;
            if (intValue < 0 || intValue > 8) {
                throw new IOException("name type not 0-8");
            }
            final Object value2 = list.get(1);
            if (!(value2 instanceof byte[]) && !(value2 instanceof String)) {
                if (X509CertSelector.debug != null) {
                    X509CertSelector.debug.println("X509CertSelector.cloneAndCheckNames() name not byte array");
                }
                throw new IOException("name not byte array or String");
            }
            if (!(value2 instanceof byte[])) {
                continue;
            }
            list.set(1, ((byte[])value2).clone());
        }
        return (Set<List<?>>)set;
    }
    
    public byte[] getNameConstraints() {
        if (this.ncBytes == null) {
            return null;
        }
        return this.ncBytes.clone();
    }
    
    public int getBasicConstraints() {
        return this.basicConstraints;
    }
    
    public Set<String> getPolicy() {
        return this.policySet;
    }
    
    public Collection<List<?>> getPathToNames() {
        if (this.pathToNames == null) {
            return null;
        }
        return cloneNames(this.pathToNames);
    }
    
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("X509CertSelector: [\n");
        if (this.x509Cert != null) {
            sb.append("  Certificate: " + this.x509Cert.toString() + "\n");
        }
        if (this.serialNumber != null) {
            sb.append("  Serial Number: " + this.serialNumber.toString() + "\n");
        }
        if (this.issuer != null) {
            sb.append("  Issuer: " + this.getIssuerAsString() + "\n");
        }
        if (this.subject != null) {
            sb.append("  Subject: " + this.getSubjectAsString() + "\n");
        }
        sb.append("  matchAllSubjectAltNames flag: " + String.valueOf(this.matchAllSubjectAltNames) + "\n");
        if (this.subjectAlternativeNames != null) {
            sb.append("  SubjectAlternativeNames:\n");
            for (final List<Object> list : this.subjectAlternativeNames) {
                sb.append("    type " + list.get(0) + ", name " + list.get(1) + "\n");
            }
        }
        if (this.subjectKeyID != null) {
            sb.append("  Subject Key Identifier: " + new HexDumpEncoder().encodeBuffer(this.subjectKeyID) + "\n");
        }
        if (this.authorityKeyID != null) {
            sb.append("  Authority Key Identifier: " + new HexDumpEncoder().encodeBuffer(this.authorityKeyID) + "\n");
        }
        if (this.certificateValid != null) {
            sb.append("  Certificate Valid: " + this.certificateValid.toString() + "\n");
        }
        if (this.privateKeyValid != null) {
            sb.append("  Private Key Valid: " + this.privateKeyValid.toString() + "\n");
        }
        if (this.subjectPublicKeyAlgID != null) {
            sb.append("  Subject Public Key AlgID: " + this.subjectPublicKeyAlgID.toString() + "\n");
        }
        if (this.subjectPublicKey != null) {
            sb.append("  Subject Public Key: " + this.subjectPublicKey.toString() + "\n");
        }
        if (this.keyUsage != null) {
            sb.append("  Key Usage: " + keyUsageToString(this.keyUsage) + "\n");
        }
        if (this.keyPurposeSet != null) {
            sb.append("  Extended Key Usage: " + this.keyPurposeSet.toString() + "\n");
        }
        if (this.policy != null) {
            sb.append("  Policy: " + this.policy.toString() + "\n");
        }
        if (this.pathToGeneralNames != null) {
            sb.append("  Path to names:\n");
            final Iterator<GeneralNameInterface> iterator2 = this.pathToGeneralNames.iterator();
            while (iterator2.hasNext()) {
                sb.append("    " + iterator2.next() + "\n");
            }
        }
        sb.append("]");
        return sb.toString();
    }
    
    private static String keyUsageToString(final boolean[] array) {
        String s = "KeyUsage [\n";
        try {
            if (array[0]) {
                s += "  DigitalSignature\n";
            }
            if (array[1]) {
                s += "  Non_repudiation\n";
            }
            if (array[2]) {
                s += "  Key_Encipherment\n";
            }
            if (array[3]) {
                s += "  Data_Encipherment\n";
            }
            if (array[4]) {
                s += "  Key_Agreement\n";
            }
            if (array[5]) {
                s += "  Key_CertSign\n";
            }
            if (array[6]) {
                s += "  Crl_Sign\n";
            }
            if (array[7]) {
                s += "  Encipher_Only\n";
            }
            if (array[8]) {
                s += "  Decipher_Only\n";
            }
        }
        catch (ArrayIndexOutOfBoundsException ex) {}
        return s + "]\n";
    }
    
    private static Extension getExtensionObject(final X509Certificate x509Certificate, final int n) throws IOException {
        if (x509Certificate instanceof X509CertImpl) {
            final X509CertImpl x509CertImpl = (X509CertImpl)x509Certificate;
            switch (n) {
                case 0: {
                    return x509CertImpl.getPrivateKeyUsageExtension();
                }
                case 1: {
                    return x509CertImpl.getSubjectAlternativeNameExtension();
                }
                case 2: {
                    return x509CertImpl.getNameConstraintsExtension();
                }
                case 3: {
                    return x509CertImpl.getCertificatePoliciesExtension();
                }
                case 4: {
                    return x509CertImpl.getExtendedKeyUsageExtension();
                }
                default: {
                    return null;
                }
            }
        }
        else {
            final byte[] extensionValue = x509Certificate.getExtensionValue(X509CertSelector.EXTENSION_OIDS[n]);
            if (extensionValue == null) {
                return null;
            }
            final byte[] octetString = new DerInputStream(extensionValue).getOctetString();
            switch (n) {
                case 0: {
                    try {
                        return new PrivateKeyUsageExtension(X509CertSelector.FALSE, octetString);
                    }
                    catch (CertificateException ex) {
                        throw new IOException(ex.getMessage());
                    }
                }
                case 1: {
                    return new SubjectAlternativeNameExtension(X509CertSelector.FALSE, octetString);
                }
                case 2: {
                    return new NameConstraintsExtension(X509CertSelector.FALSE, octetString);
                }
                case 3: {
                    return new CertificatePoliciesExtension(X509CertSelector.FALSE, octetString);
                }
                case 4: {
                    return new ExtendedKeyUsageExtension(X509CertSelector.FALSE, octetString);
                }
                default: {
                    return null;
                }
            }
        }
    }
    
    @Override
    public boolean match(final Certificate certificate) {
        if (!(certificate instanceof X509Certificate)) {
            return false;
        }
        final X509Certificate x509Certificate = (X509Certificate)certificate;
        if (X509CertSelector.debug != null) {
            X509CertSelector.debug.println("X509CertSelector.match(SN: " + x509Certificate.getSerialNumber().toString(16) + "\n  Issuer: " + x509Certificate.getIssuerDN() + "\n  Subject: " + x509Certificate.getSubjectDN() + ")");
        }
        if (this.x509Cert != null && !this.x509Cert.equals(x509Certificate)) {
            if (X509CertSelector.debug != null) {
                X509CertSelector.debug.println("X509CertSelector.match: certs don't match");
            }
            return false;
        }
        if (this.serialNumber != null && !this.serialNumber.equals(x509Certificate.getSerialNumber())) {
            if (X509CertSelector.debug != null) {
                X509CertSelector.debug.println("X509CertSelector.match: serial numbers don't match");
            }
            return false;
        }
        if (this.issuer != null && !this.issuer.equals(x509Certificate.getIssuerX500Principal())) {
            if (X509CertSelector.debug != null) {
                X509CertSelector.debug.println("X509CertSelector.match: issuer DNs don't match");
            }
            return false;
        }
        if (this.subject != null && !this.subject.equals(x509Certificate.getSubjectX500Principal())) {
            if (X509CertSelector.debug != null) {
                X509CertSelector.debug.println("X509CertSelector.match: subject DNs don't match");
            }
            return false;
        }
        if (this.certificateValid != null) {
            try {
                x509Certificate.checkValidity(this.certificateValid);
            }
            catch (CertificateException ex) {
                if (X509CertSelector.debug != null) {
                    X509CertSelector.debug.println("X509CertSelector.match: certificate not within validity period");
                }
                return false;
            }
        }
        if (this.subjectPublicKeyBytes != null && !Arrays.equals(this.subjectPublicKeyBytes, x509Certificate.getPublicKey().getEncoded())) {
            if (X509CertSelector.debug != null) {
                X509CertSelector.debug.println("X509CertSelector.match: subject public keys don't match");
            }
            return false;
        }
        final boolean b = this.matchBasicConstraints(x509Certificate) && this.matchKeyUsage(x509Certificate) && this.matchExtendedKeyUsage(x509Certificate) && this.matchSubjectKeyID(x509Certificate) && this.matchAuthorityKeyID(x509Certificate) && this.matchPrivateKeyValid(x509Certificate) && this.matchSubjectPublicKeyAlgID(x509Certificate) && this.matchPolicy(x509Certificate) && this.matchSubjectAlternativeNames(x509Certificate) && this.matchPathToNames(x509Certificate) && this.matchNameConstraints(x509Certificate);
        if (b && X509CertSelector.debug != null) {
            X509CertSelector.debug.println("X509CertSelector.match returning: true");
        }
        return b;
    }
    
    private boolean matchSubjectKeyID(final X509Certificate x509Certificate) {
        if (this.subjectKeyID == null) {
            return true;
        }
        try {
            final byte[] extensionValue = x509Certificate.getExtensionValue("2.5.29.14");
            if (extensionValue == null) {
                if (X509CertSelector.debug != null) {
                    X509CertSelector.debug.println("X509CertSelector.match: no subject key ID extension");
                }
                return false;
            }
            final byte[] octetString = new DerInputStream(extensionValue).getOctetString();
            if (octetString == null || !Arrays.equals(this.subjectKeyID, octetString)) {
                if (X509CertSelector.debug != null) {
                    X509CertSelector.debug.println("X509CertSelector.match: subject key IDs don't match\nX509CertSelector.match: subjectKeyID: " + Arrays.toString(this.subjectKeyID) + "\nX509CertSelector.match: certSubjectKeyID: " + Arrays.toString(octetString));
                }
                return false;
            }
        }
        catch (IOException ex) {
            if (X509CertSelector.debug != null) {
                X509CertSelector.debug.println("X509CertSelector.match: exception in subject key ID check");
            }
            return false;
        }
        return true;
    }
    
    private boolean matchAuthorityKeyID(final X509Certificate x509Certificate) {
        if (this.authorityKeyID == null) {
            return true;
        }
        try {
            final byte[] extensionValue = x509Certificate.getExtensionValue("2.5.29.35");
            if (extensionValue == null) {
                if (X509CertSelector.debug != null) {
                    X509CertSelector.debug.println("X509CertSelector.match: no authority key ID extension");
                }
                return false;
            }
            final byte[] octetString = new DerInputStream(extensionValue).getOctetString();
            if (octetString == null || !Arrays.equals(this.authorityKeyID, octetString)) {
                if (X509CertSelector.debug != null) {
                    X509CertSelector.debug.println("X509CertSelector.match: authority key IDs don't match");
                }
                return false;
            }
        }
        catch (IOException ex) {
            if (X509CertSelector.debug != null) {
                X509CertSelector.debug.println("X509CertSelector.match: exception in authority key ID check");
            }
            return false;
        }
        return true;
    }
    
    private boolean matchPrivateKeyValid(final X509Certificate x509Certificate) {
        if (this.privateKeyValid == null) {
            return true;
        }
        PrivateKeyUsageExtension privateKeyUsageExtension = null;
        try {
            privateKeyUsageExtension = (PrivateKeyUsageExtension)getExtensionObject(x509Certificate, 0);
            if (privateKeyUsageExtension != null) {
                privateKeyUsageExtension.valid(this.privateKeyValid);
            }
        }
        catch (CertificateExpiredException ex) {
            if (X509CertSelector.debug != null) {
                String string = "n/a";
                try {
                    string = privateKeyUsageExtension.get("not_after").toString();
                }
                catch (CertificateException ex4) {}
                X509CertSelector.debug.println("X509CertSelector.match: private key usage not within validity date; ext.NOT_After: " + string + "; X509CertSelector: " + this.toString());
                ex.printStackTrace();
            }
            return false;
        }
        catch (CertificateNotYetValidException ex2) {
            if (X509CertSelector.debug != null) {
                String string2 = "n/a";
                try {
                    string2 = privateKeyUsageExtension.get("not_before").toString();
                }
                catch (CertificateException ex5) {}
                X509CertSelector.debug.println("X509CertSelector.match: private key usage not within validity date; ext.NOT_BEFORE: " + string2 + "; X509CertSelector: " + this.toString());
                ex2.printStackTrace();
            }
            return false;
        }
        catch (IOException ex3) {
            if (X509CertSelector.debug != null) {
                X509CertSelector.debug.println("X509CertSelector.match: IOException in private key usage check; X509CertSelector: " + this.toString());
                ex3.printStackTrace();
            }
            return false;
        }
        return true;
    }
    
    private boolean matchSubjectPublicKeyAlgID(final X509Certificate x509Certificate) {
        if (this.subjectPublicKeyAlgID == null) {
            return true;
        }
        try {
            final DerValue derValue = new DerValue(x509Certificate.getPublicKey().getEncoded());
            if (derValue.tag != 48) {
                throw new IOException("invalid key format");
            }
            final AlgorithmId parse = AlgorithmId.parse(derValue.data.getDerValue());
            if (X509CertSelector.debug != null) {
                X509CertSelector.debug.println("X509CertSelector.match: subjectPublicKeyAlgID = " + this.subjectPublicKeyAlgID + ", xcert subjectPublicKeyAlgID = " + parse.getOID());
            }
            if (!this.subjectPublicKeyAlgID.equals((Object)parse.getOID())) {
                if (X509CertSelector.debug != null) {
                    X509CertSelector.debug.println("X509CertSelector.match: subject public key alg IDs don't match");
                }
                return false;
            }
        }
        catch (IOException ex) {
            if (X509CertSelector.debug != null) {
                X509CertSelector.debug.println("X509CertSelector.match: IOException in subject public key algorithm OID check");
            }
            return false;
        }
        return true;
    }
    
    private boolean matchKeyUsage(final X509Certificate x509Certificate) {
        if (this.keyUsage == null) {
            return true;
        }
        final boolean[] keyUsage = x509Certificate.getKeyUsage();
        if (keyUsage != null) {
            for (int i = 0; i < this.keyUsage.length; ++i) {
                if (this.keyUsage[i] && (i >= keyUsage.length || !keyUsage[i])) {
                    if (X509CertSelector.debug != null) {
                        X509CertSelector.debug.println("X509CertSelector.match: key usage bits don't match");
                    }
                    return false;
                }
            }
        }
        return true;
    }
    
    private boolean matchExtendedKeyUsage(final X509Certificate x509Certificate) {
        if (this.keyPurposeSet == null || this.keyPurposeSet.isEmpty()) {
            return true;
        }
        try {
            final ExtendedKeyUsageExtension extendedKeyUsageExtension = (ExtendedKeyUsageExtension)getExtensionObject(x509Certificate, 4);
            if (extendedKeyUsageExtension != null) {
                final Vector<ObjectIdentifier> value = extendedKeyUsageExtension.get("usages");
                if (!value.contains(X509CertSelector.ANY_EXTENDED_KEY_USAGE) && !value.containsAll(this.keyPurposeOIDSet)) {
                    if (X509CertSelector.debug != null) {
                        X509CertSelector.debug.println("X509CertSelector.match: cert failed extendedKeyUsage criterion");
                    }
                    return false;
                }
            }
        }
        catch (IOException ex) {
            if (X509CertSelector.debug != null) {
                X509CertSelector.debug.println("X509CertSelector.match: IOException in extended key usage check");
            }
            return false;
        }
        return true;
    }
    
    private boolean matchSubjectAlternativeNames(final X509Certificate x509Certificate) {
        if (this.subjectAlternativeNames == null || this.subjectAlternativeNames.isEmpty()) {
            return true;
        }
        try {
            final SubjectAlternativeNameExtension subjectAlternativeNameExtension = (SubjectAlternativeNameExtension)getExtensionObject(x509Certificate, 1);
            if (subjectAlternativeNameExtension == null) {
                if (X509CertSelector.debug != null) {
                    X509CertSelector.debug.println("X509CertSelector.match: no subject alternative name extension");
                }
                return false;
            }
            final GeneralNames value = subjectAlternativeNameExtension.get("subject_name");
            final Iterator<GeneralNameInterface> iterator = this.subjectAlternativeGeneralNames.iterator();
            while (iterator.hasNext()) {
                final GeneralNameInterface generalNameInterface = iterator.next();
                boolean equals = false;
                for (Iterator<GeneralName> iterator2 = value.iterator(); iterator2.hasNext() && !equals; equals = iterator2.next().getName().equals(generalNameInterface)) {}
                if (!equals && (this.matchAllSubjectAltNames || !iterator.hasNext())) {
                    if (X509CertSelector.debug != null) {
                        X509CertSelector.debug.println("X509CertSelector.match: subject alternative name " + generalNameInterface + " not found");
                    }
                    return false;
                }
                if (equals && !this.matchAllSubjectAltNames) {
                    break;
                }
            }
        }
        catch (IOException ex) {
            if (X509CertSelector.debug != null) {
                X509CertSelector.debug.println("X509CertSelector.match: IOException in subject alternative name check");
            }
            return false;
        }
        return true;
    }
    
    private boolean matchNameConstraints(final X509Certificate x509Certificate) {
        if (this.nc == null) {
            return true;
        }
        try {
            if (!this.nc.verify(x509Certificate)) {
                if (X509CertSelector.debug != null) {
                    X509CertSelector.debug.println("X509CertSelector.match: name constraints not satisfied");
                }
                return false;
            }
        }
        catch (IOException ex) {
            if (X509CertSelector.debug != null) {
                X509CertSelector.debug.println("X509CertSelector.match: IOException in name constraints check");
            }
            return false;
        }
        return true;
    }
    
    private boolean matchPolicy(final X509Certificate x509Certificate) {
        if (this.policy == null) {
            return true;
        }
        try {
            final CertificatePoliciesExtension certificatePoliciesExtension = (CertificatePoliciesExtension)getExtensionObject(x509Certificate, 3);
            if (certificatePoliciesExtension == null) {
                if (X509CertSelector.debug != null) {
                    X509CertSelector.debug.println("X509CertSelector.match: no certificate policy extension");
                }
                return false;
            }
            final List<PolicyInformation> value = certificatePoliciesExtension.get("policies");
            final ArrayList list = new ArrayList<CertificatePolicyId>(value.size());
            final Iterator<PolicyInformation> iterator = value.iterator();
            while (iterator.hasNext()) {
                list.add(iterator.next().getPolicyIdentifier());
            }
            if (this.policy != null) {
                boolean b = false;
                if (this.policy.getCertPolicyIds().isEmpty()) {
                    if (list.isEmpty()) {
                        if (X509CertSelector.debug != null) {
                            X509CertSelector.debug.println("X509CertSelector.match: cert failed policyAny criterion");
                        }
                        return false;
                    }
                }
                else {
                    final Iterator<CertificatePolicyId> iterator2 = this.policy.getCertPolicyIds().iterator();
                    while (iterator2.hasNext()) {
                        if (list.contains(iterator2.next())) {
                            b = true;
                            break;
                        }
                    }
                    if (!b) {
                        if (X509CertSelector.debug != null) {
                            X509CertSelector.debug.println("X509CertSelector.match: cert failed policyAny criterion");
                        }
                        return false;
                    }
                }
            }
        }
        catch (IOException ex) {
            if (X509CertSelector.debug != null) {
                X509CertSelector.debug.println("X509CertSelector.match: IOException in certificate policy ID check");
            }
            return false;
        }
        return true;
    }
    
    private boolean matchPathToNames(final X509Certificate x509Certificate) {
        if (this.pathToGeneralNames == null) {
            return true;
        }
        try {
            final NameConstraintsExtension nameConstraintsExtension = (NameConstraintsExtension)getExtensionObject(x509Certificate, 2);
            if (nameConstraintsExtension == null) {
                return true;
            }
            if (X509CertSelector.debug != null && Debug.isOn("certpath")) {
                X509CertSelector.debug.println("X509CertSelector.match pathToNames:\n");
                final Iterator<GeneralNameInterface> iterator = this.pathToGeneralNames.iterator();
                while (iterator.hasNext()) {
                    X509CertSelector.debug.println("    " + iterator.next() + "\n");
                }
            }
            final GeneralSubtrees value = nameConstraintsExtension.get("permitted_subtrees");
            final GeneralSubtrees value2 = nameConstraintsExtension.get("excluded_subtrees");
            if (value2 != null && !this.matchExcluded(value2)) {
                return false;
            }
            if (value != null && !this.matchPermitted(value)) {
                return false;
            }
        }
        catch (IOException ex) {
            if (X509CertSelector.debug != null) {
                X509CertSelector.debug.println("X509CertSelector.match: IOException in name constraints check");
            }
            return false;
        }
        return true;
    }
    
    private boolean matchExcluded(final GeneralSubtrees generalSubtrees) {
        final Iterator<GeneralSubtree> iterator = generalSubtrees.iterator();
        while (iterator.hasNext()) {
            final GeneralNameInterface name = iterator.next().getName().getName();
            for (final GeneralNameInterface generalNameInterface : this.pathToGeneralNames) {
                if (name.getType() == generalNameInterface.getType()) {
                    switch (generalNameInterface.constrains(name)) {
                        case 0:
                        case 2: {
                            if (X509CertSelector.debug != null) {
                                X509CertSelector.debug.println("X509CertSelector.match: name constraints inhibit path to specified name");
                                X509CertSelector.debug.println("X509CertSelector.match: excluded name: " + generalNameInterface);
                            }
                            return false;
                        }
                        default: {
                            continue;
                        }
                    }
                }
            }
        }
        return true;
    }
    
    private boolean matchPermitted(final GeneralSubtrees generalSubtrees) {
        for (final GeneralNameInterface generalNameInterface : this.pathToGeneralNames) {
            final Iterator<GeneralSubtree> iterator2 = generalSubtrees.iterator();
            int n = 0;
            boolean b = false;
            String string = "";
            while (iterator2.hasNext() && n == 0) {
                final GeneralNameInterface name = iterator2.next().getName().getName();
                if (name.getType() == generalNameInterface.getType()) {
                    b = true;
                    string = string + "  " + name;
                    switch (generalNameInterface.constrains(name)) {
                        case 0:
                        case 2: {
                            n = 1;
                            continue;
                        }
                    }
                }
            }
            if (n == 0 && b) {
                if (X509CertSelector.debug != null) {
                    X509CertSelector.debug.println("X509CertSelector.match: name constraints inhibit path to specified name; permitted names of type " + generalNameInterface.getType() + ": " + string);
                }
                return false;
            }
        }
        return true;
    }
    
    private boolean matchBasicConstraints(final X509Certificate x509Certificate) {
        if (this.basicConstraints == -1) {
            return true;
        }
        final int basicConstraints = x509Certificate.getBasicConstraints();
        if (this.basicConstraints == -2) {
            if (basicConstraints != -1) {
                if (X509CertSelector.debug != null) {
                    X509CertSelector.debug.println("X509CertSelector.match: not an EE cert");
                }
                return false;
            }
        }
        else if (basicConstraints < this.basicConstraints) {
            if (X509CertSelector.debug != null) {
                X509CertSelector.debug.println("X509CertSelector.match: cert's maxPathLen is less than the min maxPathLen set by basicConstraints. (" + basicConstraints + " < " + this.basicConstraints + ")");
            }
            return false;
        }
        return true;
    }
    
    private static <T> Set<T> cloneSet(final Set<T> set) {
        if (set instanceof HashSet) {
            return (Set<T>)((HashSet<? extends T>)set).clone();
        }
        return new HashSet<T>((Collection<? extends T>)set);
    }
    
    @Override
    public Object clone() {
        try {
            final X509CertSelector x509CertSelector = (X509CertSelector)super.clone();
            if (this.subjectAlternativeNames != null) {
                x509CertSelector.subjectAlternativeNames = cloneSet(this.subjectAlternativeNames);
                x509CertSelector.subjectAlternativeGeneralNames = cloneSet(this.subjectAlternativeGeneralNames);
            }
            if (this.pathToGeneralNames != null) {
                x509CertSelector.pathToNames = cloneSet(this.pathToNames);
                x509CertSelector.pathToGeneralNames = cloneSet(this.pathToGeneralNames);
            }
            return x509CertSelector;
        }
        catch (CloneNotSupportedException ex) {
            throw new InternalError(ex.toString(), ex);
        }
    }
    
    static {
        debug = Debug.getInstance("certpath");
        ANY_EXTENDED_KEY_USAGE = ObjectIdentifier.newInternal(new int[] { 2, 5, 29, 37, 0 });
        CertPathHelperImpl.initialize();
        FALSE = Boolean.FALSE;
        (EXTENSION_OIDS = new String[5])[0] = "2.5.29.16";
        X509CertSelector.EXTENSION_OIDS[1] = "2.5.29.17";
        X509CertSelector.EXTENSION_OIDS[2] = "2.5.29.30";
        X509CertSelector.EXTENSION_OIDS[3] = "2.5.29.32";
        X509CertSelector.EXTENSION_OIDS[4] = "2.5.29.37";
    }
}
