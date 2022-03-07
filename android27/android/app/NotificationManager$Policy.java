package android.app;

import android.os.*;

public static class Policy implements Parcelable
{
    public static final Creator<Policy> CREATOR;
    public static final int PRIORITY_CATEGORY_CALLS = 8;
    public static final int PRIORITY_CATEGORY_EVENTS = 2;
    public static final int PRIORITY_CATEGORY_MESSAGES = 4;
    public static final int PRIORITY_CATEGORY_REMINDERS = 1;
    public static final int PRIORITY_CATEGORY_REPEAT_CALLERS = 16;
    public static final int PRIORITY_SENDERS_ANY = 0;
    public static final int PRIORITY_SENDERS_CONTACTS = 1;
    public static final int PRIORITY_SENDERS_STARRED = 2;
    public static final int SUPPRESSED_EFFECT_SCREEN_OFF = 1;
    public static final int SUPPRESSED_EFFECT_SCREEN_ON = 2;
    public final int priorityCallSenders;
    public final int priorityCategories;
    public final int priorityMessageSenders;
    public final int suppressedVisualEffects;
    
    public Policy(final int priorityCategories, final int priorityCallSenders, final int priorityMessageSenders) {
        throw new RuntimeException("Stub!");
    }
    
    public Policy(final int priorityCategories, final int priorityCallSenders, final int priorityMessageSenders, final int suppressedVisualEffects) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int describeContents() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int hashCode() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean equals(final Object o) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
    
    public static String suppressedEffectsToString(final int effects) {
        throw new RuntimeException("Stub!");
    }
    
    public static String priorityCategoriesToString(final int priorityCategories) {
        throw new RuntimeException("Stub!");
    }
    
    public static String prioritySendersToString(final int prioritySenders) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
}
