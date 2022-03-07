package android.view.accessibility;

import android.os.*;

public final class AccessibilityEvent extends AccessibilityRecord implements Parcelable
{
    public static final int CONTENT_CHANGE_TYPE_CONTENT_DESCRIPTION = 4;
    public static final int CONTENT_CHANGE_TYPE_SUBTREE = 1;
    public static final int CONTENT_CHANGE_TYPE_TEXT = 2;
    public static final int CONTENT_CHANGE_TYPE_UNDEFINED = 0;
    public static final Creator<AccessibilityEvent> CREATOR;
    public static final int INVALID_POSITION = -1;
    @Deprecated
    public static final int MAX_TEXT_LENGTH = 500;
    public static final int TYPES_ALL_MASK = -1;
    public static final int TYPE_ANNOUNCEMENT = 16384;
    public static final int TYPE_ASSIST_READING_CONTEXT = 16777216;
    public static final int TYPE_GESTURE_DETECTION_END = 524288;
    public static final int TYPE_GESTURE_DETECTION_START = 262144;
    public static final int TYPE_NOTIFICATION_STATE_CHANGED = 64;
    public static final int TYPE_TOUCH_EXPLORATION_GESTURE_END = 1024;
    public static final int TYPE_TOUCH_EXPLORATION_GESTURE_START = 512;
    public static final int TYPE_TOUCH_INTERACTION_END = 2097152;
    public static final int TYPE_TOUCH_INTERACTION_START = 1048576;
    public static final int TYPE_VIEW_ACCESSIBILITY_FOCUSED = 32768;
    public static final int TYPE_VIEW_ACCESSIBILITY_FOCUS_CLEARED = 65536;
    public static final int TYPE_VIEW_CLICKED = 1;
    public static final int TYPE_VIEW_CONTEXT_CLICKED = 8388608;
    public static final int TYPE_VIEW_FOCUSED = 8;
    public static final int TYPE_VIEW_HOVER_ENTER = 128;
    public static final int TYPE_VIEW_HOVER_EXIT = 256;
    public static final int TYPE_VIEW_LONG_CLICKED = 2;
    public static final int TYPE_VIEW_SCROLLED = 4096;
    public static final int TYPE_VIEW_SELECTED = 4;
    public static final int TYPE_VIEW_TEXT_CHANGED = 16;
    public static final int TYPE_VIEW_TEXT_SELECTION_CHANGED = 8192;
    public static final int TYPE_VIEW_TEXT_TRAVERSED_AT_MOVEMENT_GRANULARITY = 131072;
    public static final int TYPE_WINDOWS_CHANGED = 4194304;
    public static final int TYPE_WINDOW_CONTENT_CHANGED = 2048;
    public static final int TYPE_WINDOW_STATE_CHANGED = 32;
    
    AccessibilityEvent() {
        throw new RuntimeException("Stub!");
    }
    
    public int getRecordCount() {
        throw new RuntimeException("Stub!");
    }
    
    public void appendRecord(final AccessibilityRecord record) {
        throw new RuntimeException("Stub!");
    }
    
    public AccessibilityRecord getRecord(final int index) {
        throw new RuntimeException("Stub!");
    }
    
    public int getEventType() {
        throw new RuntimeException("Stub!");
    }
    
    public int getContentChangeTypes() {
        throw new RuntimeException("Stub!");
    }
    
    public void setContentChangeTypes(final int changeTypes) {
        throw new RuntimeException("Stub!");
    }
    
    public void setEventType(final int eventType) {
        throw new RuntimeException("Stub!");
    }
    
    public long getEventTime() {
        throw new RuntimeException("Stub!");
    }
    
    public void setEventTime(final long eventTime) {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence getPackageName() {
        throw new RuntimeException("Stub!");
    }
    
    public void setPackageName(final CharSequence packageName) {
        throw new RuntimeException("Stub!");
    }
    
    public void setMovementGranularity(final int granularity) {
        throw new RuntimeException("Stub!");
    }
    
    public int getMovementGranularity() {
        throw new RuntimeException("Stub!");
    }
    
    public void setAction(final int action) {
        throw new RuntimeException("Stub!");
    }
    
    public int getAction() {
        throw new RuntimeException("Stub!");
    }
    
    public static AccessibilityEvent obtain(final int eventType) {
        throw new RuntimeException("Stub!");
    }
    
    public static AccessibilityEvent obtain(final AccessibilityEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    public static AccessibilityEvent obtain() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void recycle() {
        throw new RuntimeException("Stub!");
    }
    
    public void initFromParcel(final Parcel parcel) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void writeToParcel(final Parcel parcel, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int describeContents() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
    
    public static String eventTypeToString(final int eventType) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
}
