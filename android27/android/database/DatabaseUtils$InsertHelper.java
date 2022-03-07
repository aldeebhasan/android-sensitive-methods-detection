package android.database;

import android.database.sqlite.*;
import android.content.*;

@Deprecated
public static class InsertHelper
{
    public InsertHelper(final SQLiteDatabase db, final String tableName) {
        throw new RuntimeException("Stub!");
    }
    
    public int getColumnIndex(final String key) {
        throw new RuntimeException("Stub!");
    }
    
    public void bind(final int index, final double value) {
        throw new RuntimeException("Stub!");
    }
    
    public void bind(final int index, final float value) {
        throw new RuntimeException("Stub!");
    }
    
    public void bind(final int index, final long value) {
        throw new RuntimeException("Stub!");
    }
    
    public void bind(final int index, final int value) {
        throw new RuntimeException("Stub!");
    }
    
    public void bind(final int index, final boolean value) {
        throw new RuntimeException("Stub!");
    }
    
    public void bindNull(final int index) {
        throw new RuntimeException("Stub!");
    }
    
    public void bind(final int index, final byte[] value) {
        throw new RuntimeException("Stub!");
    }
    
    public void bind(final int index, final String value) {
        throw new RuntimeException("Stub!");
    }
    
    public long insert(final ContentValues values) {
        throw new RuntimeException("Stub!");
    }
    
    public long execute() {
        throw new RuntimeException("Stub!");
    }
    
    public void prepareForInsert() {
        throw new RuntimeException("Stub!");
    }
    
    public void prepareForReplace() {
        throw new RuntimeException("Stub!");
    }
    
    public long replace(final ContentValues values) {
        throw new RuntimeException("Stub!");
    }
    
    public void close() {
        throw new RuntimeException("Stub!");
    }
}
