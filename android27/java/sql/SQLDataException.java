package java.sql;

public class SQLDataException extends SQLNonTransientException
{
    private static final long serialVersionUID = -6889123282670549800L;
    
    public SQLDataException() {
    }
    
    public SQLDataException(final String s) {
        super(s);
    }
    
    public SQLDataException(final String s, final String s2) {
        super(s, s2);
    }
    
    public SQLDataException(final String s, final String s2, final int n) {
        super(s, s2, n);
    }
    
    public SQLDataException(final Throwable t) {
        super(t);
    }
    
    public SQLDataException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public SQLDataException(final String s, final String s2, final Throwable t) {
        super(s, s2, t);
    }
    
    public SQLDataException(final String s, final String s2, final int n, final Throwable t) {
        super(s, s2, n, t);
    }
}
