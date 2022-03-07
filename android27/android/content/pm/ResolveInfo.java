package android.content.pm;

import android.content.*;
import android.graphics.drawable.*;
import android.util.*;
import android.os.*;
import java.util.*;

public class ResolveInfo implements Parcelable
{
    public static final Creator<ResolveInfo> CREATOR;
    public ActivityInfo activityInfo;
    public IntentFilter filter;
    public int icon;
    public boolean isDefault;
    public boolean isInstantAppAvailable;
    public int labelRes;
    public int match;
    public CharSequence nonLocalizedLabel;
    public int preferredOrder;
    public int priority;
    public ProviderInfo providerInfo;
    public String resolvePackageName;
    public ServiceInfo serviceInfo;
    public int specificIndex;
    
    public ResolveInfo() {
        throw new RuntimeException("Stub!");
    }
    
    public ResolveInfo(final ResolveInfo orig) {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence loadLabel(final PackageManager pm) {
        throw new RuntimeException("Stub!");
    }
    
    public Drawable loadIcon(final PackageManager pm) {
        throw new RuntimeException("Stub!");
    }
    
    public final int getIconResource() {
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
    public int describeContents() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void writeToParcel(final Parcel dest, final int parcelableFlags) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
    
    public static class DisplayNameComparator implements Comparator<ResolveInfo>
    {
        public DisplayNameComparator(final PackageManager pm) {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public final int compare(final ResolveInfo a, final ResolveInfo b) {
            throw new RuntimeException("Stub!");
        }
    }
}
