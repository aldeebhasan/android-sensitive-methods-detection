package android.security;

import java.security.*;
import android.content.*;

@Deprecated
public final class KeyStoreParameter implements KeyStore.ProtectionParameter
{
    KeyStoreParameter() {
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
        
        public Builder setEncryptionRequired(final boolean required) {
            throw new RuntimeException("Stub!");
        }
        
        public KeyStoreParameter build() {
            throw new RuntimeException("Stub!");
        }
    }
}
