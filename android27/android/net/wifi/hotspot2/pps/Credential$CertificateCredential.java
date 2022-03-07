package android.net.wifi.hotspot2.pps;

import android.os.*;

public static final class CertificateCredential implements Parcelable
{
    public static final Creator<CertificateCredential> CREATOR;
    
    public CertificateCredential() {
        throw new RuntimeException("Stub!");
    }
    
    public CertificateCredential(final CertificateCredential source) {
        throw new RuntimeException("Stub!");
    }
    
    public void setCertType(final String certType) {
        throw new RuntimeException("Stub!");
    }
    
    public String getCertType() {
        throw new RuntimeException("Stub!");
    }
    
    public void setCertSha256Fingerprint(final byte[] certSha256Fingerprint) {
        throw new RuntimeException("Stub!");
    }
    
    public byte[] getCertSha256Fingerprint() {
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
    
    @Override
    public boolean equals(final Object thatObject) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int hashCode() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
}
