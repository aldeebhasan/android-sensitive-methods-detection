package java.lang.invoke;

public class WrongMethodTypeException extends RuntimeException
{
    private static final long serialVersionUID = 292L;
    
    public WrongMethodTypeException() {
    }
    
    public WrongMethodTypeException(final String s) {
        super(s);
    }
    
    WrongMethodTypeException(final String s, final Throwable t) {
        super(s, t);
    }
    
    WrongMethodTypeException(final Throwable t) {
        super(t);
    }
}
