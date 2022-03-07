package android.app;

import android.graphics.drawable.*;
import android.os.*;
import java.io.*;

public final class RemoteAction implements Parcelable
{
    public static final Creator<RemoteAction> CREATOR;
    
    public RemoteAction(final Icon icon, final CharSequence title, final CharSequence contentDescription, final PendingIntent intent) {
        throw new RuntimeException("Stub!");
    }
    
    public void setEnabled(final boolean enabled) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isEnabled() {
        throw new RuntimeException("Stub!");
    }
    
    public Icon getIcon() {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence getTitle() {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence getContentDescription() {
        throw new RuntimeException("Stub!");
    }
    
    public PendingIntent getActionIntent() {
        throw new RuntimeException("Stub!");
    }
    
    public RemoteAction clone() {
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
    
    public void dump(final String prefix, final PrintWriter pw) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
}
