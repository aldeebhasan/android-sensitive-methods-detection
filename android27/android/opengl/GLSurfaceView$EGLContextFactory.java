package android.opengl;

import javax.microedition.khronos.egl.*;

public interface EGLContextFactory
{
    EGLContext createContext(final EGL10 p0, final EGLDisplay p1, final EGLConfig p2);
    
    void destroyContext(final EGL10 p0, final EGLDisplay p1, final EGLContext p2);
}
