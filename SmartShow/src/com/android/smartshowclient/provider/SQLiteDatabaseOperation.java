package com.android.smartshowclient.provider;

import android.content.ContentValues;
import android.database.Cursor;

public interface SQLiteDatabaseOperation {

    public long insert(String table, String nullColumnHack, ContentValues values);
    
    public int delete(String table, String whereClause, String[] whereArgs);

    public int update(String table, ContentValues values, String whereClause,
            String[] whereArgs);

    public Cursor query(String table, String[] columns, String selection,
            String[] selectionArgs, String groupBy, String having,
            String orderBy);

    public void beginTransaction();

    public void endTransaction();

    public void setTransactionSuccessful();

    public void execSQL(String sql);

    public void execSQL(String sql, String[] bindArgs);

    public void reset(String phoneNumber);

    public Cursor rawQuery(String sql, String[] selectionArgs);
}
