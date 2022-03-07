package android.content.pm;

import android.os.*;
import android.graphics.drawable.*;
import android.content.res.*;
import android.util.*;
import java.util.*;

public class PackageItemInfo
{
    public int banner;
    public int icon;
    public int labelRes;
    public int logo;
    public Bundle metaData;
    public String name;
    public CharSequence nonLocalizedLabel;
    public String packageName;
    
    public PackageItemInfo() {
        throw new RuntimeException("Stub!");
    }
    
    public PackageItemInfo(final PackageItemInfo orig) {
        throw new RuntimeException("Stub!");
    }
    
    protected PackageItemInfo(final Parcel source) {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence loadLabel(final PackageManager pm) {
        throw new RuntimeException("Stub!");
    }
    
    public Drawable loadIcon(final PackageManager pm) {
        throw new RuntimeException("Stub!");
    }
    
    public Drawable loadUnbadgedIcon(final PackageManager pm) {
        throw new RuntimeException("Stub!");
    }
    
    public Drawable loadBanner(final PackageManager pm) {
        throw new RuntimeException("Stub!");
    }
    
    public Drawable loadLogo(final PackageManager pm) {
        throw new RuntimeException("Stub!");
    }
    
    public XmlResourceParser loadXmlMetaData(final PackageManager pm, final String name) {
        throw new RuntimeException("Stub!");
    }
    
    protected void dumpFront(final Printer pw, final String prefix) {
        throw new RuntimeException("Stub!");
    }
    
    protected void dumpBack(final Printer pw, final String prefix) {
        throw new RuntimeException("Stub!");
    }
    
    public void writeToParcel(final Parcel dest, final int parcelableFlags) {
        throw new RuntimeException("Stub!");
    }
    
    public static class DisplayNameComparator implements Comparator<PackageItemInfo>
    {
        public DisplayNameComparator(final PackageManager pm) {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public final int compare(final PackageItemInfo aa, final PackageItemInfo ab) {
            throw new RuntimeException("Stub!");
        }
    }
}
