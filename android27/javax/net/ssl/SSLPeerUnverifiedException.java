package javax.net.ssl;

public class SSLPeerUnverifiedException extends SSLException
{
    private static final long serialVersionUID = -8919512675000600547L;
    
    public SSLPeerUnverifiedException(final String s) {
        super(s);
    }
}
