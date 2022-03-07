package java.lang.invoke;

public class ConstantCallSite extends CallSite
{
    private final boolean isFrozen;
    
    public ConstantCallSite(final MethodHandle methodHandle) {
        super(methodHandle);
        this.isFrozen = true;
    }
    
    protected ConstantCallSite(final MethodType methodType, final MethodHandle methodHandle) throws Throwable {
        super(methodType, methodHandle);
        this.isFrozen = true;
    }
    
    @Override
    public final MethodHandle getTarget() {
        if (!this.isFrozen) {
            throw new IllegalStateException();
        }
        return this.target;
    }
    
    @Override
    public final void setTarget(final MethodHandle methodHandle) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public final MethodHandle dynamicInvoker() {
        return this.getTarget();
    }
}
