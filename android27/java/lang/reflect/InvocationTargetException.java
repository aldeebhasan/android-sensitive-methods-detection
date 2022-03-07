package java.lang.reflect;

public class InvocationTargetException extends ReflectiveOperationException
{
    private static final long serialVersionUID = 4085088731926701167L;
    private Throwable target;
    
    protected InvocationTargetException() {
        super((Throwable)null);
    }
    
    public InvocationTargetException(final Throwable target) {
        super((Throwable)null);
        this.target = target;
    }
    
    public InvocationTargetException(final Throwable target, final String s) {
        super(s, null);
        this.target = target;
    }
    
    public Throwable getTargetException() {
        return this.target;
    }
    
    @Override
    public Throwable getCause() {
        return this.target;
    }
}
