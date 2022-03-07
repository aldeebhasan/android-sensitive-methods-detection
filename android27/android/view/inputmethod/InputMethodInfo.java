package android.view.inputmethod;

import org.xmlpull.v1.*;
import java.io.*;
import android.content.*;
import android.content.pm.*;
import android.graphics.drawable.*;
import android.util.*;
import android.os.*;

public final class InputMethodInfo implements Parcelable
{
    public static final Creator<InputMethodInfo> CREATOR;
    
    public InputMethodInfo(final Context context, final ResolveInfo service) throws XmlPullParserException, IOException {
        throw new RuntimeException("Stub!");
    }
    
    public InputMethodInfo(final String packageName, final String className, final CharSequence label, final String settingsActivity) {
        throw new RuntimeException("Stub!");
    }
    
    public String getId() {
        throw new RuntimeException("Stub!");
    }
    
    public String getPackageName() {
        throw new RuntimeException("Stub!");
    }
    
    public String getServiceName() {
        throw new RuntimeException("Stub!");
    }
    
    public ServiceInfo getServiceInfo() {
        throw new RuntimeException("Stub!");
    }
    
    public ComponentName getComponent() {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence loadLabel(final PackageManager pm) {
        throw new RuntimeException("Stub!");
    }
    
    public Drawable loadIcon(final PackageManager pm) {
        throw new RuntimeException("Stub!");
    }
    
    public String getSettingsActivity() {
        throw new RuntimeException("Stub!");
    }
    
    public int getSubtypeCount() {
        throw new RuntimeException("Stub!");
    }
    
    public InputMethodSubtype getSubtypeAt(final int index) {
        throw new RuntimeException("Stub!");
    }
    
    public int getIsDefaultResourceId() {
        throw new RuntimeException("Stub!");
    }
    
    public void dump(final Printer pw, final String prefix) {
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
    
    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
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
