package android.accessibilityservice;

import android.content.pm.*;
import android.os.*;

public class AccessibilityServiceInfo implements Parcelable
{
    public static final int CAPABILITY_CAN_CONTROL_MAGNIFICATION = 16;
    public static final int CAPABILITY_CAN_PERFORM_GESTURES = 32;
    @Deprecated
    public static final int CAPABILITY_CAN_REQUEST_ENHANCED_WEB_ACCESSIBILITY = 4;
    public static final int CAPABILITY_CAN_REQUEST_FILTER_KEY_EVENTS = 8;
    public static final int CAPABILITY_CAN_REQUEST_FINGERPRINT_GESTURES = 64;
    public static final int CAPABILITY_CAN_REQUEST_TOUCH_EXPLORATION = 2;
    public static final int CAPABILITY_CAN_RETRIEVE_WINDOW_CONTENT = 1;
    public static final Creator<AccessibilityServiceInfo> CREATOR;
    public static final int DEFAULT = 1;
    public static final int FEEDBACK_ALL_MASK = -1;
    public static final int FEEDBACK_AUDIBLE = 4;
    public static final int FEEDBACK_BRAILLE = 32;
    public static final int FEEDBACK_GENERIC = 16;
    public static final int FEEDBACK_HAPTIC = 2;
    public static final int FEEDBACK_SPOKEN = 1;
    public static final int FEEDBACK_VISUAL = 8;
    public static final int FLAG_ENABLE_ACCESSIBILITY_VOLUME = 128;
    public static final int FLAG_INCLUDE_NOT_IMPORTANT_VIEWS = 2;
    public static final int FLAG_REPORT_VIEW_IDS = 16;
    public static final int FLAG_REQUEST_ACCESSIBILITY_BUTTON = 256;
    @Deprecated
    public static final int FLAG_REQUEST_ENHANCED_WEB_ACCESSIBILITY = 8;
    public static final int FLAG_REQUEST_FILTER_KEY_EVENTS = 32;
    public static final int FLAG_REQUEST_FINGERPRINT_GESTURES = 512;
    public static final int FLAG_REQUEST_TOUCH_EXPLORATION_MODE = 4;
    public static final int FLAG_RETRIEVE_INTERACTIVE_WINDOWS = 64;
    public int eventTypes;
    public int feedbackType;
    public int flags;
    public long notificationTimeout;
    public String[] packageNames;
    
    public AccessibilityServiceInfo() {
        this.packageNames = null;
        throw new RuntimeException("Stub!");
    }
    
    public String getId() {
        throw new RuntimeException("Stub!");
    }
    
    public ResolveInfo getResolveInfo() {
        throw new RuntimeException("Stub!");
    }
    
    public String getSettingsActivityName() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public boolean getCanRetrieveWindowContent() {
        throw new RuntimeException("Stub!");
    }
    
    public int getCapabilities() {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence loadSummary(final PackageManager packageManager) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public String getDescription() {
        throw new RuntimeException("Stub!");
    }
    
    public String loadDescription(final PackageManager packageManager) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int describeContents() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void writeToParcel(final Parcel parcel, final int flagz) {
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
    
    public static String feedbackTypeToString(final int feedbackType) {
        throw new RuntimeException("Stub!");
    }
    
    public static String flagToString(final int flag) {
        throw new RuntimeException("Stub!");
    }
    
    public static String capabilityToString(final int capability) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
}
