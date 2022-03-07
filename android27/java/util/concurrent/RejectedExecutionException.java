package java.util.concurrent;

public class RejectedExecutionException extends RuntimeException
{
    private static final long serialVersionUID = -375805702767069545L;
    
    public RejectedExecutionException() {
    }
    
    public RejectedExecutionException(final String s) {
        super(s);
    }
    
    public RejectedExecutionException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public RejectedExecutionException(final Throwable t) {
        super(t);
    }
}
