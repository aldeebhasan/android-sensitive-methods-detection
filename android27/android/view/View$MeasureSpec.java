package android.view;

public static class MeasureSpec
{
    public static final int AT_MOST = Integer.MIN_VALUE;
    public static final int EXACTLY = 1073741824;
    public static final int UNSPECIFIED = 0;
    
    public MeasureSpec() {
        throw new RuntimeException("Stub!");
    }
    
    public static int makeMeasureSpec(final int size, final int mode) {
        throw new RuntimeException("Stub!");
    }
    
    public static int getMode(final int measureSpec) {
        throw new RuntimeException("Stub!");
    }
    
    public static int getSize(final int measureSpec) {
        throw new RuntimeException("Stub!");
    }
    
    public static String toString(final int measureSpec) {
        throw new RuntimeException("Stub!");
    }
}
