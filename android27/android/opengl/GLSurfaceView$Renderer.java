package android.opengl;

import javax.microedition.khronos.opengles.*;
import javax.microedition.khronos.egl.*;

public interface Renderer
{
    void onSurfaceCreated(final GL10 p0, final EGLConfig p1);
    
    void onSurfaceChanged(final GL10 p0, final int p1, final int p2);
    
    void onDrawFrame(final GL10 p0);
}
