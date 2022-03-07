package android.database;

import android.database.sqlite.*;

public final class DefaultDatabaseErrorHandler implements DatabaseErrorHandler
{
    public DefaultDatabaseErrorHandler() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onCorruption(final SQLiteDatabase dbObj) {
        throw new RuntimeException("Stub!");
    }
}
