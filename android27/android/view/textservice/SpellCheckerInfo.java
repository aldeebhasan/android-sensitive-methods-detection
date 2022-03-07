package android.view.textservice;

import android.content.*;
import android.os.*;
import android.graphics.drawable.*;
import android.content.pm.*;

public final class SpellCheckerInfo implements Parcelable
{
    public static final Creator<SpellCheckerInfo> CREATOR;
    
    SpellCheckerInfo() {
        throw new RuntimeException("Stub!");
    }
    
    public String getId() {
        throw new RuntimeException("Stub!");
    }
    
    public ComponentName getComponent() {
        throw new RuntimeException("Stub!");
    }
    
    public String getPackageName() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence loadLabel(final PackageManager pm) {
        throw new RuntimeException("Stub!");
    }
    
    public Drawable loadIcon(final PackageManager pm) {
        throw new RuntimeException("Stub!");
    }
    
    public ServiceInfo getServiceInfo() {
        throw new RuntimeException("Stub!");
    }
    
    public String getSettingsActivity() {
        throw new RuntimeException("Stub!");
    }
    
    public int getSubtypeCount() {
        throw new RuntimeException("Stub!");
    }
    
    public SpellCheckerSubtype getSubtypeAt(final int index) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int describeContents() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
}
