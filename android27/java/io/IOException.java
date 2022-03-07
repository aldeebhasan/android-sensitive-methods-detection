package java.io;

public class IOException extends Exception
{
    static final long serialVersionUID = 7818375828146090155L;
    
    public IOException() {
    }
    
    public IOException(final String s) {
        super(s);
    }
    
    public IOException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public IOException(final Throwable t) {
        super(t);
    }
}
