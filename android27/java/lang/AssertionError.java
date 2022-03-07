package java.lang;

public class AssertionError extends Error
{
    private static final long serialVersionUID = -5013299493970297370L;
    
    public AssertionError() {
    }
    
    private AssertionError(final String s) {
        super(s);
    }
    
    public AssertionError(final Object o) {
        this(String.valueOf(o));
        if (o instanceof Throwable) {
            this.initCause((Throwable)o);
        }
    }
    
    public AssertionError(final boolean b) {
        this(String.valueOf(b));
    }
    
    public AssertionError(final char c) {
        this(String.valueOf(c));
    }
    
    public AssertionError(final int n) {
        this(String.valueOf(n));
    }
    
    public AssertionError(final long n) {
        this(String.valueOf(n));
    }
    
    public AssertionError(final float n) {
        this(String.valueOf(n));
    }
    
    public AssertionError(final double n) {
        this(String.valueOf(n));
    }
    
    public AssertionError(final String s, final Throwable t) {
        super(s, t);
    }
}
