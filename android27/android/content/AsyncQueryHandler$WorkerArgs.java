package android.content;

import android.os.*;
import android.net.*;

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
