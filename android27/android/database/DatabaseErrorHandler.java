package android.database;

import android.database.sqlite.*;

public interface DatabaseErrorHandler
{
    void onCorruption(final SQLiteDatabase p0);
}
