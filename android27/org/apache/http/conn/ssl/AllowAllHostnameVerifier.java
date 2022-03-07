package org.apache.http.conn.ssl;

@Deprecated
public class AllowAllHostnameVerifier extends AbstractVerifier
{
    public AllowAllHostnameVerifier() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public final void verify(final String host, final String[] cns, final String[] subjectAlts) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public final String toString() {
        throw new RuntimeException("Stub!");
    }
}
