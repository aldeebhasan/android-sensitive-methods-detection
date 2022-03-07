package android.inputmethodservice;

import android.content.*;
import android.util.*;
import android.graphics.*;
import android.view.*;

public class KeyboardView extends View implements OnClickListener
{
    public KeyboardView(final Context context, final AttributeSet attrs) {
        super(null, null, 0, 0);
        throw new RuntimeException("Stub!");
    }
    
    public KeyboardView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(null, null, 0, 0);
        throw new RuntimeException("Stub!");
    }
    
    public KeyboardView(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
        super(null, null, 0, 0);
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onAttachedToWindow() {
        throw new RuntimeException("Stub!");
    }
    
    public void setOnKeyboardActionListener(final OnKeyboardActionListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    protected OnKeyboardActionListener getOnKeyboardActionListener() {
        throw new RuntimeException("Stub!");
    }
    
    public void setKeyboard(final Keyboard keyboard) {
        throw new RuntimeException("Stub!");
    }
    
    public Keyboard getKeyboard() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean setShifted(final boolean shifted) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isShifted() {
        throw new RuntimeException("Stub!");
    }
    
    public void setPreviewEnabled(final boolean previewEnabled) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isPreviewEnabled() {
        throw new RuntimeException("Stub!");
    }
    
    public void setVerticalCorrection(final int verticalOffset) {
        throw new RuntimeException("Stub!");
    }
    
    public void setPopupParent(final View v) {
        throw new RuntimeException("Stub!");
    }
    
    public void setPopupOffset(final int x, final int y) {
        throw new RuntimeException("Stub!");
    }
    
    public void setProximityCorrectionEnabled(final boolean enabled) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isProximityCorrectionEnabled() {
        throw new RuntimeException("Stub!");
    }
    
    public void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        throw new RuntimeException("Stub!");
    }
    
    public void onSizeChanged(final int w, final int h, final int oldw, final int oldh) {
        throw new RuntimeException("Stub!");
    }
    
    public void onDraw(final Canvas canvas) {
        throw new RuntimeException("Stub!");
    }
    
    public void invalidateAllKeys() {
        throw new RuntimeException("Stub!");
    }
    
    public void invalidateKey(final int keyIndex) {
        throw new RuntimeException("Stub!");
    }
    
    protected boolean onLongPress(final Keyboard.Key popupKey) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean onHoverEvent(final MotionEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean onTouchEvent(final MotionEvent me) {
        throw new RuntimeException("Stub!");
    }
    
    protected void swipeRight() {
        throw new RuntimeException("Stub!");
    }
    
    protected void swipeLeft() {
        throw new RuntimeException("Stub!");
    }
    
    protected void swipeUp() {
        throw new RuntimeException("Stub!");
    }
    
    protected void swipeDown() {
        throw new RuntimeException("Stub!");
    }
    
    public void closing() {
        throw new RuntimeException("Stub!");
    }
    
    public void onDetachedFromWindow() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean handleBack() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onClick(final View v) {
        throw new RuntimeException("Stub!");
    }
    
    public interface OnKeyboardActionListener
    {
        void onPress(final int p0);
        
        void onRelease(final int p0);
        
        void onKey(final int p0, final int[] p1);
        
        void onText(final CharSequence p0);
        
        void swipeLeft();
        
        void swipeRight();
        
        void swipeDown();
        
        void swipeUp();
    }
}
