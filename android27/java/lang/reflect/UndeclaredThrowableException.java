package java.lang.reflect;

public class UndeclaredThrowableException extends RuntimeException
{
    static final long serialVersionUID = 330127114055056639L;
    private Throwable undeclaredThrowable;
    
    public UndeclaredThrowableException(final Throwable undeclaredThrowable) {
        super((Throwable)null);
        this.undeclaredThrowable = undeclaredThrowable;
    }
    
    public UndeclaredThrowableException(final Throwable undeclaredThrowable, final String s) {
        super(s, null);
        this.undeclaredThrowable = undeclaredThrowable;
    }
    
    public Throwable getUndeclaredThrowable() {
        return this.undeclaredThrowable;
    }
    
    @Override
    public Throwable getCause() {
        return this.undeclaredThrowable;
    }
}
