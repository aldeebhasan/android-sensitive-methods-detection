package android.content.pm;

import java.util.*;
import android.os.*;
import android.content.*;
import android.graphics.drawable.*;

public final class ShortcutInfo implements Parcelable
{
    public static final Creator<ShortcutInfo> CREATOR;
    public static final String SHORTCUT_CATEGORY_CONVERSATION = "android.shortcut.conversation";
    
    ShortcutInfo() {
        throw new RuntimeException("Stub!");
    }
    
    public String getId() {
        throw new RuntimeException("Stub!");
    }
    
    public String getPackage() {
        throw new RuntimeException("Stub!");
    }
    
    public ComponentName getActivity() {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence getShortLabel() {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence getLongLabel() {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence getDisabledMessage() {
        throw new RuntimeException("Stub!");
    }
    
    public Set<String> getCategories() {
        throw new RuntimeException("Stub!");
    }
    
    public Intent getIntent() {
        throw new RuntimeException("Stub!");
    }
    
    public Intent[] getIntents() {
        throw new RuntimeException("Stub!");
    }
    
    public int getRank() {
        throw new RuntimeException("Stub!");
    }
    
    public PersistableBundle getExtras() {
        throw new RuntimeException("Stub!");
    }
    
    public UserHandle getUserHandle() {
        throw new RuntimeException("Stub!");
    }
    
    public long getLastChangedTimestamp() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isDynamic() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isPinned() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isDeclaredInManifest() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isImmutable() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isEnabled() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean hasKeyFieldsOnly() {
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
    public String toString() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
    
    public static class Builder
    {
        public Builder(final Context context, final String id) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setActivity(final ComponentName activity) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setIcon(final Icon icon) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setShortLabel(final CharSequence shortLabel) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setLongLabel(final CharSequence longLabel) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setDisabledMessage(final CharSequence disabledMessage) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setCategories(final Set<String> categories) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setIntent(final Intent intent) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setIntents(final Intent[] intents) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setRank(final int rank) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setExtras(final PersistableBundle extras) {
            throw new RuntimeException("Stub!");
        }
        
        public ShortcutInfo build() {
            throw new RuntimeException("Stub!");
        }
    }
}
