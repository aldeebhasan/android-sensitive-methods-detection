package java.lang;

public class IllegalStateException extends RuntimeException
{
    static final long serialVersionUID = -1848914673093119416L;
    
    public IllegalStateException() {
    }
    
    public IllegalStateException(final String s) {
        super(s);
    }
    
    public IllegalStateException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public IllegalStateException(final Throwable t) {
        super(t);
    }
}
