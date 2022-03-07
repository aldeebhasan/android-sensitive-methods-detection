package android.renderscript;

public class Script extends BaseObj
{
    Script() {
        throw new RuntimeException("Stub!");
    }
    
    protected KernelID createKernelID(final int slot, final int sig, final Element ein, final Element eout) {
        throw new RuntimeException("Stub!");
    }
    
    protected InvokeID createInvokeID(final int slot) {
        throw new RuntimeException("Stub!");
    }
    
    protected FieldID createFieldID(final int slot, final Element e) {
        throw new RuntimeException("Stub!");
    }
    
    protected void invoke(final int slot) {
        throw new RuntimeException("Stub!");
    }
    
    protected void invoke(final int slot, final FieldPacker v) {
        throw new RuntimeException("Stub!");
    }
    
    protected void forEach(final int slot, final Allocation ain, final Allocation aout, final FieldPacker v) {
        throw new RuntimeException("Stub!");
    }
    
    protected void forEach(final int slot, final Allocation ain, final Allocation aout, final FieldPacker v, final LaunchOptions sc) {
        throw new RuntimeException("Stub!");
    }
    
    protected void forEach(final int slot, final Allocation[] ains, final Allocation aout, final FieldPacker v) {
        throw new RuntimeException("Stub!");
    }
    
    protected void forEach(final int slot, final Allocation[] ains, final Allocation aout, final FieldPacker v, final LaunchOptions sc) {
        throw new RuntimeException("Stub!");
    }
    
    protected void reduce(final int slot, final Allocation[] ains, final Allocation aout, final LaunchOptions sc) {
        throw new RuntimeException("Stub!");
    }
    
    public void bindAllocation(final Allocation va, final int slot) {
        throw new RuntimeException("Stub!");
    }
    
    public void setVar(final int index, final float v) {
        throw new RuntimeException("Stub!");
    }
    
    public float getVarF(final int index) {
        throw new RuntimeException("Stub!");
    }
    
    public void setVar(final int index, final double v) {
        throw new RuntimeException("Stub!");
    }
    
    public double getVarD(final int index) {
        throw new RuntimeException("Stub!");
    }
    
    public void setVar(final int index, final int v) {
        throw new RuntimeException("Stub!");
    }
    
    public int getVarI(final int index) {
        throw new RuntimeException("Stub!");
    }
    
    public void setVar(final int index, final long v) {
        throw new RuntimeException("Stub!");
    }
    
    public long getVarJ(final int index) {
        throw new RuntimeException("Stub!");
    }
    
    public void setVar(final int index, final boolean v) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean getVarB(final int index) {
        throw new RuntimeException("Stub!");
    }
    
    public void setVar(final int index, final BaseObj o) {
        throw new RuntimeException("Stub!");
    }
    
    public void setVar(final int index, final FieldPacker v) {
        throw new RuntimeException("Stub!");
    }
    
    public void setVar(final int index, final FieldPacker v, final Element e, final int[] dims) {
        throw new RuntimeException("Stub!");
    }
    
    public void getVarV(final int index, final FieldPacker v) {
        throw new RuntimeException("Stub!");
    }
    
    public void setTimeZone(final String timeZone) {
        throw new RuntimeException("Stub!");
    }
    
    public static final class KernelID extends BaseObj
    {
        KernelID() {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static final class InvokeID extends BaseObj
    {
        InvokeID() {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static final class FieldID extends BaseObj
    {
        FieldID() {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static class Builder
    {
        Builder() {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static class FieldBase
    {
        protected Allocation mAllocation;
        protected Element mElement;
        
        protected FieldBase() {
            throw new RuntimeException("Stub!");
        }
        
        protected void init(final RenderScript rs, final int dimx) {
            throw new RuntimeException("Stub!");
        }
        
        protected void init(final RenderScript rs, final int dimx, final int usages) {
            throw new RuntimeException("Stub!");
        }
        
        public Element getElement() {
            throw new RuntimeException("Stub!");
        }
        
        public Type getType() {
            throw new RuntimeException("Stub!");
        }
        
        public Allocation getAllocation() {
            throw new RuntimeException("Stub!");
        }
        
        public void updateAllocation() {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static final class LaunchOptions
    {
        public LaunchOptions() {
            throw new RuntimeException("Stub!");
        }
        
        public LaunchOptions setX(final int xstartArg, final int xendArg) {
            throw new RuntimeException("Stub!");
        }
        
        public LaunchOptions setY(final int ystartArg, final int yendArg) {
            throw new RuntimeException("Stub!");
        }
        
        public LaunchOptions setZ(final int zstartArg, final int zendArg) {
            throw new RuntimeException("Stub!");
        }
        
        public int getXStart() {
            throw new RuntimeException("Stub!");
        }
        
        public int getXEnd() {
            throw new RuntimeException("Stub!");
        }
        
        public int getYStart() {
            throw new RuntimeException("Stub!");
        }
        
        public int getYEnd() {
            throw new RuntimeException("Stub!");
        }
        
        public int getZStart() {
            throw new RuntimeException("Stub!");
        }
        
        public int getZEnd() {
            throw new RuntimeException("Stub!");
        }
    }
}
