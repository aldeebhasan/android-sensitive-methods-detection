package android.widget;

import android.view.*;

@Deprecated
public class ZoomButtonsController implements View.OnTouchListener
{
    public ZoomButtonsController(final View ownerView) {
        throw new RuntimeException("Stub!");
    }
    
    public void setZoomInEnabled(final boolean enabled) {
        throw new RuntimeException("Stub!");
    }
    
    public void setZoomOutEnabled(final boolean enabled) {
        throw new RuntimeException("Stub!");
    }
    
    public void setZoomSpeed(final long speed) {
        throw new RuntimeException("Stub!");
    }
    
    public void setOnZoomListener(final OnZoomListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public void setFocusable(final boolean focusable) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isAutoDismissed() {
        throw new RuntimeException("Stub!");
    }
    
    public void setAutoDismissed(final boolean autoDismiss) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isVisible() {
        throw new RuntimeException("Stub!");
    }
    
    public void setVisible(final boolean visible) {
        throw new RuntimeException("Stub!");
    }
    
    public ViewGroup getContainer() {
        throw new RuntimeException("Stub!");
    }
    
    public View getZoomControls() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean onTouch(final View v, final MotionEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    public interface OnZoomListener
    {
        void onVisibilityChanged(final boolean p0);
        
        void onZoom(final boolean p0);
    }
}
