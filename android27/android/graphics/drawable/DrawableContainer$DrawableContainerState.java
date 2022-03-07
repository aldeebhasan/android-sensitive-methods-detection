package android.graphics.drawable;

import android.graphics.*;

public abstract static class DrawableContainerState extends ConstantState
{
    DrawableContainerState() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int getChangingConfigurations() {
        throw new RuntimeException("Stub!");
    }
    
    public final int addChild(final Drawable dr) {
        throw new RuntimeException("Stub!");
    }
    
    public final int getChildCount() {
        throw new RuntimeException("Stub!");
    }
    
    public final Drawable[] getChildren() {
        throw new RuntimeException("Stub!");
    }
    
    public final Drawable getChild(final int index) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean canApplyTheme() {
        throw new RuntimeException("Stub!");
    }
    
    public final void setVariablePadding(final boolean variable) {
        throw new RuntimeException("Stub!");
    }
    
    public final Rect getConstantPadding() {
        throw new RuntimeException("Stub!");
    }
    
    public final void setConstantSize(final boolean constant) {
        throw new RuntimeException("Stub!");
    }
    
    public final boolean isConstantSize() {
        throw new RuntimeException("Stub!");
    }
    
    public final int getConstantWidth() {
        throw new RuntimeException("Stub!");
    }
    
    public final int getConstantHeight() {
        throw new RuntimeException("Stub!");
    }
    
    public final int getConstantMinimumWidth() {
        throw new RuntimeException("Stub!");
    }
    
    public final int getConstantMinimumHeight() {
        throw new RuntimeException("Stub!");
    }
    
    protected void computeConstantSize() {
        throw new RuntimeException("Stub!");
    }
    
    public final void setEnterFadeDuration(final int duration) {
        throw new RuntimeException("Stub!");
    }
    
    public final int getEnterFadeDuration() {
        throw new RuntimeException("Stub!");
    }
    
    public final void setExitFadeDuration(final int duration) {
        throw new RuntimeException("Stub!");
    }
    
    public final int getExitFadeDuration() {
        throw new RuntimeException("Stub!");
    }
    
    public final int getOpacity() {
        throw new RuntimeException("Stub!");
    }
    
    public final boolean isStateful() {
        throw new RuntimeException("Stub!");
    }
    
    public void growArray(final int oldSize, final int newSize) {
        throw new RuntimeException("Stub!");
    }
    
    public synchronized boolean canConstantState() {
        throw new RuntimeException("Stub!");
    }
}
