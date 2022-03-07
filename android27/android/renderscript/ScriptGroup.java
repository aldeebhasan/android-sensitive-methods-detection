package android.renderscript;

public final class ScriptGroup extends BaseObj
{
    ScriptGroup() {
        throw new RuntimeException("Stub!");
    }
    
    public Object[] execute(final Object... inputs) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void setInput(final Script.KernelID s, final Allocation a) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void setOutput(final Script.KernelID s, final Allocation a) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void execute() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void destroy() {
        throw new RuntimeException("Stub!");
    }
    
    public static final class Closure extends BaseObj
    {
        Closure() {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public void destroy() {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        protected void finalize() throws Throwable {
            throw new RuntimeException("Stub!");
        }
        
        public Future getReturn() {
            throw new RuntimeException("Stub!");
        }
        
        public Future getGlobal(final Script.FieldID field) {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static final class Future
    {
        Future() {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static final class Input
    {
        Input() {
            throw new RuntimeException("Stub!");
        }
    }
    
    @Deprecated
    public static final class Builder
    {
        public Builder(final RenderScript rs) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder addKernel(final Script.KernelID k) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder addConnection(final Type t, final Script.KernelID from, final Script.FieldID to) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder addConnection(final Type t, final Script.KernelID from, final Script.KernelID to) {
            throw new RuntimeException("Stub!");
        }
        
        public ScriptGroup create() {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static final class Binding
    {
        public Binding(final Script.FieldID field, final Object value) {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static final class Builder2
    {
        public Builder2(final RenderScript rs) {
            throw new RuntimeException("Stub!");
        }
        
        public Input addInput() {
            throw new RuntimeException("Stub!");
        }
        
        public Closure addKernel(final Script.KernelID k, final Type returnType, final Object... argsAndBindings) {
            throw new RuntimeException("Stub!");
        }
        
        public Closure addInvoke(final Script.InvokeID invoke, final Object... argsAndBindings) {
            throw new RuntimeException("Stub!");
        }
        
        public ScriptGroup create(final String name, final Future... outputs) {
            throw new RuntimeException("Stub!");
        }
    }
}
