package android.opengl;

import javax.microedition.khronos.egl.*;

public interface EGLConfigChooser
{
    EGLConfig chooseConfig(final EGL10 p0, final EGLDisplay p1);
}
