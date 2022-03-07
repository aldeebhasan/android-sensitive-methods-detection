package java.sql;

public class SQLWarning extends SQLException
{
    private static final long serialVersionUID = 3917336774604784856L;
    
    public SQLWarning(final String s, final String s2, final int n) {
        super(s, s2, n);
        DriverManager.println("SQLWarning: reason(" + s + ") SQLState(" + s2 + ") vendor code(" + n + ")");
    }
    
    public SQLWarning(final String s, final String s2) {
        super(s, s2);
        DriverManager.println("SQLWarning: reason(" + s + ") SQLState(" + s2 + ")");
    }
    
    public SQLWarning(final String s) {
        super(s);
        DriverManager.println("SQLWarning: reason(" + s + ")");
    }
    
    public SQLWarning() {
        DriverManager.println("SQLWarning: ");
    }
    
    public SQLWarning(final Throwable t) {
        super(t);
        DriverManager.println("SQLWarning");
    }
    
    public SQLWarning(final String s, final Throwable t) {
        super(s, t);
        DriverManager.println("SQLWarning : reason(" + s + ")");
    }
    
    public SQLWarning(final String s, final String s2, final Throwable t) {
        super(s, s2, t);
        DriverManager.println("SQLWarning: reason(" + s + ") SQLState(" + s2 + ")");
    }
    
    public SQLWarning(final String s, final String s2, final int n, final Throwable t) {
        super(s, s2, n, t);
        DriverManager.println("SQLWarning: reason(" + s + ") SQLState(" + s2 + ") vendor code(" + n + ")");
    }
    
    public SQLWarning getNextWarning() {
        try {
            return (SQLWarning)this.getNextException();
        }
        catch (ClassCastException ex) {
            throw new Error("SQLWarning chain holds value that is not a SQLWarning");
        }
    }
    
    public void setNextWarning(final SQLWarning nextException) {
        this.setNextException(nextException);
    }
}
