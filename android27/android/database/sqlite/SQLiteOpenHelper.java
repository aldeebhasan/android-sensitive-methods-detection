package android.database.sqlite;

import android.content.*;
import android.database.*;

public abstract class SQLiteOpenHelper
{
    public SQLiteOpenHelper(final Context context, final String name, final SQLiteDatabase.CursorFactory factory, final int version) {
        throw new RuntimeException("Stub!");
    }
    
    public SQLiteOpenHelper(final Context context, final String name, final SQLiteDatabase.CursorFactory factory, final int version, final DatabaseErrorHandler errorHandler) {
        throw new RuntimeException("Stub!");
    }
    
    public String getDatabaseName() {
        throw new RuntimeException("Stub!");
    }
    
    public void setWriteAheadLoggingEnabled(final boolean enabled) {
        throw new RuntimeException("Stub!");
    }
    
    public void setLookasideConfig(final int slotSize, final int slotCount) {
        throw new RuntimeException("Stub!");
    }
    
    public void setIdleConnectionTimeout(final long idleConnectionTimeoutMs) {
        throw new RuntimeException("Stub!");
    }
    
    public SQLiteDatabase getWritableDatabase() {
        throw new RuntimeException("Stub!");
    }
    
    public SQLiteDatabase getReadableDatabase() {
        throw new RuntimeException("Stub!");
    }
    
    public synchronized void close() {
        throw new RuntimeException("Stub!");
    }
    
    public void onConfigure(final SQLiteDatabase db) {
        throw new RuntimeException("Stub!");
    }
    
    public abstract void onCreate(final SQLiteDatabase p0);
    
    public abstract void onUpgrade(final SQLiteDatabase p0, final int p1, final int p2);
    
    public void onDowngrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
        throw new RuntimeException("Stub!");
    }
    
    public void onOpen(final SQLiteDatabase db) {
        throw new RuntimeException("Stub!");
    }
}
