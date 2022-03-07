package android.view;

import java.util.*;
import android.os.*;

public final class KeyboardShortcutGroup implements Parcelable
{
    public static final Creator<KeyboardShortcutGroup> CREATOR;
    
    public KeyboardShortcutGroup(final CharSequence label, final List<KeyboardShortcutInfo> items) {
        throw new RuntimeException("Stub!");
    }
    
    public KeyboardShortcutGroup(final CharSequence label) {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence getLabel() {
        throw new RuntimeException("Stub!");
    }
    
    public List<KeyboardShortcutInfo> getItems() {
        throw new RuntimeException("Stub!");
    }
    
    public void addItem(final KeyboardShortcutInfo item) {
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
    
    static {
        CREATOR = null;
    }
}
