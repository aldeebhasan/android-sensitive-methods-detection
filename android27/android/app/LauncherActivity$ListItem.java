package android.app;

import android.os.*;
import android.graphics.drawable.*;
import android.content.pm.*;

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
