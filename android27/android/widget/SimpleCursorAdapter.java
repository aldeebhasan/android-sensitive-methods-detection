package android.widget;

import android.content.*;
import android.database.*;
import android.view.*;

public class SimpleCursorAdapter extends ResourceCursorAdapter
{
    public SimpleCursorAdapter(final Context context, final int layout, final Cursor c, final String[] from, final int[] to) {
        super(null, 0, null, 0);
        throw new RuntimeException("Stub!");
    }
    
    public SimpleCursorAdapter(final Context context, final int layout, final Cursor c, final String[] from, final int[] to, final int flags) {
        super(null, 0, null, 0);
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void bindView(final View view, final Context context, final Cursor cursor) {
        throw new RuntimeException("Stub!");
    }
    
    public ViewBinder getViewBinder() {
        throw new RuntimeException("Stub!");
    }
    
    public void setViewBinder(final ViewBinder viewBinder) {
        throw new RuntimeException("Stub!");
    }
    
    public void setViewImage(final ImageView v, final String value) {
        throw new RuntimeException("Stub!");
    }
    
    public void setViewText(final TextView v, final String text) {
        throw new RuntimeException("Stub!");
    }
    
    public int getStringConversionColumn() {
        throw new RuntimeException("Stub!");
    }
    
    public void setStringConversionColumn(final int stringConversionColumn) {
        throw new RuntimeException("Stub!");
    }
    
    public CursorToStringConverter getCursorToStringConverter() {
        throw new RuntimeException("Stub!");
    }
    
    public void setCursorToStringConverter(final CursorToStringConverter cursorToStringConverter) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public CharSequence convertToString(final Cursor cursor) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Cursor swapCursor(final Cursor c) {
        throw new RuntimeException("Stub!");
    }
    
    public void changeCursorAndColumns(final Cursor c, final String[] from, final int[] to) {
        throw new RuntimeException("Stub!");
    }
    
    public interface CursorToStringConverter
    {
        CharSequence convertToString(final Cursor p0);
    }
    
    public interface ViewBinder
    {
        boolean setViewValue(final View p0, final Cursor p1, final int p2);
    }
}
