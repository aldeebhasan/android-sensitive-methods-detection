package java.security;

import java.security.spec.*;
import java.io.*;

public abstract class AlgorithmParametersSpi
{
    protected abstract void engineInit(final AlgorithmParameterSpec p0) throws InvalidParameterSpecException;
    
    protected abstract void engineInit(final byte[] p0) throws IOException;
    
    protected abstract void engineInit(final byte[] p0, final String p1) throws IOException;
    
    protected abstract <T extends AlgorithmParameterSpec> T engineGetParameterSpec(final Class<T> p0) throws InvalidParameterSpecException;
    
    protected abstract byte[] engineGetEncoded() throws IOException;
    
    protected abstract byte[] engineGetEncoded(final String p0) throws IOException;
    
    protected abstract String engineToString();
}
