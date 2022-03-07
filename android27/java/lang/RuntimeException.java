package java.lang;

public class RuntimeException extends Exception
{
    static final long serialVersionUID = -7034897190745766939L;
    
    public RuntimeException() {
    }
    
    public RuntimeException(final String s) {
        super(s);
    }
    
    public RuntimeException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public RuntimeException(final Throwable t) {
        super(t);
    }
    
    protected RuntimeException(final String s, final Throwable t, final boolean b, final boolean b2) {
        super(s, t, b, b2);
    }
}
