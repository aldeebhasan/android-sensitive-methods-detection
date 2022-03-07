package android.app;

import android.content.*;
import android.net.*;
import android.os.*;

public final class AutomaticZenRule implements Parcelable
{
    public static final Creator<AutomaticZenRule> CREATOR;
    
    public AutomaticZenRule(final String name, final ComponentName owner, final Uri conditionId, final int interruptionFilter, final boolean enabled) {
        throw new RuntimeException("Stub!");
    }
    
    public AutomaticZenRule(final Parcel source) {
        throw new RuntimeException("Stub!");
    }
    
    public ComponentName getOwner() {
        throw new RuntimeException("Stub!");
    }
    
    public Uri getConditionId() {
        throw new RuntimeException("Stub!");
    }
    
    public int getInterruptionFilter() {
        throw new RuntimeException("Stub!");
    }
    
    public String getName() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isEnabled() {
        throw new RuntimeException("Stub!");
    }
    
    public long getCreationTime() {
        throw new RuntimeException("Stub!");
    }
    
    public void setConditionId(final Uri conditionId) {
        throw new RuntimeException("Stub!");
    }
    
    public void setInterruptionFilter(final int interruptionFilter) {
        throw new RuntimeException("Stub!");
    }
    
    public void setName(final String name) {
        throw new RuntimeException("Stub!");
    }
    
    public void setEnabled(final boolean enabled) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int describeContents() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean equals(final Object o) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int hashCode() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
}
