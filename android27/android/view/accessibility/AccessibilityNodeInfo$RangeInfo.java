package android.view.accessibility;

public static final class RangeInfo
{
    public static final int RANGE_TYPE_FLOAT = 1;
    public static final int RANGE_TYPE_INT = 0;
    public static final int RANGE_TYPE_PERCENT = 2;
    
    RangeInfo() {
        throw new RuntimeException("Stub!");
    }
    
    public static RangeInfo obtain(final int type, final float min, final float max, final float current) {
        throw new RuntimeException("Stub!");
    }
    
    public int getType() {
        throw new RuntimeException("Stub!");
    }
    
    public float getMin() {
        throw new RuntimeException("Stub!");
    }
    
    public float getMax() {
        throw new RuntimeException("Stub!");
    }
    
    public float getCurrent() {
        throw new RuntimeException("Stub!");
    }
}
