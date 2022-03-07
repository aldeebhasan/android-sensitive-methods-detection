package android.security.keystore;

import java.security.*;
import java.util.*;

public final class KeyProtection implements KeyStore.ProtectionParameter
{
    KeyProtection() {
        throw new RuntimeException("Stub!");
    }
    
    public Date getKeyValidityStart() {
        throw new RuntimeException("Stub!");
    }
    
    public Date getKeyValidityForConsumptionEnd() {
        throw new RuntimeException("Stub!");
    }
    
    public Date getKeyValidityForOriginationEnd() {
        throw new RuntimeException("Stub!");
    }
    
    public int getPurposes() {
        throw new RuntimeException("Stub!");
    }
    
    public String[] getEncryptionPaddings() {
        throw new RuntimeException("Stub!");
    }
    
    public String[] getSignaturePaddings() {
        throw new RuntimeException("Stub!");
    }
    
    public String[] getDigests() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isDigestsSpecified() {
        throw new RuntimeException("Stub!");
    }
    
    public String[] getBlockModes() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isRandomizedEncryptionRequired() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isUserAuthenticationRequired() {
        throw new RuntimeException("Stub!");
    }
    
    public int getUserAuthenticationValidityDurationSeconds() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isUserAuthenticationValidWhileOnBody() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isInvalidatedByBiometricEnrollment() {
        throw new RuntimeException("Stub!");
    }
    
    public static final class Builder
    {
        public Builder(final int purposes) {
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
        
        public Builder setEncryptionPaddings(final String... paddings) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setSignaturePaddings(final String... paddings) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setDigests(final String... digests) {
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
        
        public Builder setUserAuthenticationValidWhileOnBody(final boolean remainsValid) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setInvalidatedByBiometricEnrollment(final boolean invalidateKey) {
            throw new RuntimeException("Stub!");
        }
        
        public KeyProtection build() {
            throw new RuntimeException("Stub!");
        }
    }
}
