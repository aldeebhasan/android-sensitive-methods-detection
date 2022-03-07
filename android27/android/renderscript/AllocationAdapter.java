package android.renderscript;

public class AllocationAdapter extends Allocation
{
    AllocationAdapter() {
        throw new RuntimeException("Stub!");
    }
    
    public void setLOD(final int lod) {
        throw new RuntimeException("Stub!");
    }
    
    public void setFace(final Type.CubemapFace cf) {
        throw new RuntimeException("Stub!");
    }
    
    public void setX(final int x) {
        throw new RuntimeException("Stub!");
    }
    
    public void setY(final int y) {
        throw new RuntimeException("Stub!");
    }
    
    public void setZ(final int z) {
        throw new RuntimeException("Stub!");
    }
    
    public static AllocationAdapter create1D(final RenderScript rs, final Allocation a) {
        throw new RuntimeException("Stub!");
    }
    
    public static AllocationAdapter create2D(final RenderScript rs, final Allocation a) {
        throw new RuntimeException("Stub!");
    }
    
    public static AllocationAdapter createTyped(final RenderScript rs, final Allocation a, final Type t) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public synchronized void resize(final int dimX) {
        throw new RuntimeException("Stub!");
    }
}
