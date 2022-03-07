package java.sql;

public class SQLIntegrityConstraintViolationException extends SQLNonTransientException
{
    private static final long serialVersionUID = 8033405298774849169L;
    
    public SQLIntegrityConstraintViolationException() {
    }
    
    public SQLIntegrityConstraintViolationException(final String s) {
        super(s);
    }
    
    public SQLIntegrityConstraintViolationException(final String s, final String s2) {
        super(s, s2);
    }
    
    public SQLIntegrityConstraintViolationException(final String s, final String s2, final int n) {
        super(s, s2, n);
    }
    
    public SQLIntegrityConstraintViolationException(final Throwable t) {
        super(t);
    }
    
    public SQLIntegrityConstraintViolationException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public SQLIntegrityConstraintViolationException(final String s, final String s2, final Throwable t) {
        super(s, s2, t);
    }
    
    public SQLIntegrityConstraintViolationException(final String s, final String s2, final int n, final Throwable t) {
        super(s, s2, n, t);
    }
}
