package java.security;

public class KeyManagementException extends KeyException
{
    private static final long serialVersionUID = 947674216157062695L;
    
    public KeyManagementException() {
    }
    
    public KeyManagementException(final String s) {
        super(s);
    }
    
    public KeyManagementException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public KeyManagementException(final Throwable t) {
        super(t);
    }
}
