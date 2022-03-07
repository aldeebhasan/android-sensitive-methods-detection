package android.content.pm;

import android.os.*;
import java.util.*;

public abstract static class Callback
{
    public Callback() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract void onPackageRemoved(final String p0, final UserHandle p1);
    
    public abstract void onPackageAdded(final String p0, final UserHandle p1);
    
    public abstract void onPackageChanged(final String p0, final UserHandle p1);
    
    public abstract void onPackagesAvailable(final String[] p0, final UserHandle p1, final boolean p2);
    
    public abstract void onPackagesUnavailable(final String[] p0, final UserHandle p1, final boolean p2);
    
    public void onPackagesSuspended(final String[] packageNames, final UserHandle user) {
        throw new RuntimeException("Stub!");
    }
    
    public void onPackagesUnsuspended(final String[] packageNames, final UserHandle user) {
        throw new RuntimeException("Stub!");
    }
    
    public void onShortcutsChanged(final String packageName, final List<ShortcutInfo> shortcuts, final UserHandle user) {
        throw new RuntimeException("Stub!");
    }
}
