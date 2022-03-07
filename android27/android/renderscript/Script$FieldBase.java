package android.renderscript;

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
