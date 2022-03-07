package android.content;

import android.database.*;
import android.net.*;
import java.io.*;

public class CursorLoader extends AsyncTaskLoader<Cursor>
{
    public CursorLoader(final Context context) {
        super(null);
        throw new RuntimeException("Stub!");
    }
    
    public CursorLoader(final Context context, final Uri uri, final String[] projection, final String selection, final String[] selectionArgs, final String sortOrder) {
        super(null);
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Cursor loadInBackground() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void cancelLoadInBackground() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void deliverResult(final Cursor cursor) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onStartLoading() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onStopLoading() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onCanceled(final Cursor cursor) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onReset() {
        throw new RuntimeException("Stub!");
    }
    
    public Uri getUri() {
        throw new RuntimeException("Stub!");
    }
    
    public void setUri(final Uri uri) {
        throw new RuntimeException("Stub!");
    }
    
    public String[] getProjection() {
        throw new RuntimeException("Stub!");
    }
    
    public void setProjection(final String[] projection) {
        throw new RuntimeException("Stub!");
    }
    
    public String getSelection() {
        throw new RuntimeException("Stub!");
    }
    
    public void setSelection(final String selection) {
        throw new RuntimeException("Stub!");
    }
    
    public String[] getSelectionArgs() {
        throw new RuntimeException("Stub!");
    }
    
    public void setSelectionArgs(final String[] selectionArgs) {
        throw new RuntimeException("Stub!");
    }
    
    public String getSortOrder() {
        throw new RuntimeException("Stub!");
    }
    
    public void setSortOrder(final String sortOrder) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void dump(final String prefix, final FileDescriptor fd, final PrintWriter writer, final String[] args) {
        throw new RuntimeException("Stub!");
    }
}
