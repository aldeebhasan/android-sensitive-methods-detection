package java.security;

public class InvalidKeyException extends KeyException
{
    private static final long serialVersionUID = 5698479920593359816L;
    
    public InvalidKeyException() {
    }
    
    public InvalidKeyException(final String s) {
        super(s);
    }
    
    public InvalidKeyException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public InvalidKeyException(final Throwable t) {
        super(t);
    }
}
