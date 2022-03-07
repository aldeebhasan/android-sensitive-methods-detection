package joansoft.dailyread;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAdapter {
    private static final String DATABASE_CREATE1 = ("create table " + DATABASE_TABLES[0] + " (_id integer primary key autoincrement, " + KEY_TITLE + " text not null, " + KEY_DATA + " text not null);");
    private static final String DATABASE_CREATE2 = ("create table " + DATABASE_TABLES[1] + " (_id integer primary key autoincrement, " + KEY_TITLE + " text not null, " + KEY_DATA + " text not null);");
    private static final String DATABASE_CREATE3 = ("create table " + DATABASE_TABLES[2] + " (_id integer primary key autoincrement, " + KEY_TITLE + " text not null, " + KEY_DATA + " text not null);");
    private static final String DATABASE_NAME = "JoansoftDailyRead";
    static final String[] DATABASE_TABLES = new String[]{"JSDword", "JSDquote", "JSDjoke"};
    private static final int DATABASE_VERSION = 1;
    public static final String KEY_DATA = "data";
    public static final String KEY_ROWID = "_id";
    public static final String KEY_TITLE = "title";
    private DatabaseHelper DBHelper = new DatabaseHelper(this.context);
    private final Context context;
    private SQLiteDatabase db;

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DBAdapter.DATABASE_NAME, null, 1);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DBAdapter.DATABASE_CREATE1);
            db.execSQL(DBAdapter.DATABASE_CREATE2);
            db.execSQL(DBAdapter.DATABASE_CREATE3);
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }

    public DBAdapter(Context ctx) {
        this.context = ctx;
    }

    public DBAdapter open() throws SQLException {
        this.db = this.DBHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        this.DBHelper.close();
    }

    public long insert(String title, String data, String table) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TITLE, title);
        initialValues.put(KEY_DATA, data);
        return this.db.insert(table, null, initialValues);
    }

    public boolean delete(long rowId, String table) {
        return this.db.delete(table, new StringBuilder("_id=").append(rowId).toString(), null) > 0;
    }

    public Cursor getAll(String table) {
        return this.db.query(table, new String[]{KEY_ROWID, KEY_TITLE, KEY_DATA}, null, null, null, null, "_id DESC ");
    }

    public Cursor get(long rowId, String table) throws SQLException {
        Cursor mCursor = this.db.query(true, table, new String[]{KEY_ROWID, KEY_TITLE, KEY_DATA}, "_id=" + rowId, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
}
