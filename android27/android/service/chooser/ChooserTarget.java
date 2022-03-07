package android.service.chooser;

import android.graphics.drawable.*;
import android.content.*;
import android.os.*;

public final class ChooserTarget implements Parcelable
{
    public static final Creator<ChooserTarget> CREATOR;
    
    public ChooserTarget(final CharSequence title, final Icon icon, final float score, final ComponentName componentName, final Bundle intentExtras) {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence getTitle() {
        throw new RuntimeException("Stub!");
    }
    
    public Icon getIcon() {
        throw new RuntimeException("Stub!");
    }
    
    public float getScore() {
        throw new RuntimeException("Stub!");
    }
    
    public ComponentName getComponentName() {
        throw new RuntimeException("Stub!");
    }
    
    public Bundle getIntentExtras() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
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
