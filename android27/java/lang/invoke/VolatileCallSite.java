package java.lang.invoke;

public class VolatileCallSite extends CallSite
{
    public VolatileCallSite(final MethodType methodType) {
        super(methodType);
    }
    
    public VolatileCallSite(final MethodHandle methodHandle) {
        super(methodHandle);
    }
    
    @Override
    public final MethodHandle getTarget() {
        return this.getTargetVolatile();
    }
    
    @Override
    public void setTarget(final MethodHandle targetVolatile) {
        this.checkTargetChange(this.getTargetVolatile(), targetVolatile);
        this.setTargetVolatile(targetVolatile);
    }
    
    @Override
    public final MethodHandle dynamicInvoker() {
        return this.makeDynamicInvoker();
    }
}
