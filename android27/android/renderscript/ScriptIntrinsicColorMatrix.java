package android.renderscript;

public final class ScriptIntrinsicColorMatrix extends ScriptIntrinsic
{
    ScriptIntrinsicColorMatrix() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public static ScriptIntrinsicColorMatrix create(final RenderScript rs, final Element e) {
        throw new RuntimeException("Stub!");
    }
    
    public static ScriptIntrinsicColorMatrix create(final RenderScript rs) {
        throw new RuntimeException("Stub!");
    }
    
    public void setColorMatrix(final Matrix4f m) {
        throw new RuntimeException("Stub!");
    }
    
    public void setColorMatrix(final Matrix3f m) {
        throw new RuntimeException("Stub!");
    }
    
    public void setAdd(final Float4 f) {
        throw new RuntimeException("Stub!");
    }
    
    public void setAdd(final float r, final float g, final float b, final float a) {
        throw new RuntimeException("Stub!");
    }
    
    public void setGreyscale() {
        throw new RuntimeException("Stub!");
    }
    
    public void setYUVtoRGB() {
        throw new RuntimeException("Stub!");
    }
    
    public void setRGBtoYUV() {
        throw new RuntimeException("Stub!");
    }
    
    public void forEach(final Allocation ain, final Allocation aout) {
        throw new RuntimeException("Stub!");
    }
    
    public void forEach(final Allocation ain, final Allocation aout, final LaunchOptions opt) {
        throw new RuntimeException("Stub!");
    }
    
    public KernelID getKernelID() {
        throw new RuntimeException("Stub!");
    }
}
