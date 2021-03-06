package java.lang;

public class ClassNotFoundException extends ReflectiveOperationException
{
    private static final long serialVersionUID = 9176873029745254542L;
    private Throwable ex;
    
    public ClassNotFoundException() {
        super((Throwable)null);
    }
    
    public ClassNotFoundException(final String s) {
        super(s, null);
    }
    
    public ClassNotFoundException(final String s, final Throwable ex) {
        super(s, null);
        this.ex = ex;
    }
    
    public Throwable getException() {
        return this.ex;
    }
    
    @Override
    public Throwable getCause() {
        return this.ex;
    }
}
