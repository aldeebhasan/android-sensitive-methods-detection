package android.database.sqlite;

import android.database.*;

public interface CursorFactory
{
    Cursor newCursor(final SQLiteDatabase p0, final SQLiteCursorDriver p1, final String p2, final SQLiteQuery p3);
}
