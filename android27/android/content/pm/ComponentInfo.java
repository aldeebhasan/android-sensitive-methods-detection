package android.content.pm;

import android.os.*;
import android.util.*;

public class ComponentInfo extends PackageItemInfo
{
    public ApplicationInfo applicationInfo;
    public int descriptionRes;
    public boolean directBootAware;
    public boolean enabled;
    public boolean exported;
    public String processName;
    public String splitName;
    
    public ComponentInfo() {
        throw new RuntimeException("Stub!");
    }
    
    public ComponentInfo(final ComponentInfo orig) {
        throw new RuntimeException("Stub!");
    }
    
    protected ComponentInfo(final Parcel source) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public CharSequence loadLabel(final PackageManager pm) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isEnabled() {
        throw new RuntimeException("Stub!");
    }
    
    public final int getIconResource() {
        throw new RuntimeException("Stub!");
    }
    
    public final int getLogoResource() {
        throw new RuntimeException("Stub!");
    }
    
    public final int getBannerResource() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void dumpFront(final Printer pw, final String prefix) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void dumpBack(final Printer pw, final String prefix) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void writeToParcel(final Parcel dest, final int parcelableFlags) {
        throw new RuntimeException("Stub!");
    }
}
