package android.opengl;

public class EGLExt
{
    public static final int EGL_CONTEXT_FLAGS_KHR = 12540;
    public static final int EGL_CONTEXT_MAJOR_VERSION_KHR = 12440;
    public static final int EGL_CONTEXT_MINOR_VERSION_KHR = 12539;
    public static final int EGL_OPENGL_ES3_BIT_KHR = 64;
    public static final int EGL_RECORDABLE_ANDROID = 12610;
    
    public EGLExt() {
        throw new RuntimeException("Stub!");
    }
    
    public static native boolean eglPresentationTimeANDROID(final EGLDisplay p0, final EGLSurface p1, final long p2);
}
