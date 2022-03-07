package android.app;

import android.os.*;
import android.widget.*;
import android.view.*;
import android.content.*;
import java.util.*;
import android.content.pm.*;
import android.graphics.drawable.*;

public abstract class LauncherActivity extends ListActivity
{
    public LauncherActivity() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onCreate(final Bundle icicle) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void setTitle(final CharSequence title) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void setTitle(final int titleId) {
        throw new RuntimeException("Stub!");
    }
    
    protected void onSetContentView() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onListItemClick(final ListView l, final View v, final int position, final long id) {
        throw new RuntimeException("Stub!");
    }
    
    protected Intent intentForPosition(final int position) {
        throw new RuntimeException("Stub!");
    }
    
    protected ListItem itemForPosition(final int position) {
        throw new RuntimeException("Stub!");
    }
    
    protected Intent getTargetIntent() {
        throw new RuntimeException("Stub!");
    }
    
    protected List<ResolveInfo> onQueryPackageManager(final Intent queryIntent) {
        throw new RuntimeException("Stub!");
    }
    
    public List<ListItem> makeListItems() {
        throw new RuntimeException("Stub!");
    }
    
    public static class ListItem
    {
        public String className;
        public Bundle extras;
        public Drawable icon;
        public CharSequence label;
        public String packageName;
        public ResolveInfo resolveInfo;
        
        public ListItem() {
            throw new RuntimeException("Stub!");
        }
    }
    
    public class IconResizer
    {
        public IconResizer() {
            throw new RuntimeException("Stub!");
        }
        
        public Drawable createIconThumbnail(final Drawable icon) {
            throw new RuntimeException("Stub!");
        }
    }
}
