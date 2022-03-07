package java.sql;

public class SQLFeatureNotSupportedException extends SQLNonTransientException
{
    private static final long serialVersionUID = -1026510870282316051L;
    
    public SQLFeatureNotSupportedException() {
    }
    
    public SQLFeatureNotSupportedException(final String s) {
        super(s);
    }
    
    public SQLFeatureNotSupportedException(final String s, final String s2) {
        super(s, s2);
    }
    
    public SQLFeatureNotSupportedException(final String s, final String s2, final int n) {
        super(s, s2, n);
    }
    
    public SQLFeatureNotSupportedException(final Throwable t) {
        super(t);
    }
    
    public SQLFeatureNotSupportedException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public SQLFeatureNotSupportedException(final String s, final String s2, final Throwable t) {
        super(s, s2, t);
    }
    
    public SQLFeatureNotSupportedException(final String s, final String s2, final int n, final Throwable t) {
        super(s, s2, n, t);
    }
}
