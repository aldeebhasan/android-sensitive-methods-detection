package android.renderscript;

public class Sampler extends BaseObj
{
    Sampler() {
        throw new RuntimeException("Stub!");
    }
    
    public Value getMinification() {
        throw new RuntimeException("Stub!");
    }
    
    public Value getMagnification() {
        throw new RuntimeException("Stub!");
    }
    
    public Value getWrapS() {
        throw new RuntimeException("Stub!");
    }
    
    public Value getWrapT() {
        throw new RuntimeException("Stub!");
    }
    
    public float getAnisotropy() {
        throw new RuntimeException("Stub!");
    }
    
    public static Sampler CLAMP_NEAREST(final RenderScript rs) {
        throw new RuntimeException("Stub!");
    }
    
    public static Sampler CLAMP_LINEAR(final RenderScript rs) {
        throw new RuntimeException("Stub!");
    }
    
    public static Sampler CLAMP_LINEAR_MIP_LINEAR(final RenderScript rs) {
        throw new RuntimeException("Stub!");
    }
    
    public static Sampler WRAP_NEAREST(final RenderScript rs) {
        throw new RuntimeException("Stub!");
    }
    
    public static Sampler WRAP_LINEAR(final RenderScript rs) {
        throw new RuntimeException("Stub!");
    }
    
    public static Sampler WRAP_LINEAR_MIP_LINEAR(final RenderScript rs) {
        throw new RuntimeException("Stub!");
    }
    
    public static Sampler MIRRORED_REPEAT_NEAREST(final RenderScript rs) {
        throw new RuntimeException("Stub!");
    }
    
    public static Sampler MIRRORED_REPEAT_LINEAR(final RenderScript rs) {
        throw new RuntimeException("Stub!");
    }
    
    public static Sampler MIRRORED_REPEAT_LINEAR_MIP_LINEAR(final RenderScript rs) {
        throw new RuntimeException("Stub!");
    }
    
    public enum Value
    {
        CLAMP, 
        LINEAR, 
        LINEAR_MIP_LINEAR, 
        LINEAR_MIP_NEAREST, 
        MIRRORED_REPEAT, 
        NEAREST, 
        WRAP;
    }
    
    public static class Builder
    {
        public Builder(final RenderScript rs) {
            throw new RuntimeException("Stub!");
        }
        
        public void setMinification(final Value v) {
            throw new RuntimeException("Stub!");
        }
        
        public void setMagnification(final Value v) {
            throw new RuntimeException("Stub!");
        }
        
        public void setWrapS(final Value v) {
            throw new RuntimeException("Stub!");
        }
        
        public void setWrapT(final Value v) {
            throw new RuntimeException("Stub!");
        }
        
        public void setAnisotropy(final float v) {
            throw new RuntimeException("Stub!");
        }
        
        public Sampler create() {
            throw new RuntimeException("Stub!");
        }
    }
}
