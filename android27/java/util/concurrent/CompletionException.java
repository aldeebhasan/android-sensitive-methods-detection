package java.util.concurrent;

public class CompletionException extends RuntimeException
{
    private static final long serialVersionUID = 7830266012832686185L;
    
    protected CompletionException() {
    }
    
    protected CompletionException(final String s) {
        super(s);
    }
    
    public CompletionException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public CompletionException(final Throwable t) {
        super(t);
    }
}
