package android.security.keystore;

import java.security.spec.*;
import java.util.*;

public class KeyInfo implements KeySpec
{
    KeyInfo() {
        throw new RuntimeException("Stub!");
    }
    
    public String getKeystoreAlias() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isInsideSecureHardware() {
        throw new RuntimeException("Stub!");
    }
    
    public int getOrigin() {
        throw new RuntimeException("Stub!");
    }
    
    public int getKeySize() {
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
    
    public String[] getBlockModes() {
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
    
    public boolean isUserAuthenticationRequired() {
        throw new RuntimeException("Stub!");
    }
    
    public int getUserAuthenticationValidityDurationSeconds() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isUserAuthenticationRequirementEnforcedBySecureHardware() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isUserAuthenticationValidWhileOnBody() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isInvalidatedByBiometricEnrollment() {
        throw new RuntimeException("Stub!");
    }
}
