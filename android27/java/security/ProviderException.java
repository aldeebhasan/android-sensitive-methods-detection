package java.security;

public class ProviderException extends RuntimeException
{
    private static final long serialVersionUID = 5256023526693665674L;
    
    public ProviderException() {
    }
    
    public ProviderException(final String s) {
        super(s);
    }
    
    public ProviderException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public ProviderException(final Throwable t) {
        super(t);
    }
}
