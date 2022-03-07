package android.widget;

import android.util.*;
import android.app.*;
import android.view.*;
import android.graphics.drawable.*;
import android.content.*;

public class TabHost extends FrameLayout implements ViewTreeObserver.OnTouchModeChangeListener
{
    public TabHost(final Context context) {
        super(null, null, 0, 0);
        throw new RuntimeException("Stub!");
    }
    
    public TabHost(final Context context, final AttributeSet attrs) {
        super(null, null, 0, 0);
        throw new RuntimeException("Stub!");
    }
    
    public TabHost(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(null, null, 0, 0);
        throw new RuntimeException("Stub!");
    }
    
    public TabHost(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
        super(null, null, 0, 0);
        throw new RuntimeException("Stub!");
    }
    
    public TabSpec newTabSpec(final String tag) {
        throw new RuntimeException("Stub!");
    }
    
    public void setup() {
        throw new RuntimeException("Stub!");
    }
    
    public void setup(final LocalActivityManager activityGroup) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onTouchModeChanged(final boolean isInTouchMode) {
        throw new RuntimeException("Stub!");
    }
    
    public void addTab(final TabSpec tabSpec) {
        throw new RuntimeException("Stub!");
    }
    
    public void clearAllTabs() {
        throw new RuntimeException("Stub!");
    }
    
    public TabWidget getTabWidget() {
        throw new RuntimeException("Stub!");
    }
    
    public int getCurrentTab() {
        throw new RuntimeException("Stub!");
    }
    
    public String getCurrentTabTag() {
        throw new RuntimeException("Stub!");
    }
    
    public View getCurrentTabView() {
        throw new RuntimeException("Stub!");
    }
    
    public View getCurrentView() {
        throw new RuntimeException("Stub!");
    }
    
    public void setCurrentTabByTag(final String tag) {
        throw new RuntimeException("Stub!");
    }
    
    public FrameLayout getTabContentView() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean dispatchKeyEvent(final KeyEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void dispatchWindowFocusChanged(final boolean hasFocus) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public CharSequence getAccessibilityClassName() {
        throw new RuntimeException("Stub!");
    }
    
    public void setCurrentTab(final int index) {
        throw new RuntimeException("Stub!");
    }
    
    public void setOnTabChangedListener(final OnTabChangeListener l) {
        throw new RuntimeException("Stub!");
    }
    
    public class TabSpec
    {
        TabSpec() {
            throw new RuntimeException("Stub!");
        }
        
        public TabSpec setIndicator(final CharSequence label) {
            throw new RuntimeException("Stub!");
        }
        
        public TabSpec setIndicator(final CharSequence label, final Drawable icon) {
            throw new RuntimeException("Stub!");
        }
        
        public TabSpec setIndicator(final View view) {
            throw new RuntimeException("Stub!");
        }
        
        public TabSpec setContent(final int viewId) {
            throw new RuntimeException("Stub!");
        }
        
        public TabSpec setContent(final TabContentFactory contentFactory) {
            throw new RuntimeException("Stub!");
        }
        
        public TabSpec setContent(final Intent intent) {
            throw new RuntimeException("Stub!");
        }
        
        public String getTag() {
            throw new RuntimeException("Stub!");
        }
    }
    
    public interface TabContentFactory
    {
        View createTabContent(final String p0);
    }
    
    public interface OnTabChangeListener
    {
        void onTabChanged(final String p0);
    }
}
