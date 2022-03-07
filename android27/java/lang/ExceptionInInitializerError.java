package java.lang;

public class ExceptionInInitializerError extends LinkageError
{
    private static final long serialVersionUID = 1521711792217232256L;
    private Throwable exception;
    
    public ExceptionInInitializerError() {
        this.initCause(null);
    }
    
    public ExceptionInInitializerError(final Throwable exception) {
        this.initCause(null);
        this.exception = exception;
    }
    
    public ExceptionInInitializerError(final String s) {
        super(s);
        this.initCause(null);
    }
    
    public Throwable getException() {
        return this.exception;
    }
    
    @Override
    public Throwable getCause() {
        return this.exception;
    }
}
