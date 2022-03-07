package java.security;

import java.io.*;

@Deprecated
public interface Certificate
{
    Principal getGuarantor();
    
    Principal getPrincipal();
    
    PublicKey getPublicKey();
    
    void encode(final OutputStream p0) throws KeyException, IOException;
    
    void decode(final InputStream p0) throws KeyException, IOException;
    
    String getFormat();
    
    String toString(final boolean p0);
}
