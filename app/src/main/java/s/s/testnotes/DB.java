package s.s.testnotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Sovochka on 14.03.2018.
 */

public class DB {

    private static DB sInstance = null;
    private Context mCtx;

    private DB(Context ctx) {
        mCtx = ctx;
    }

    public static synchronized DB getInstance(Context ctx) {
        if (sInstance == null)
            sInstance = new DB(ctx);
        return sInstance;
    }

    private static final String DB_NAME = "mydb";
    private static final int DB_VERSION = 1;
    private static final String DB_TABLE = "mytab";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TXT = "txt";

    private static final String DB_CREATE = "create table " + DB_TABLE + "(" + COLUMN_ID + " integer primary key autoincrement, " +
            COLUMN_TXT + " text);";


    private DBHelper mDBHelper;
    private SQLiteDatabase mDB;

    public void open() {
        mDBHelper = new DBHelper(mCtx, DB_NAME, null, DB_VERSION);
        mDB = mDBHelper.getWritableDatabase();
    }

    public void close() {
        if (mDBHelper != null) mDBHelper.close();
    }

    public Cursor getAllData() {
        return mDB.query(DB_TABLE, null, null, null, null, null, null);
    }

    public String getNote(int pos) {
        String[] columns = new String[]{COLUMN_TXT};
        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = new String[]{String.valueOf(pos)};
        Cursor c = mDB.query(DB_TABLE, columns, selection, selectionArgs, null, null, null);
        String text = "";
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    for (String cn : c.getColumnNames()) {
                        text = text.concat(c.getString(c.getColumnIndex(cn)));
                    }
                    //Log.d("myLogs", text);

                } while (c.moveToNext());
            }
            c.close();
        }
        return text;
    }

    public void addRec(String txt) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TXT, txt);
        mDB.insert(DB_TABLE, null, cv);
    }

    public void delRec(long id) {
        mDB.delete(DB_TABLE, COLUMN_ID + " = " + id, null);
    }

    public void updRec(long id, String txt) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TXT, txt);
        mDB.update(DB_TABLE, cv, COLUMN_ID + " = " + id, null);
    }

    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}