package java.util.concurrent;

public class ExecutionException extends Exception
{
    private static final long serialVersionUID = 7830266012832686185L;
    
    protected ExecutionException() {
    }
    
    protected ExecutionException(final String s) {
        super(s);
    }
    
    public ExecutionException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public ExecutionException(final Throwable t) {
        super(t);
    }
}
