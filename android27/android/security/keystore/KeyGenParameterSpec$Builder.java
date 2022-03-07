package android.security.keystore;

import java.security.spec.*;
import javax.security.auth.x500.*;
import java.math.*;
import java.util.*;

public static final class Builder
{
    public Builder(final String keystoreAlias, final int purposes) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setKeySize(final int keySize) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setAlgorithmParameterSpec(final AlgorithmParameterSpec spec) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setCertificateSubject(final X500Principal subject) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setCertificateSerialNumber(final BigInteger serialNumber) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setCertificateNotBefore(final Date date) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setCertificateNotAfter(final Date date) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setKeyValidityStart(final Date startDate) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setKeyValidityEnd(final Date endDate) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setKeyValidityForOriginationEnd(final Date endDate) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setKeyValidityForConsumptionEnd(final Date endDate) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setDigests(final String... digests) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setEncryptionPaddings(final String... paddings) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setSignaturePaddings(final String... paddings) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setBlockModes(final String... blockModes) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setRandomizedEncryptionRequired(final boolean required) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setUserAuthenticationRequired(final boolean required) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setUserAuthenticationValidityDurationSeconds(final int seconds) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setAttestationChallenge(final byte[] attestationChallenge) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setUserAuthenticationValidWhileOnBody(final boolean remainsValid) {
        throw new RuntimeException("Stub!");
    }
    
    public Builder setInvalidatedByBiometricEnrollment(final boolean invalidateKey) {
        throw new RuntimeException("Stub!");
    }
    
    public KeyGenParameterSpec build() {
        throw new RuntimeException("Stub!");
    }
}
