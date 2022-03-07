package android.app;

import org.xmlpull.v1.*;
import java.io.*;
import android.content.*;
import android.content.pm.*;
import android.graphics.drawable.*;
import android.content.res.*;
import android.net.*;
import android.util.*;
import android.os.*;

public final class WallpaperInfo implements Parcelable
{
    public static final Creator<WallpaperInfo> CREATOR;
    
    public WallpaperInfo(final Context context, final ResolveInfo service) throws XmlPullParserException, IOException {
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
    
    public Drawable loadThumbnail(final PackageManager pm) {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence loadAuthor(final PackageManager pm) throws Resources.NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence loadDescription(final PackageManager pm) throws Resources.NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public Uri loadContextUri(final PackageManager pm) throws Resources.NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence loadContextDescription(final PackageManager pm) throws Resources.NotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public boolean getShowMetadataInPreview() {
        throw new RuntimeException("Stub!");
    }
    
    public String getSettingsActivity() {
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
