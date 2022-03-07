package android.security;

import java.security.spec.*;
import android.content.*;
import javax.security.auth.x500.*;
import java.math.*;
import java.util.*;
import java.security.*;

@Deprecated
public final class KeyPairGeneratorSpec implements AlgorithmParameterSpec
{
    KeyPairGeneratorSpec() {
        throw new RuntimeException("Stub!");
    }
    
    public Context getContext() {
        throw new RuntimeException("Stub!");
    }
    
    public String getKeystoreAlias() {
        throw new RuntimeException("Stub!");
    }
    
    public String getKeyType() {
        throw new RuntimeException("Stub!");
    }
    
    public int getKeySize() {
        throw new RuntimeException("Stub!");
    }
    
    public AlgorithmParameterSpec getAlgorithmParameterSpec() {
        throw new RuntimeException("Stub!");
    }
    
    public X500Principal getSubjectDN() {
        throw new RuntimeException("Stub!");
    }
    
    public BigInteger getSerialNumber() {
        throw new RuntimeException("Stub!");
    }
    
    public Date getStartDate() {
        throw new RuntimeException("Stub!");
    }
    
    public Date getEndDate() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isEncryptionRequired() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public static final class Builder
    {
        public Builder(final Context context) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setAlias(final String alias) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setKeyType(final String keyType) throws NoSuchAlgorithmException {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setKeySize(final int keySize) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setAlgorithmParameterSpec(final AlgorithmParameterSpec spec) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setSubject(final X500Principal subject) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setSerialNumber(final BigInteger serialNumber) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setStartDate(final Date startDate) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setEndDate(final Date endDate) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setEncryptionRequired() {
            throw new RuntimeException("Stub!");
        }
        
        public KeyPairGeneratorSpec build() {
            throw new RuntimeException("Stub!");
        }
    }
}
