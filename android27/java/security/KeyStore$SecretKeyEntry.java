package java.security;

import javax.crypto.*;
import java.util.*;

public static final class SecretKeyEntry implements Entry
{
    private final SecretKey sKey;
    private final Set<Attribute> attributes;
    
    public SecretKeyEntry(final SecretKey sKey) {
        if (sKey == null) {
            throw new NullPointerException("invalid null input");
        }
        this.sKey = sKey;
        this.attributes = Collections.emptySet();
    }
    
    public SecretKeyEntry(final SecretKey sKey, final Set<Attribute> set) {
        if (sKey == null || set == null) {
            throw new NullPointerException("invalid null input");
        }
        this.sKey = sKey;
        this.attributes = Collections.unmodifiableSet((Set<? extends Attribute>)new HashSet<Attribute>(set));
    }
    
    public SecretKey getSecretKey() {
        return this.sKey;
    }
    
    @Override
    public Set<Attribute> getAttributes() {
        return this.attributes;
    }
    
    @Override
    public String toString() {
        return "Secret key entry with algorithm " + this.sKey.getAlgorithm();
    }
}
