package java.lang;

public class InstantiationError extends IncompatibleClassChangeError
{
    private static final long serialVersionUID = -4885810657349421204L;
    
    public InstantiationError() {
    }
    
    public InstantiationError(final String s) {
        super(s);
    }
}
