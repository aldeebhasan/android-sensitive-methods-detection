package java.security;

import java.security.spec.*;
import java.util.*;
import javax.security.auth.*;

public static class PasswordProtection implements ProtectionParameter, Destroyable
{
    private final char[] password;
    private final String protectionAlgorithm;
    private final AlgorithmParameterSpec protectionParameters;
    private volatile boolean destroyed;
    
    public PasswordProtection(final char[] array) {
        this.destroyed = false;
        this.password = (char[])((array == null) ? null : ((char[])array.clone()));
        this.protectionAlgorithm = null;
        this.protectionParameters = null;
    }
    
    public PasswordProtection(final char[] array, final String protectionAlgorithm, final AlgorithmParameterSpec protectionParameters) {
        this.destroyed = false;
        if (protectionAlgorithm == null) {
            throw new NullPointerException("invalid null input");
        }
        this.password = (char[])((array == null) ? null : ((char[])array.clone()));
        this.protectionAlgorithm = protectionAlgorithm;
        this.protectionParameters = protectionParameters;
    }
    
    public String getProtectionAlgorithm() {
        return this.protectionAlgorithm;
    }
    
    public AlgorithmParameterSpec getProtectionParameters() {
        return this.protectionParameters;
    }
    
    public synchronized char[] getPassword() {
        if (this.destroyed) {
            throw new IllegalStateException("password has been cleared");
        }
        return this.password;
    }
    
    @Override
    public synchronized void destroy() throws DestroyFailedException {
        this.destroyed = true;
        if (this.password != null) {
            Arrays.fill(this.password, ' ');
        }
    }
    
    @Override
    public synchronized boolean isDestroyed() {
        return this.destroyed;
    }
}
