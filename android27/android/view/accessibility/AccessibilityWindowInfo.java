package android.view.accessibility;

import android.graphics.*;
import android.os.*;

public final class AccessibilityWindowInfo implements Parcelable
{
    public static final Creator<AccessibilityWindowInfo> CREATOR;
    public static final int TYPE_ACCESSIBILITY_OVERLAY = 4;
    public static final int TYPE_APPLICATION = 1;
    public static final int TYPE_INPUT_METHOD = 2;
    public static final int TYPE_SPLIT_SCREEN_DIVIDER = 5;
    public static final int TYPE_SYSTEM = 3;
    
    AccessibilityWindowInfo() {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence getTitle() {
        throw new RuntimeException("Stub!");
    }
    
    public int getType() {
        throw new RuntimeException("Stub!");
    }
    
    public int getLayer() {
        throw new RuntimeException("Stub!");
    }
    
    public AccessibilityNodeInfo getRoot() {
        throw new RuntimeException("Stub!");
    }
    
    public AccessibilityNodeInfo getAnchor() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isInPictureInPictureMode() {
        throw new RuntimeException("Stub!");
    }
    
    public AccessibilityWindowInfo getParent() {
        throw new RuntimeException("Stub!");
    }
    
    public int getId() {
        throw new RuntimeException("Stub!");
    }
    
    public void getBoundsInScreen(final Rect outBounds) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isActive() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isFocused() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isAccessibilityFocused() {
        throw new RuntimeException("Stub!");
    }
    
    public int getChildCount() {
        throw new RuntimeException("Stub!");
    }
    
    public AccessibilityWindowInfo getChild(final int index) {
        throw new RuntimeException("Stub!");
    }
    
    public static AccessibilityWindowInfo obtain() {
        throw new RuntimeException("Stub!");
    }
    
    public static AccessibilityWindowInfo obtain(final AccessibilityWindowInfo info) {
        throw new RuntimeException("Stub!");
    }
    
    public void recycle() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int describeContents() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void writeToParcel(final Parcel parcel, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int hashCode() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean equals(final Object obj) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
}
