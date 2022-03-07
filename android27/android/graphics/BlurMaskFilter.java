package android.graphics;

public class BlurMaskFilter extends MaskFilter
{
    public BlurMaskFilter(final float radius, final Blur style) {
        throw new RuntimeException("Stub!");
    }
    
    public enum Blur
    {
        INNER, 
        NORMAL, 
        OUTER, 
        SOLID;
    }
}
