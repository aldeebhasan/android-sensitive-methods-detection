package java.lang.invoke;

import java.util.concurrent.atomic.*;

public class MutableCallSite extends CallSite
{
    private static final AtomicInteger STORE_BARRIER;
    
    public MutableCallSite(final MethodType methodType) {
        super(methodType);
    }
    
    public MutableCallSite(final MethodHandle methodHandle) {
        super(methodHandle);
    }
    
    @Override
    public final MethodHandle getTarget() {
        return this.target;
    }
    
    @Override
    public void setTarget(final MethodHandle targetNormal) {
        this.checkTargetChange(this.target, targetNormal);
        this.setTargetNormal(targetNormal);
    }
    
    @Override
    public final MethodHandle dynamicInvoker() {
        return this.makeDynamicInvoker();
    }
    
    public static void syncAll(final MutableCallSite[] array) {
        if (array.length == 0) {
            return;
        }
        MutableCallSite.STORE_BARRIER.lazySet(0);
        for (int i = 0; i < array.length; ++i) {
            array[i].getClass();
        }
    }
    
    static {
        STORE_BARRIER = new AtomicInteger();
    }
}
