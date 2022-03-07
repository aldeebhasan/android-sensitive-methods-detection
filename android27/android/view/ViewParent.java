package android.view;

import android.graphics.*;
import android.view.accessibility.*;
import android.os.*;

public interface ViewParent
{
    void requestLayout();
    
    boolean isLayoutRequested();
    
    void requestTransparentRegion(final View p0);
    
    default void onDescendantInvalidated(final View child, final View target) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    void invalidateChild(final View p0, final Rect p1);
    
    @Deprecated
    ViewParent invalidateChildInParent(final int[] p0, final Rect p1);
    
    ViewParent getParent();
    
    void requestChildFocus(final View p0, final View p1);
    
    void recomputeViewAttributes(final View p0);
    
    void clearChildFocus(final View p0);
    
    boolean getChildVisibleRect(final View p0, final Rect p1, final Point p2);
    
    View focusSearch(final View p0, final int p1);
    
    View keyboardNavigationClusterSearch(final View p0, final int p1);
    
    void bringChildToFront(final View p0);
    
    void focusableViewAvailable(final View p0);
    
    boolean showContextMenuForChild(final View p0);
    
    boolean showContextMenuForChild(final View p0, final float p1, final float p2);
    
    void createContextMenu(final ContextMenu p0);
    
    ActionMode startActionModeForChild(final View p0, final ActionMode.Callback p1);
    
    ActionMode startActionModeForChild(final View p0, final ActionMode.Callback p1, final int p2);
    
    void childDrawableStateChanged(final View p0);
    
    void requestDisallowInterceptTouchEvent(final boolean p0);
    
    boolean requestChildRectangleOnScreen(final View p0, final Rect p1, final boolean p2);
    
    boolean requestSendAccessibilityEvent(final View p0, final AccessibilityEvent p1);
    
    void childHasTransientStateChanged(final View p0, final boolean p1);
    
    void requestFitSystemWindows();
    
    ViewParent getParentForAccessibility();
    
    void notifySubtreeAccessibilityStateChanged(final View p0, final View p1, final int p2);
    
    boolean canResolveLayoutDirection();
    
    boolean isLayoutDirectionResolved();
    
    int getLayoutDirection();
    
    boolean canResolveTextDirection();
    
    boolean isTextDirectionResolved();
    
    int getTextDirection();
    
    boolean canResolveTextAlignment();
    
    boolean isTextAlignmentResolved();
    
    int getTextAlignment();
    
    boolean onStartNestedScroll(final View p0, final View p1, final int p2);
    
    void onNestedScrollAccepted(final View p0, final View p1, final int p2);
    
    void onStopNestedScroll(final View p0);
    
    void onNestedScroll(final View p0, final int p1, final int p2, final int p3, final int p4);
    
    void onNestedPreScroll(final View p0, final int p1, final int p2, final int[] p3);
    
    boolean onNestedFling(final View p0, final float p1, final float p2, final boolean p3);
    
    boolean onNestedPreFling(final View p0, final float p1, final float p2);
    
    boolean onNestedPrePerformAccessibilityAction(final View p0, final int p1, final Bundle p2);
}
