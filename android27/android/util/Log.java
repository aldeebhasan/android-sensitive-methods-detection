package android.util;

public final class Log
{
    public static final int ASSERT = 7;
    public static final int DEBUG = 3;
    public static final int ERROR = 6;
    public static final int INFO = 4;
    public static final int VERBOSE = 2;
    public static final int WARN = 5;
    
    Log() {
        throw new RuntimeException("Stub!");
    }
    
    public static int v(final String tag, final String msg) {
        throw new RuntimeException("Stub!");
    }
    
    public static int v(final String tag, final String msg, final Throwable tr) {
        throw new RuntimeException("Stub!");
    }
    
    public static int d(final String tag, final String msg) {
        throw new RuntimeException("Stub!");
    }
    
    public static int d(final String tag, final String msg, final Throwable tr) {
        throw new RuntimeException("Stub!");
    }
    
    public static int i(final String tag, final String msg) {
        throw new RuntimeException("Stub!");
    }
    
    public static int i(final String tag, final String msg, final Throwable tr) {
        throw new RuntimeException("Stub!");
    }
    
    public static int w(final String tag, final String msg) {
        throw new RuntimeException("Stub!");
    }
    
    public static int w(final String tag, final String msg, final Throwable tr) {
        throw new RuntimeException("Stub!");
    }
    
    public static native boolean isLoggable(final String p0, final int p1);
    
    public static int w(final String tag, final Throwable tr) {
        throw new RuntimeException("Stub!");
    }
    
    public static int e(final String tag, final String msg) {
        throw new RuntimeException("Stub!");
    }
    
    public static int e(final String tag, final String msg, final Throwable tr) {
        throw new RuntimeException("Stub!");
    }
    
    public static int wtf(final String tag, final String msg) {
        throw new RuntimeException("Stub!");
    }
    
    public static int wtf(final String tag, final Throwable tr) {
        throw new RuntimeException("Stub!");
    }
    
    public static int wtf(final String tag, final String msg, final Throwable tr) {
        throw new RuntimeException("Stub!");
    }
    
    public static String getStackTraceString(final Throwable tr) {
        throw new RuntimeException("Stub!");
    }
    
    public static int println(final int priority, final String tag, final String msg) {
        throw new RuntimeException("Stub!");
    }
}
