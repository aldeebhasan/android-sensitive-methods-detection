package java.security;

public class PrivilegedActionException extends Exception
{
    private static final long serialVersionUID = 4724086851538908602L;
    private Exception exception;
    
    public PrivilegedActionException(final Exception exception) {
        super((Throwable)null);
        this.exception = exception;
    }
    
    public Exception getException() {
        return this.exception;
    }
    
    @Override
    public Throwable getCause() {
        return this.exception;
    }
    
    @Override
    public String toString() {
        final String name = this.getClass().getName();
        return (this.exception != null) ? (name + ": " + this.exception.toString()) : name;
    }
}
