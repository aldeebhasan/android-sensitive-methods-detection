package android.app;

import android.graphics.drawable.*;
import android.os.*;

public static class Action implements Parcelable
{
    public static final Creator<Action> CREATOR;
    public PendingIntent actionIntent;
    @Deprecated
    public int icon;
    public CharSequence title;
    
    public Action(final int icon, final CharSequence title, final PendingIntent intent) {
        throw new RuntimeException("Stub!");
    }
    
    public Icon getIcon() {
        throw new RuntimeException("Stub!");
    }
    
    public Bundle getExtras() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean getAllowGeneratedReplies() {
        throw new RuntimeException("Stub!");
    }
    
    public RemoteInput[] getRemoteInputs() {
        throw new RuntimeException("Stub!");
    }
    
    public RemoteInput[] getDataOnlyRemoteInputs() {
        throw new RuntimeException("Stub!");
    }
    
    public Action clone() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int describeContents() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void writeToParcel(final Parcel out, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
    
    public static final class Builder
    {
        public Builder(final int icon, final CharSequence title, final PendingIntent intent) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder(final Icon icon, final CharSequence title, final PendingIntent intent) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder(final Action action) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder addExtras(final Bundle extras) {
            throw new RuntimeException("Stub!");
        }
        
        public Bundle getExtras() {
            throw new RuntimeException("Stub!");
        }
        
        public Builder addRemoteInput(final RemoteInput remoteInput) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setAllowGeneratedReplies(final boolean allowGeneratedReplies) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder extend(final Extender extender) {
            throw new RuntimeException("Stub!");
        }
        
        public Action build() {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static final class WearableExtender implements Extender
    {
        public WearableExtender() {
            throw new RuntimeException("Stub!");
        }
        
        public WearableExtender(final Action action) {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public Builder extend(final Builder builder) {
            throw new RuntimeException("Stub!");
        }
        
        public WearableExtender clone() {
            throw new RuntimeException("Stub!");
        }
        
        public WearableExtender setAvailableOffline(final boolean availableOffline) {
            throw new RuntimeException("Stub!");
        }
        
        public boolean isAvailableOffline() {
            throw new RuntimeException("Stub!");
        }
        
        public WearableExtender setInProgressLabel(final CharSequence label) {
            throw new RuntimeException("Stub!");
        }
        
        public CharSequence getInProgressLabel() {
            throw new RuntimeException("Stub!");
        }
        
        public WearableExtender setConfirmLabel(final CharSequence label) {
            throw new RuntimeException("Stub!");
        }
        
        public CharSequence getConfirmLabel() {
            throw new RuntimeException("Stub!");
        }
        
        public WearableExtender setCancelLabel(final CharSequence label) {
            throw new RuntimeException("Stub!");
        }
        
        public CharSequence getCancelLabel() {
            throw new RuntimeException("Stub!");
        }
        
        public WearableExtender setHintLaunchesActivity(final boolean hintLaunchesActivity) {
            throw new RuntimeException("Stub!");
        }
        
        public boolean getHintLaunchesActivity() {
            throw new RuntimeException("Stub!");
        }
        
        public WearableExtender setHintDisplayActionInline(final boolean hintDisplayInline) {
            throw new RuntimeException("Stub!");
        }
        
        public boolean getHintDisplayActionInline() {
            throw new RuntimeException("Stub!");
        }
    }
    
    public interface Extender
    {
        Builder extend(final Builder p0);
    }
}
