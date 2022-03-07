package java.lang;

public class UnsupportedOperationException extends RuntimeException
{
    static final long serialVersionUID = -1242599979055084673L;
    
    public UnsupportedOperationException() {
    }
    
    public UnsupportedOperationException(final String s) {
        super(s);
    }
    
    public UnsupportedOperationException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public UnsupportedOperationException(final Throwable t) {
        super(t);
    }
}
