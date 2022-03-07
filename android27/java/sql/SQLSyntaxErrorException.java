package java.sql;

public class SQLSyntaxErrorException extends SQLNonTransientException
{
    private static final long serialVersionUID = -1843832610477496053L;
    
    public SQLSyntaxErrorException() {
    }
    
    public SQLSyntaxErrorException(final String s) {
        super(s);
    }
    
    public SQLSyntaxErrorException(final String s, final String s2) {
        super(s, s2);
    }
    
    public SQLSyntaxErrorException(final String s, final String s2, final int n) {
        super(s, s2, n);
    }
    
    public SQLSyntaxErrorException(final Throwable t) {
        super(t);
    }
    
    public SQLSyntaxErrorException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public SQLSyntaxErrorException(final String s, final String s2, final Throwable t) {
        super(s, s2, t);
    }
    
    public SQLSyntaxErrorException(final String s, final String s2, final int n, final Throwable t) {
        super(s, s2, n, t);
    }
}
