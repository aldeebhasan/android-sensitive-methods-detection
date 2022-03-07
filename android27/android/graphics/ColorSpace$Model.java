package android.graphics;

public enum Model
{
    CMYK, 
    LAB, 
    RGB, 
    XYZ;
    
    public int getComponentCount() {
        throw new RuntimeException("Stub!");
    }
}
