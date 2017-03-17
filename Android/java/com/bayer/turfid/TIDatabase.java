package com.bayer.turfid;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TIDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_PATH = "/data/data/com.bayer.turfid/databases/";
    private static final String DATABASE_NAME = "TurfId.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    public TIDatabase(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public TIDatabase open() throws SQLException {
        String dbPath = DATABASE_PATH + DATABASE_NAME;
        db = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY | SQLiteDatabase.NO_LOCALIZED_COLLATORS);
        return this;
    }

    public void close() {
        db.close();
    }

    public Cursor FetchAllRecords(String tableName, String selection, String orderBy) {
        Cursor c;
        if (selection.equals("")) {
            c = this.db.query(tableName, new String[]{"*"}, null, null, null, null, orderBy);
        } else {
            c = this.db.query(tableName, new String[]{"*"}, selection, null, null, null, orderBy);
        }
        if (c == null)
            return null;

        return c;
    }

    public Cursor FetchAllRecordsForRawQuery(String query) {
        Cursor c;
        c = this.db.rawQuery(query, null);
        if (c == null)
            return null;

        return c;
    }

    @Override
    public void onCreate(SQLiteDatabase arg0) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
