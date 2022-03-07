package java.util;

public class ConcurrentModificationException extends RuntimeException
{
    private static final long serialVersionUID = -3666751008965953603L;
    
    public ConcurrentModificationException() {
    }
    
    public ConcurrentModificationException(final String s) {
        super(s);
    }
    
    public ConcurrentModificationException(final Throwable t) {
        super(t);
    }
    
    public ConcurrentModificationException(final String s, final Throwable t) {
        super(s, t);
    }
}
