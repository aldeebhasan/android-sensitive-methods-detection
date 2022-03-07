package java.lang;

public abstract class VirtualMachineError extends Error
{
    private static final long serialVersionUID = 4161983926571568670L;
    
    public VirtualMachineError() {
    }
    
    public VirtualMachineError(final String s) {
        super(s);
    }
    
    public VirtualMachineError(final String s, final Throwable t) {
        super(s, t);
    }
    
    public VirtualMachineError(final Throwable t) {
        super(t);
    }
}
