package java.security;

import java.net.*;
import java.util.*;

public final class DomainLoadStoreParameter implements KeyStore.LoadStoreParameter
{
    private final URI configuration;
    private final Map<String, KeyStore.ProtectionParameter> protectionParams;
    
    public DomainLoadStoreParameter(final URI configuration, final Map<String, KeyStore.ProtectionParameter> map) {
        if (configuration == null || map == null) {
            throw new NullPointerException("invalid null input");
        }
        this.configuration = configuration;
        this.protectionParams = Collections.unmodifiableMap((Map<? extends String, ? extends KeyStore.ProtectionParameter>)new HashMap<String, KeyStore.ProtectionParameter>(map));
    }
    
    public URI getConfiguration() {
        return this.configuration;
    }
    
    public Map<String, KeyStore.ProtectionParameter> getProtectionParams() {
        return this.protectionParams;
    }
    
    @Override
    public KeyStore.ProtectionParameter getProtectionParameter() {
        return null;
    }
}
