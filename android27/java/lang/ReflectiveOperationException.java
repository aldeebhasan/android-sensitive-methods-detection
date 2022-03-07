package java.lang;

public class ReflectiveOperationException extends Exception
{
    static final long serialVersionUID = 123456789L;
    
    public ReflectiveOperationException() {
    }
    
    public ReflectiveOperationException(final String s) {
        super(s);
    }
    
    public ReflectiveOperationException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public ReflectiveOperationException(final Throwable t) {
        super(t);
    }
}
