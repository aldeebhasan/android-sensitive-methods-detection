package android.database.sqlite;

import java.io.*;
import android.database.*;
import android.os.*;
import android.content.*;
import java.util.*;
import android.util.*;

public final class SQLiteDatabase extends SQLiteClosable
{
    public static final int CONFLICT_ABORT = 2;
    public static final int CONFLICT_FAIL = 3;
    public static final int CONFLICT_IGNORE = 4;
    public static final int CONFLICT_NONE = 0;
    public static final int CONFLICT_REPLACE = 5;
    public static final int CONFLICT_ROLLBACK = 1;
    public static final int CREATE_IF_NECESSARY = 268435456;
    public static final int ENABLE_WRITE_AHEAD_LOGGING = 536870912;
    public static final int MAX_SQL_CACHE_SIZE = 100;
    public static final int NO_LOCALIZED_COLLATORS = 16;
    public static final int OPEN_READONLY = 1;
    public static final int OPEN_READWRITE = 0;
    public static final int SQLITE_MAX_LIKE_PATTERN_LENGTH = 50000;
    
    SQLiteDatabase() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void finalize() throws Throwable {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void onAllReferencesReleased() {
        throw new RuntimeException("Stub!");
    }
    
    public static int releaseMemory() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void setLockingEnabled(final boolean lockingEnabled) {
        throw new RuntimeException("Stub!");
    }
    
    public void beginTransaction() {
        throw new RuntimeException("Stub!");
    }
    
    public void beginTransactionNonExclusive() {
        throw new RuntimeException("Stub!");
    }
    
    public void beginTransactionWithListener(final SQLiteTransactionListener transactionListener) {
        throw new RuntimeException("Stub!");
    }
    
    public void beginTransactionWithListenerNonExclusive(final SQLiteTransactionListener transactionListener) {
        throw new RuntimeException("Stub!");
    }
    
    public void endTransaction() {
        throw new RuntimeException("Stub!");
    }
    
    public void setTransactionSuccessful() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean inTransaction() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isDbLockedByCurrentThread() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public boolean isDbLockedByOtherThreads() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public boolean yieldIfContended() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean yieldIfContendedSafely() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean yieldIfContendedSafely(final long sleepAfterYieldDelay) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public Map<String, String> getSyncedTables() {
        throw new RuntimeException("Stub!");
    }
    
    public static SQLiteDatabase openDatabase(final String path, final CursorFactory factory, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    public static SQLiteDatabase openDatabase(final File path, final OpenParams openParams) {
        throw new RuntimeException("Stub!");
    }
    
    public static SQLiteDatabase openDatabase(final String path, final CursorFactory factory, final int flags, final DatabaseErrorHandler errorHandler) {
        throw new RuntimeException("Stub!");
    }
    
    public static SQLiteDatabase openOrCreateDatabase(final File file, final CursorFactory factory) {
        throw new RuntimeException("Stub!");
    }
    
    public static SQLiteDatabase openOrCreateDatabase(final String path, final CursorFactory factory) {
        throw new RuntimeException("Stub!");
    }
    
    public static SQLiteDatabase openOrCreateDatabase(final String path, final CursorFactory factory, final DatabaseErrorHandler errorHandler) {
        throw new RuntimeException("Stub!");
    }
    
    public static boolean deleteDatabase(final File file) {
        throw new RuntimeException("Stub!");
    }
    
    public static SQLiteDatabase create(final CursorFactory factory) {
        throw new RuntimeException("Stub!");
    }
    
    public static SQLiteDatabase createInMemory(final OpenParams openParams) {
        throw new RuntimeException("Stub!");
    }
    
    public int getVersion() {
        throw new RuntimeException("Stub!");
    }
    
    public void setVersion(final int version) {
        throw new RuntimeException("Stub!");
    }
    
    public long getMaximumSize() {
        throw new RuntimeException("Stub!");
    }
    
    public long setMaximumSize(final long numBytes) {
        throw new RuntimeException("Stub!");
    }
    
    public long getPageSize() {
        throw new RuntimeException("Stub!");
    }
    
    public void setPageSize(final long numBytes) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void markTableSyncable(final String table, final String deletedTable) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void markTableSyncable(final String table, final String foreignKey, final String updateTable) {
        throw new RuntimeException("Stub!");
    }
    
    public static String findEditTable(final String tables) {
        throw new RuntimeException("Stub!");
    }
    
    public SQLiteStatement compileStatement(final String sql) throws SQLException {
        throw new RuntimeException("Stub!");
    }
    
    public Cursor query(final boolean distinct, final String table, final String[] columns, final String selection, final String[] selectionArgs, final String groupBy, final String having, final String orderBy, final String limit) {
        throw new RuntimeException("Stub!");
    }
    
    public Cursor query(final boolean distinct, final String table, final String[] columns, final String selection, final String[] selectionArgs, final String groupBy, final String having, final String orderBy, final String limit, final CancellationSignal cancellationSignal) {
        throw new RuntimeException("Stub!");
    }
    
    public Cursor queryWithFactory(final CursorFactory cursorFactory, final boolean distinct, final String table, final String[] columns, final String selection, final String[] selectionArgs, final String groupBy, final String having, final String orderBy, final String limit) {
        throw new RuntimeException("Stub!");
    }
    
    public Cursor queryWithFactory(final CursorFactory cursorFactory, final boolean distinct, final String table, final String[] columns, final String selection, final String[] selectionArgs, final String groupBy, final String having, final String orderBy, final String limit, final CancellationSignal cancellationSignal) {
        throw new RuntimeException("Stub!");
    }
    
    public Cursor query(final String table, final String[] columns, final String selection, final String[] selectionArgs, final String groupBy, final String having, final String orderBy) {
        throw new RuntimeException("Stub!");
    }
    
    public Cursor query(final String table, final String[] columns, final String selection, final String[] selectionArgs, final String groupBy, final String having, final String orderBy, final String limit) {
        throw new RuntimeException("Stub!");
    }
    
    public Cursor rawQuery(final String sql, final String[] selectionArgs) {
        throw new RuntimeException("Stub!");
    }
    
    public Cursor rawQuery(final String sql, final String[] selectionArgs, final CancellationSignal cancellationSignal) {
        throw new RuntimeException("Stub!");
    }
    
    public Cursor rawQueryWithFactory(final CursorFactory cursorFactory, final String sql, final String[] selectionArgs, final String editTable) {
        throw new RuntimeException("Stub!");
    }
    
    public Cursor rawQueryWithFactory(final CursorFactory cursorFactory, final String sql, final String[] selectionArgs, final String editTable, final CancellationSignal cancellationSignal) {
        throw new RuntimeException("Stub!");
    }
    
    public long insert(final String table, final String nullColumnHack, final ContentValues values) {
        throw new RuntimeException("Stub!");
    }
    
    public long insertOrThrow(final String table, final String nullColumnHack, final ContentValues values) throws SQLException {
        throw new RuntimeException("Stub!");
    }
    
    public long replace(final String table, final String nullColumnHack, final ContentValues initialValues) {
        throw new RuntimeException("Stub!");
    }
    
    public long replaceOrThrow(final String table, final String nullColumnHack, final ContentValues initialValues) throws SQLException {
        throw new RuntimeException("Stub!");
    }
    
    public long insertWithOnConflict(final String table, final String nullColumnHack, final ContentValues initialValues, final int conflictAlgorithm) {
        throw new RuntimeException("Stub!");
    }
    
    public int delete(final String table, final String whereClause, final String[] whereArgs) {
        throw new RuntimeException("Stub!");
    }
    
    public int update(final String table, final ContentValues values, final String whereClause, final String[] whereArgs) {
        throw new RuntimeException("Stub!");
    }
    
    public int updateWithOnConflict(final String table, final ContentValues values, final String whereClause, final String[] whereArgs, final int conflictAlgorithm) {
        throw new RuntimeException("Stub!");
    }
    
    public void execSQL(final String sql) throws SQLException {
        throw new RuntimeException("Stub!");
    }
    
    public void execSQL(final String sql, final Object[] bindArgs) throws SQLException {
        throw new RuntimeException("Stub!");
    }
    
    public void validateSql(final String sql, final CancellationSignal cancellationSignal) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isReadOnly() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isOpen() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean needUpgrade(final int newVersion) {
        throw new RuntimeException("Stub!");
    }
    
    public final String getPath() {
        throw new RuntimeException("Stub!");
    }
    
    public void setLocale(final Locale locale) {
        throw new RuntimeException("Stub!");
    }
    
    public void setMaxSqlCacheSize(final int cacheSize) {
        throw new RuntimeException("Stub!");
    }
    
    public void setForeignKeyConstraintsEnabled(final boolean enable) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean enableWriteAheadLogging() {
        throw new RuntimeException("Stub!");
    }
    
    public void disableWriteAheadLogging() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isWriteAheadLoggingEnabled() {
        throw new RuntimeException("Stub!");
    }
    
    public List<Pair<String, String>> getAttachedDbs() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isDatabaseIntegrityOk() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
    
    public static final class OpenParams
    {
        OpenParams() {
            throw new RuntimeException("Stub!");
        }
        
        public int getLookasideSlotSize() {
            throw new RuntimeException("Stub!");
        }
        
        public int getLookasideSlotCount() {
            throw new RuntimeException("Stub!");
        }
        
        public int getOpenFlags() {
            throw new RuntimeException("Stub!");
        }
        
        public CursorFactory getCursorFactory() {
            throw new RuntimeException("Stub!");
        }
        
        public DatabaseErrorHandler getErrorHandler() {
            throw new RuntimeException("Stub!");
        }
        
        public long getIdleConnectionTimeout() {
            throw new RuntimeException("Stub!");
        }
        
        public static final class Builder
        {
            public Builder() {
                throw new RuntimeException("Stub!");
            }
            
            public Builder(final OpenParams params) {
                throw new RuntimeException("Stub!");
            }
            
            public Builder setLookasideConfig(final int slotSize, final int slotCount) {
                throw new RuntimeException("Stub!");
            }
            
            public Builder setOpenFlags(final int openFlags) {
                throw new RuntimeException("Stub!");
            }
            
            public Builder addOpenFlags(final int openFlags) {
                throw new RuntimeException("Stub!");
            }
            
            public Builder removeOpenFlags(final int openFlags) {
                throw new RuntimeException("Stub!");
            }
            
            public Builder setCursorFactory(final CursorFactory cursorFactory) {
                throw new RuntimeException("Stub!");
            }
            
            public Builder setErrorHandler(final DatabaseErrorHandler errorHandler) {
                throw new RuntimeException("Stub!");
            }
            
            public Builder setIdleConnectionTimeout(final long idleConnectionTimeoutMs) {
                throw new RuntimeException("Stub!");
            }
            
            public OpenParams build() {
                throw new RuntimeException("Stub!");
            }
        }
    }
    
    public interface CursorFactory
    {
        Cursor newCursor(final SQLiteDatabase p0, final SQLiteCursorDriver p1, final String p2, final SQLiteQuery p3);
    }
}
