package java.security;

import java.security.cert.*;
import java.util.*;

public static final class PrivateKeyEntry implements Entry
{
    private final PrivateKey privKey;
    private final Certificate[] chain;
    private final Set<Attribute> attributes;
    
    public PrivateKeyEntry(final PrivateKey privateKey, final Certificate[] array) {
        this(privateKey, array, Collections.emptySet());
    }
    
    public PrivateKeyEntry(final PrivateKey privKey, final Certificate[] array, final Set<Attribute> set) {
        if (privKey == null || array == null || set == null) {
            throw new NullPointerException("invalid null input");
        }
        if (array.length == 0) {
            throw new IllegalArgumentException("invalid zero-length input chain");
        }
        final Certificate[] chain = array.clone();
        final String type = chain[0].getType();
        for (int i = 1; i < chain.length; ++i) {
            if (!type.equals(chain[i].getType())) {
                throw new IllegalArgumentException("chain does not contain certificates of the same type");
            }
        }
        if (!privKey.getAlgorithm().equals(chain[0].getPublicKey().getAlgorithm())) {
            throw new IllegalArgumentException("private key algorithm does not match algorithm of public key in end entity certificate (at index 0)");
        }
        this.privKey = privKey;
        if (chain[0] instanceof X509Certificate && !(chain instanceof X509Certificate[])) {
            System.arraycopy(chain, 0, this.chain = new X509Certificate[chain.length], 0, chain.length);
        }
        else {
            this.chain = chain;
        }
        this.attributes = Collections.unmodifiableSet((Set<? extends Attribute>)new HashSet<Attribute>(set));
    }
    
    public PrivateKey getPrivateKey() {
        return this.privKey;
    }
    
    public Certificate[] getCertificateChain() {
        return this.chain.clone();
    }
    
    public Certificate getCertificate() {
        return this.chain[0];
    }
    
    @Override
    public Set<Attribute> getAttributes() {
        return this.attributes;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Private key entry and certificate chain with " + this.chain.length + " elements:\r\n");
        final Certificate[] chain = this.chain;
        for (int length = chain.length, i = 0; i < length; ++i) {
            sb.append(chain[i]);
            sb.append("\r\n");
        }
        return sb.toString();
    }
}
