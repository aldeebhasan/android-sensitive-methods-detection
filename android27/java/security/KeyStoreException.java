package java.security;

public class KeyStoreException extends GeneralSecurityException
{
    private static final long serialVersionUID = -1119353179322377262L;
    
    public KeyStoreException() {
    }
    
    public KeyStoreException(final String s) {
        super(s);
    }
    
    public KeyStoreException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public KeyStoreException(final Throwable t) {
        super(t);
    }
}
