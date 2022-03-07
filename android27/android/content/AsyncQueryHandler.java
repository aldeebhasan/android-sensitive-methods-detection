package android.content;

import android.net.*;
import android.database.*;
import android.os.*;

public abstract class AsyncQueryHandler extends Handler
{
    public AsyncQueryHandler(final ContentResolver cr) {
        throw new RuntimeException("Stub!");
    }
    
    protected Handler createHandler(final Looper looper) {
        throw new RuntimeException("Stub!");
    }
    
    public void startQuery(final int token, final Object cookie, final Uri uri, final String[] projection, final String selection, final String[] selectionArgs, final String orderBy) {
        throw new RuntimeException("Stub!");
    }
    
    public final void cancelOperation(final int token) {
        throw new RuntimeException("Stub!");
    }
    
    public final void startInsert(final int token, final Object cookie, final Uri uri, final ContentValues initialValues) {
        throw new RuntimeException("Stub!");
    }
    
    public final void startUpdate(final int token, final Object cookie, final Uri uri, final ContentValues values, final String selection, final String[] selectionArgs) {
        throw new RuntimeException("Stub!");
    }
    
    public final void startDelete(final int token, final Object cookie, final Uri uri, final String selection, final String[] selectionArgs) {
        throw new RuntimeException("Stub!");
    }
    
    protected void onQueryComplete(final int token, final Object cookie, final Cursor cursor) {
        throw new RuntimeException("Stub!");
    }
    
    protected void onInsertComplete(final int token, final Object cookie, final Uri uri) {
        throw new RuntimeException("Stub!");
    }
    
    protected void onUpdateComplete(final int token, final Object cookie, final int result) {
        throw new RuntimeException("Stub!");
    }
    
    protected void onDeleteComplete(final int token, final Object cookie, final int result) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void handleMessage(final Message msg) {
        throw new RuntimeException("Stub!");
    }
    
    protected static final class WorkerArgs
    {
        public Object cookie;
        public Handler handler;
        public String orderBy;
        public String[] projection;
        public Object result;
        public String selection;
        public String[] selectionArgs;
        public Uri uri;
        public ContentValues values;
        
        protected WorkerArgs() {
            this.projection = null;
            this.selectionArgs = null;
            throw new RuntimeException("Stub!");
        }
    }
    
    protected class WorkerHandler extends Handler
    {
        public WorkerHandler(final Looper looper) {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public void handleMessage(final Message msg) {
            throw new RuntimeException("Stub!");
        }
    }
}
