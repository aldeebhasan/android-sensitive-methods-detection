package android.net.wifi;

import android.os.*;
import java.security.cert.*;
import java.security.*;

public class WifiEnterpriseConfig implements Parcelable
{
    public static final Creator<WifiEnterpriseConfig> CREATOR;
    
    public WifiEnterpriseConfig() {
        throw new RuntimeException("Stub!");
    }
    
    public WifiEnterpriseConfig(final WifiEnterpriseConfig source) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int describeContents() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    public void setEapMethod(final int eapMethod) {
        throw new RuntimeException("Stub!");
    }
    
    public int getEapMethod() {
        throw new RuntimeException("Stub!");
    }
    
    public void setPhase2Method(final int phase2Method) {
        throw new RuntimeException("Stub!");
    }
    
    public int getPhase2Method() {
        throw new RuntimeException("Stub!");
    }
    
    public void setIdentity(final String identity) {
        throw new RuntimeException("Stub!");
    }
    
    public String getIdentity() {
        throw new RuntimeException("Stub!");
    }
    
    public void setAnonymousIdentity(final String anonymousIdentity) {
        throw new RuntimeException("Stub!");
    }
    
    public String getAnonymousIdentity() {
        throw new RuntimeException("Stub!");
    }
    
    public void setPassword(final String password) {
        throw new RuntimeException("Stub!");
    }
    
    public String getPassword() {
        throw new RuntimeException("Stub!");
    }
    
    public void setCaCertificate(final X509Certificate cert) {
        throw new RuntimeException("Stub!");
    }
    
    public X509Certificate getCaCertificate() {
        throw new RuntimeException("Stub!");
    }
    
    public void setCaCertificates(final X509Certificate[] certs) {
        throw new RuntimeException("Stub!");
    }
    
    public X509Certificate[] getCaCertificates() {
        throw new RuntimeException("Stub!");
    }
    
    public void setClientKeyEntry(final PrivateKey privateKey, final X509Certificate clientCertificate) {
        throw new RuntimeException("Stub!");
    }
    
    public void setClientKeyEntryWithCertificateChain(final PrivateKey privateKey, final X509Certificate[] clientCertificateChain) {
        throw new RuntimeException("Stub!");
    }
    
    public X509Certificate getClientCertificate() {
        throw new RuntimeException("Stub!");
    }
    
    public X509Certificate[] getClientCertificateChain() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void setSubjectMatch(final String subjectMatch) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public String getSubjectMatch() {
        throw new RuntimeException("Stub!");
    }
    
    public void setAltSubjectMatch(final String altSubjectMatch) {
        throw new RuntimeException("Stub!");
    }
    
    public String getAltSubjectMatch() {
        throw new RuntimeException("Stub!");
    }
    
    public void setDomainSuffixMatch(final String domain) {
        throw new RuntimeException("Stub!");
    }
    
    public String getDomainSuffixMatch() {
        throw new RuntimeException("Stub!");
    }
    
    public void setRealm(final String realm) {
        throw new RuntimeException("Stub!");
    }
    
    public String getRealm() {
        throw new RuntimeException("Stub!");
    }
    
    public void setPlmn(final String plmn) {
        throw new RuntimeException("Stub!");
    }
    
    public String getPlmn() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
    
    public static final class Eap
    {
        public static final int AKA = 5;
        public static final int AKA_PRIME = 6;
        public static final int NONE = -1;
        public static final int PEAP = 0;
        public static final int PWD = 3;
        public static final int SIM = 4;
        public static final int TLS = 1;
        public static final int TTLS = 2;
        public static final int UNAUTH_TLS = 7;
        
        Eap() {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static final class Phase2
    {
        public static final int AKA = 6;
        public static final int AKA_PRIME = 7;
        public static final int GTC = 4;
        public static final int MSCHAP = 2;
        public static final int MSCHAPV2 = 3;
        public static final int NONE = 0;
        public static final int PAP = 1;
        public static final int SIM = 5;
        
        Phase2() {
            throw new RuntimeException("Stub!");
        }
    }
}
