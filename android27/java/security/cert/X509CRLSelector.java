package java.security.cert;

import javax.security.auth.x500.*;
import java.math.*;
import java.io.*;
import java.util.*;
import sun.security.util.*;
import sun.security.x509.*;

public class X509CRLSelector implements CRLSelector
{
    private static final Debug debug;
    private HashSet<Object> issuerNames;
    private HashSet<X500Principal> issuerX500Principals;
    private BigInteger minCRL;
    private BigInteger maxCRL;
    private Date dateAndTime;
    private X509Certificate certChecking;
    private long skew;
    
    public X509CRLSelector() {
        this.skew = 0L;
    }
    
    public void setIssuers(final Collection<X500Principal> collection) {
        if (collection == null || collection.isEmpty()) {
            this.issuerNames = null;
            this.issuerX500Principals = null;
        }
        else {
            this.issuerX500Principals = new HashSet<X500Principal>(collection);
            this.issuerNames = new HashSet<Object>();
            final Iterator<X500Principal> iterator = this.issuerX500Principals.iterator();
            while (iterator.hasNext()) {
                this.issuerNames.add(iterator.next().getEncoded());
            }
        }
    }
    
    public void setIssuerNames(final Collection<?> collection) throws IOException {
        if (collection == null || collection.size() == 0) {
            this.issuerNames = null;
            this.issuerX500Principals = null;
        }
        else {
            final HashSet<Object> cloneAndCheckIssuerNames = cloneAndCheckIssuerNames(collection);
            this.issuerX500Principals = parseIssuerNames(cloneAndCheckIssuerNames);
            this.issuerNames = cloneAndCheckIssuerNames;
        }
    }
    
    public void addIssuer(final X500Principal x500Principal) {
        this.addIssuerNameInternal(x500Principal.getEncoded(), x500Principal);
    }
    
    public void addIssuerName(final String s) throws IOException {
        this.addIssuerNameInternal(s, new X500Name(s).asX500Principal());
    }
    
    public void addIssuerName(final byte[] array) throws IOException {
        this.addIssuerNameInternal(array.clone(), new X500Name(array).asX500Principal());
    }
    
    private void addIssuerNameInternal(final Object o, final X500Principal x500Principal) {
        if (this.issuerNames == null) {
            this.issuerNames = new HashSet<Object>();
        }
        if (this.issuerX500Principals == null) {
            this.issuerX500Principals = new HashSet<X500Principal>();
        }
        this.issuerNames.add(o);
        this.issuerX500Principals.add(x500Principal);
    }
    
    private static HashSet<Object> cloneAndCheckIssuerNames(final Collection<?> collection) throws IOException {
        final HashSet<byte[]> set = (HashSet<byte[]>)new HashSet<Object>();
        for (final Object next : collection) {
            if (!(next instanceof byte[]) && !(next instanceof String)) {
                throw new IOException("name not byte array or String");
            }
            if (next instanceof byte[]) {
                set.add(((byte[])next).clone());
            }
            else {
                set.add((byte[])next);
            }
        }
        return (HashSet<Object>)set;
    }
    
