package java.lang;

public class InternalError extends VirtualMachineError
{
    private static final long serialVersionUID = -9062593416125562365L;
    
    public InternalError() {
    }
    
    public InternalError(final String s) {
        super(s);
    }
    
    public InternalError(final String s, final Throwable t) {
        super(s, t);
    }
    
    public InternalError(final Throwable t) {
        super(t);
    }
}
