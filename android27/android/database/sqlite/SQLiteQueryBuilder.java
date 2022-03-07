package android.database.sqlite;

import android.database.*;
import android.os.*;
import java.util.*;

public class SQLiteQueryBuilder
{
    public SQLiteQueryBuilder() {
        throw new RuntimeException("Stub!");
    }
    
    public void setDistinct(final boolean distinct) {
        throw new RuntimeException("Stub!");
    }
    
    public String getTables() {
        throw new RuntimeException("Stub!");
    }
    
    public void setTables(final String inTables) {
        throw new RuntimeException("Stub!");
    }
    
    public void appendWhere(final CharSequence inWhere) {
        throw new RuntimeException("Stub!");
    }
    
    public void appendWhereEscapeString(final String inWhere) {
        throw new RuntimeException("Stub!");
    }
    
    public void setProjectionMap(final Map<String, String> columnMap) {
        throw new RuntimeException("Stub!");
    }
    
    public void setCursorFactory(final SQLiteDatabase.CursorFactory factory) {
        throw new RuntimeException("Stub!");
    }
    
    public void setStrict(final boolean flag) {
        throw new RuntimeException("Stub!");
    }
    
    public static String buildQueryString(final boolean distinct, final String tables, final String[] columns, final String where, final String groupBy, final String having, final String orderBy, final String limit) {
        throw new RuntimeException("Stub!");
    }
    
    public static void appendColumns(final StringBuilder s, final String[] columns) {
        throw new RuntimeException("Stub!");
    }
    
    public Cursor query(final SQLiteDatabase db, final String[] projectionIn, final String selection, final String[] selectionArgs, final String groupBy, final String having, final String sortOrder) {
        throw new RuntimeException("Stub!");
    }
    
    public Cursor query(final SQLiteDatabase db, final String[] projectionIn, final String selection, final String[] selectionArgs, final String groupBy, final String having, final String sortOrder, final String limit) {
        throw new RuntimeException("Stub!");
    }
    
    public Cursor query(final SQLiteDatabase db, final String[] projectionIn, final String selection, final String[] selectionArgs, final String groupBy, final String having, final String sortOrder, final String limit, final CancellationSignal cancellationSignal) {
        throw new RuntimeException("Stub!");
    }
    
    public String buildQuery(final String[] projectionIn, final String selection, final String groupBy, final String having, final String sortOrder, final String limit) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public String buildQuery(final String[] projectionIn, final String selection, final String[] selectionArgs, final String groupBy, final String having, final String sortOrder, final String limit) {
        throw new RuntimeException("Stub!");
    }
    
    public String buildUnionSubQuery(final String typeDiscriminatorColumn, final String[] unionColumns, final Set<String> columnsPresentInTable, final int computedColumnsOffset, final String typeDiscriminatorValue, final String selection, final String groupBy, final String having) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public String buildUnionSubQuery(final String typeDiscriminatorColumn, final String[] unionColumns, final Set<String> columnsPresentInTable, final int computedColumnsOffset, final String typeDiscriminatorValue, final String selection, final String[] selectionArgs, final String groupBy, final String having) {
        throw new RuntimeException("Stub!");
    }
    
    public String buildUnionQuery(final String[] subQueries, final String sortOrder, final String limit) {
        throw new RuntimeException("Stub!");
    }
}
