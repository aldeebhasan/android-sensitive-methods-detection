package android.view;

import android.graphics.*;

public interface SurfaceTextureListener
{
    void onSurfaceTextureAvailable(final SurfaceTexture p0, final int p1, final int p2);
    
    void onSurfaceTextureSizeChanged(final SurfaceTexture p0, final int p1, final int p2);
    
    boolean onSurfaceTextureDestroyed(final SurfaceTexture p0);
    
    void onSurfaceTextureUpdated(final SurfaceTexture p0);
}
