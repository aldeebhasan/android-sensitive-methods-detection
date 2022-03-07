package java.lang;

public class IllegalArgumentException extends RuntimeException
{
    private static final long serialVersionUID = -5365630128856068164L;
    
    public IllegalArgumentException() {
    }
    
    public IllegalArgumentException(final String s) {
        super(s);
    }
    
    public IllegalArgumentException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public IllegalArgumentException(final Throwable t) {
        super(t);
    }
}