    private static HashSet<Object> cloneIssuerNames(final Collection<Object> collection) {
        try {
            return cloneAndCheckIssuerNames(collection);
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    private static HashSet<X500Principal> parseIssuerNames(final Collection<Object> collection) throws IOException {
        final HashSet<X500Principal> set = new HashSet<X500Principal>();
        for (final String next : collection) {
            if (next instanceof String) {
                set.add(new X500Name(next).asX500Principal());
            }
            else {
                try {
                    set.add(new X500Principal((byte[])(Object)next));
                }
                catch (IllegalArgumentException ex) {
                    throw (IOException)new IOException("Invalid name").initCause(ex);
                }
            }
        }
        return set;
    }
    
    public void setMinCRLNumber(final BigInteger minCRL) {
        this.minCRL = minCRL;
    }
    
    public void setMaxCRLNumber(final BigInteger maxCRL) {
        this.maxCRL = maxCRL;
    }
    
    public void setDateAndTime(final Date date) {
        if (date == null) {
            this.dateAndTime = null;
        }
        else {
            this.dateAndTime = new Date(date.getTime());
        }
        this.skew = 0L;
    }
    
    void setDateAndTime(final Date date, final long skew) {
        this.dateAndTime = ((date == null) ? null : new Date(date.getTime()));
        this.skew = skew;
    }
    
    public void setCertificateChecking(final X509Certificate certChecking) {
        this.certChecking = certChecking;
    }
    
    public Collection<X500Principal> getIssuers() {
        if (this.issuerX500Principals == null) {
            return null;
        }
        return Collections.unmodifiableCollection((Collection<? extends X500Principal>)this.issuerX500Principals);
    }
    
    public Collection<Object> getIssuerNames() {
        if (this.issuerNames == null) {
            return null;
        }
        return cloneIssuerNames(this.issuerNames);
    }
    
    public BigInteger getMinCRL() {
        return this.minCRL;
    }
    
    public BigInteger getMaxCRL() {
        return this.maxCRL;
    }
    
    public Date getDateAndTime() {
        if (this.dateAndTime == null) {
            return null;
        }
        return (Date)this.dateAndTime.clone();
    }
    
    public X509Certificate getCertificateChecking() {
        return this.certChecking;
    }
    
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("X509CRLSelector: [\n");
        if (this.issuerNames != null) {
            sb.append("  IssuerNames:\n");
            final Iterator<Object> iterator = this.issuerNames.iterator();
            while (iterator.hasNext()) {
                sb.append("    " + iterator.next() + "\n");
            }
        }
        if (this.minCRL != null) {
            sb.append("  minCRLNumber: " + this.minCRL + "\n");
        }
        if (this.maxCRL != null) {
            sb.append("  maxCRLNumber: " + this.maxCRL + "\n");
        }
        if (this.dateAndTime != null) {
            sb.append("  dateAndTime: " + this.dateAndTime + "\n");
        }
        if (this.certChecking != null) {
            sb.append("  Certificate being checked: " + this.certChecking + "\n");
        }
        sb.append("]");
        return sb.toString();
    }
    
    @Override
    public boolean match(final CRL crl) {
        if (!(crl instanceof X509CRL)) {
            return false;
        }
        final X509CRL x509CRL = (X509CRL)crl;
        if (this.issuerNames != null) {
            final X500Principal issuerX500Principal = x509CRL.getIssuerX500Principal();
            Iterator<X500Principal> iterator;
            int n;
            for (iterator = this.issuerX500Principals.iterator(), n = 0; n == 0 && iterator.hasNext(); n = 1) {
                if (iterator.next().equals(issuerX500Principal)) {}
            }
            if (n == 0) {
                if (X509CRLSelector.debug != null) {
                    X509CRLSelector.debug.println("X509CRLSelector.match: issuer DNs don't match");
                }
                return false;
            }
        }
        if (this.minCRL != null || this.maxCRL != null) {
            final byte[] extensionValue = x509CRL.getExtensionValue("2.5.29.20");
            if (extensionValue == null && X509CRLSelector.debug != null) {
                X509CRLSelector.debug.println("X509CRLSelector.match: no CRLNumber");
            }
            BigInteger value;
            try {
                value = new CRLNumberExtension(Boolean.FALSE, new DerInputStream(extensionValue).getOctetString()).get("value");
            }
            catch (IOException ex) {
                if (X509CRLSelector.debug != null) {
                    X509CRLSelector.debug.println("X509CRLSelector.match: exception in decoding CRL number");
                }
                return false;
            }
            if (this.minCRL != null && value.compareTo(this.minCRL) < 0) {
                if (X509CRLSelector.debug != null) {
                    X509CRLSelector.debug.println("X509CRLSelector.match: CRLNumber too small");
                }
                return false;
            }
            if (this.maxCRL != null && value.compareTo(this.maxCRL) > 0) {
                if (X509CRLSelector.debug != null) {
                    X509CRLSelector.debug.println("X509CRLSelector.match: CRLNumber too large");
                }
                return false;
            }
        }
        if (this.dateAndTime != null) {
            final Date thisUpdate = x509CRL.getThisUpdate();
            final Date nextUpdate = x509CRL.getNextUpdate();
            if (nextUpdate == null) {
                if (X509CRLSelector.debug != null) {
                    X509CRLSelector.debug.println("X509CRLSelector.match: nextUpdate null");
                }
                return false;
            }
            Date dateAndTime = this.dateAndTime;
            Date dateAndTime2 = this.dateAndTime;
            if (this.skew > 0L) {
                dateAndTime = new Date(this.dateAndTime.getTime() + this.skew);
                dateAndTime2 = new Date(this.dateAndTime.getTime() - this.skew);
            }
            if (dateAndTime2.after(nextUpdate) || dateAndTime.before(thisUpdate)) {
                if (X509CRLSelector.debug != null) {
                    X509CRLSelector.debug.println("X509CRLSelector.match: update out-of-range");
                }
                return false;
            }
        }
        return true;
    }
    
    @Override
    public Object clone() {
        try {
            final X509CRLSelector x509CRLSelector = (X509CRLSelector)super.clone();
            if (this.issuerNames != null) {
                x509CRLSelector.issuerNames = new HashSet<Object>(this.issuerNames);
                x509CRLSelector.issuerX500Principals = new HashSet<X500Principal>(this.issuerX500Principals);
            }
            return x509CRLSelector;
        }
        catch (CloneNotSupportedException ex) {
            throw new InternalError(ex.toString(), ex);
        }
    }
    
    static {
        CertPathHelperImpl.initialize();
        debug = Debug.getInstance("certpath");
    }
}
