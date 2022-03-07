package javax.security.auth;

public class DestroyFailedException extends Exception
{
    private static final long serialVersionUID = -7790152857282749162L;
    
    public DestroyFailedException() {
    }
    
    public DestroyFailedException(final String s) {
        super(s);
    }
}
