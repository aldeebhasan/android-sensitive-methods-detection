package android.view;

import android.content.*;
import android.util.*;
import android.graphics.drawable.*;
import android.graphics.*;

public class TextureView extends View
{
    public TextureView(final Context context) {
        super(null, null, 0, 0);
        throw new RuntimeException("Stub!");
    }
    
    public TextureView(final Context context, final AttributeSet attrs) {
        super(null, null, 0, 0);
        throw new RuntimeException("Stub!");
    }
    
    public TextureView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(null, null, 0, 0);
        throw new RuntimeException("Stub!");
    }
    
    public TextureView(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
        super(null, null, 0, 0);
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean isOpaque() {
        throw new RuntimeException("Stub!");
    }
    
    public void setOpaque(final boolean opaque) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onAttachedToWindow() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void setLayerType(final int layerType, final Paint paint) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void setLayerPaint(final Paint paint) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int getLayerType() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void buildLayer() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void setForeground(final Drawable foreground) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void setBackgroundDrawable(final Drawable background) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public final void draw(final Canvas canvas) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected final void onDraw(final Canvas canvas) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onSizeChanged(final int w, final int h, final int oldw, final int oldh) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onVisibilityChanged(final View changedView, final int visibility) {
        throw new RuntimeException("Stub!");
    }
    
    public void setTransform(final Matrix transform) {
        throw new RuntimeException("Stub!");
    }
    
    public Matrix getTransform(final Matrix transform) {
        throw new RuntimeException("Stub!");
    }
    
    public Bitmap getBitmap() {
        throw new RuntimeException("Stub!");
    }
    
    public Bitmap getBitmap(final int width, final int height) {
        throw new RuntimeException("Stub!");
    }
    
    public Bitmap getBitmap(final Bitmap bitmap) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isAvailable() {
        throw new RuntimeException("Stub!");
    }
    
    public Canvas lockCanvas() {
        throw new RuntimeException("Stub!");
    }
    
    public Canvas lockCanvas(final Rect dirty) {
        throw new RuntimeException("Stub!");
    }
    
    public void unlockCanvasAndPost(final Canvas canvas) {
        throw new RuntimeException("Stub!");
    }
    
    public SurfaceTexture getSurfaceTexture() {
        throw new RuntimeException("Stub!");
    }
    
    public void setSurfaceTexture(final SurfaceTexture surfaceTexture) {
        throw new RuntimeException("Stub!");
    }
    
    public SurfaceTextureListener getSurfaceTextureListener() {
        throw new RuntimeException("Stub!");
    }
    
    public void setSurfaceTextureListener(final SurfaceTextureListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public interface SurfaceTextureListener
    {
        void onSurfaceTextureAvailable(final SurfaceTexture p0, final int p1, final int p2);
        
        void onSurfaceTextureSizeChanged(final SurfaceTexture p0, final int p1, final int p2);
        
        boolean onSurfaceTextureDestroyed(final SurfaceTexture p0);
        
        void onSurfaceTextureUpdated(final SurfaceTexture p0);
    }
}
