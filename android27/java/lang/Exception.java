package java.lang;

public class Exception extends Throwable
{
    static final long serialVersionUID = -3387516993124229948L;
    
    public Exception() {
    }
    
    public Exception(final String s) {
        super(s);
    }
    
    public Exception(final String s, final Throwable t) {
        super(s, t);
    }
    
    public Exception(final Throwable t) {
        super(t);
    }
    
    protected Exception(final String s, final Throwable t, final boolean b, final boolean b2) {
        super(s, t, b, b2);
    }
}
