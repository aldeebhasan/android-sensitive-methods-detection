package android.renderscript;

public class Matrix4f
{
    public Matrix4f() {
        throw new RuntimeException("Stub!");
    }
    
    public Matrix4f(final float[] dataArray) {
        throw new RuntimeException("Stub!");
    }
    
    public float[] getArray() {
        throw new RuntimeException("Stub!");
    }
    
    public float get(final int x, final int y) {
        throw new RuntimeException("Stub!");
    }
    
    public void set(final int x, final int y, final float v) {
        throw new RuntimeException("Stub!");
    }
    
    public void loadIdentity() {
        throw new RuntimeException("Stub!");
    }
    
    public void load(final Matrix4f src) {
        throw new RuntimeException("Stub!");
    }
    
    public void loadRotate(final float rot, final float x, final float y, final float z) {
        throw new RuntimeException("Stub!");
    }
    
    public void loadScale(final float x, final float y, final float z) {
        throw new RuntimeException("Stub!");
    }
    
    public void loadTranslate(final float x, final float y, final float z) {
        throw new RuntimeException("Stub!");
    }
    
    public void loadMultiply(final Matrix4f lhs, final Matrix4f rhs) {
        throw new RuntimeException("Stub!");
    }
    
    public void loadOrtho(final float l, final float r, final float b, final float t, final float n, final float f) {
        throw new RuntimeException("Stub!");
    }
    
    public void loadOrthoWindow(final int w, final int h) {
        throw new RuntimeException("Stub!");
    }
    
    public void loadFrustum(final float l, final float r, final float b, final float t, final float n, final float f) {
        throw new RuntimeException("Stub!");
    }
    
    public void loadPerspective(final float fovy, final float aspect, final float near, final float far) {
        throw new RuntimeException("Stub!");
    }
    
    public void loadProjectionNormalized(final int w, final int h) {
        throw new RuntimeException("Stub!");
    }
    
    public void multiply(final Matrix4f rhs) {
        throw new RuntimeException("Stub!");
    }
    
    public void rotate(final float rot, final float x, final float y, final float z) {
        throw new RuntimeException("Stub!");
    }
    
    public void scale(final float x, final float y, final float z) {
        throw new RuntimeException("Stub!");
    }
    
    public void translate(final float x, final float y, final float z) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean inverse() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean inverseTranspose() {
        throw new RuntimeException("Stub!");
    }
    
    public void transpose() {
        throw new RuntimeException("Stub!");
    }
}
