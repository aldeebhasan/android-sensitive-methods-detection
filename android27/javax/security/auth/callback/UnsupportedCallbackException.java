package javax.security.auth.callback;

public class UnsupportedCallbackException extends Exception
{
    private static final long serialVersionUID = -6873556327655666839L;
    private Callback callback;
    
    public UnsupportedCallbackException(final Callback callback) {
        this.callback = callback;
    }
    
    public UnsupportedCallbackException(final Callback callback, final String s) {
        super(s);
        this.callback = callback;
    }
    
    public Callback getCallback() {
        return this.callback;
    }
}
