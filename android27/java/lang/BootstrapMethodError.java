package java.lang;

public class BootstrapMethodError extends LinkageError
{
    private static final long serialVersionUID = 292L;
    
    public BootstrapMethodError() {
    }
    
    public BootstrapMethodError(final String s) {
        super(s);
    }
    
    public BootstrapMethodError(final String s, final Throwable t) {
        super(s, t);
    }
    
    public BootstrapMethodError(final Throwable t) {
        super((t == null) ? null : t.toString());
        this.initCause(t);
    }
}
