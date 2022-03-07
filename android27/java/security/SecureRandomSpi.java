package java.security;

import java.io.*;

public abstract class SecureRandomSpi implements Serializable
{
    private static final long serialVersionUID = -2991854161009191830L;
    
    protected abstract void engineSetSeed(final byte[] p0);
    
    protected abstract void engineNextBytes(final byte[] p0);
    
    protected abstract byte[] engineGenerateSeed(final int p0);
}
