package android.renderscript;

public class Type extends BaseObj
{
    Type() {
        throw new RuntimeException("Stub!");
    }
    
    public Element getElement() {
        throw new RuntimeException("Stub!");
    }
    
    public int getX() {
        throw new RuntimeException("Stub!");
    }
    
    public int getY() {
        throw new RuntimeException("Stub!");
    }
    
    public int getZ() {
        throw new RuntimeException("Stub!");
    }
    
    public int getYuv() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean hasMipmaps() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean hasFaces() {
        throw new RuntimeException("Stub!");
    }
    
    public int getCount() {
        throw new RuntimeException("Stub!");
    }
    
    public static Type createX(final RenderScript rs, final Element e, final int dimX) {
        throw new RuntimeException("Stub!");
    }
    
    public static Type createXY(final RenderScript rs, final Element e, final int dimX, final int dimY) {
        throw new RuntimeException("Stub!");
    }
    
    public static Type createXYZ(final RenderScript rs, final Element e, final int dimX, final int dimY, final int dimZ) {
        throw new RuntimeException("Stub!");
    }
    
    public enum CubemapFace
    {
        NEGATIVE_X, 
        NEGATIVE_Y, 
        NEGATIVE_Z, 
        POSITIVE_X, 
        POSITIVE_Y, 
        POSITIVE_Z, 
        POSITVE_X, 
        POSITVE_Y, 
        POSITVE_Z;
    }
    
    public static class Builder
    {
        public Builder(final RenderScript rs, final Element e) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setX(final int value) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setY(final int value) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setZ(final int value) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setMipmaps(final boolean value) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setFaces(final boolean value) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setYuvFormat(final int yuvFormat) {
            throw new RuntimeException("Stub!");
        }
        
        public Type create() {
            throw new RuntimeException("Stub!");
        }
    }
}
