package java.lang;

public class Error extends Throwable
{
    static final long serialVersionUID = 4980196508277280342L;
    
    public Error() {
    }
    
    public Error(final String s) {
        super(s);
    }
    
    public Error(final String s, final Throwable t) {
        super(s, t);
    }
    
    public Error(final Throwable t) {
        super(t);
    }
    
    protected Error(final String s, final Throwable t, final boolean b, final boolean b2) {
        super(s, t, b, b2);
    }
}
