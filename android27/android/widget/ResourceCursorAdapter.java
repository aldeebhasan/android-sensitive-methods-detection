package android.widget;

import android.content.*;
import android.database.*;
import android.content.res.*;
import android.view.*;

public abstract class ResourceCursorAdapter extends CursorAdapter
{
    public ResourceCursorAdapter(final Context context, final int layout, final Cursor c) {
        super(null, null, 0);
        throw new RuntimeException("Stub!");
    }
    
    public ResourceCursorAdapter(final Context context, final int layout, final Cursor c, final boolean autoRequery) {
        super(null, null, 0);
        throw new RuntimeException("Stub!");
    }
    
    public ResourceCursorAdapter(final Context context, final int layout, final Cursor c, final int flags) {
        super(null, null, 0);
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void setDropDownViewTheme(final Resources.Theme theme) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public View newView(final Context context, final Cursor cursor, final ViewGroup parent) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public View newDropDownView(final Context context, final Cursor cursor, final ViewGroup parent) {
        throw new RuntimeException("Stub!");
    }
    
    public void setViewResource(final int layout) {
        throw new RuntimeException("Stub!");
    }
    
    public void setDropDownViewResource(final int dropDownLayout) {
        throw new RuntimeException("Stub!");
    }
}
