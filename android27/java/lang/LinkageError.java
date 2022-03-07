package java.lang;

public class LinkageError extends Error
{
    private static final long serialVersionUID = 3579600108157160122L;
    
    public LinkageError() {
    }
    
    public LinkageError(final String s) {
        super(s);
    }
    
    public LinkageError(final String s, final Throwable t) {
        super(s, t);
    }
}
