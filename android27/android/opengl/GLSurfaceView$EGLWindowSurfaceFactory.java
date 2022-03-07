package android.opengl;

import javax.microedition.khronos.egl.*;

public interface EGLWindowSurfaceFactory
{
    EGLSurface createWindowSurface(final EGL10 p0, final EGLDisplay p1, final EGLConfig p2, final Object p3);
    
    void destroySurface(final EGL10 p0, final EGLDisplay p1, final EGLSurface p2);
}
