
package com.android.smartshowclient.provider;

import com.android.smartshowclient.provider.OnePieceConstant.Table;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class OnePieceProvider extends BaseProvider implements Table {
    private static final String AUTHORITY = "com.onepiece.heartguard.provider";

    static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    @Override
    public int delete(Uri uri, String whereClause, String[] whereArgs) {
        String table = getTableByUri(uri);
        if (TextUtils.isEmpty(table)) {
            return 0;
        }
        notifyChange(uri);
        return mSQLiteDatabaseOp.delete(table, whereClause, whereArgs);
    }

    @Override
    public String getType(Uri uri) {
        return getTableByUri(uri);
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        String table = getTableByUri(uri);
        if (TextUtils.isEmpty(table)) {
            return ContentUris.withAppendedId(uri, 0);
        } else {
            long id = mSQLiteDatabaseOp.insert(table, null, values);
            notifyChange(ContentUris.withAppendedId(uri, id));
            return ContentUris.withAppendedId(uri, id);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] columns, String where,
            String[] whereArgs, String order) {
        String table = getTableByUri(uri);
        if (TextUtils.isEmpty(table)) {
            return null;
        }
        return mSQLiteDatabaseOp.query(table, columns, where, whereArgs, null,
                null, order);
    }

    @Override
    public int update(Uri uri, ContentValues values, String where,
            String[] whereArgs) {
        String table = getTableByUri(uri);
        if (TextUtils.isEmpty(table)) {
            return 0;
        }
        notifyChange(uri);
        return mSQLiteDatabaseOp.update(table, values, where, whereArgs);
    }

    private static final UriMatcher sURLMatcher = new UriMatcher(
            UriMatcher.NO_MATCH);

    private static final int ACCOUNT = 1;// 广告
    private static final int DATA_DICTIONARY = 2;// 数据字典
    private static final int QUESTION_CONTENT = 3;//问题列表
    private static final int TASK = 4;  //任务
    private static final int TREATMENT = 5; //用药助手
    private static final int MEDICINE_DOSAGE = 6; //药物详情

    static {
        sURLMatcher.addURI(AUTHORITY, OnePieceConstant.Table.T_ACCOUNT, ACCOUNT);
        sURLMatcher.addURI(AUTHORITY, OnePieceConstant.Table.T_DATA_DICTIONARY, DATA_DICTIONARY);
        sURLMatcher.addURI(AUTHORITY, OnePieceConstant.Table.T_TASK, TASK);
        sURLMatcher.addURI(AUTHORITY, OnePieceConstant.Table.T_QUESTION_CONTENT, QUESTION_CONTENT);
        sURLMatcher.addURI(AUTHORITY, OnePieceConstant.Table.T_TREATMENT, TREATMENT);
        sURLMatcher.addURI(AUTHORITY, OnePieceConstant.Table.T_MEDICINE_DOSAGE, MEDICINE_DOSAGE);
    }

    private String getTableByUri(Uri uri) {
        String table;
        switch (sURLMatcher.match(uri)) {
            case ACCOUNT:
                table = T_ACCOUNT;
                break;
            case DATA_DICTIONARY:
                table = T_DATA_DICTIONARY;
                break;
            case TASK:
                table = T_TASK;
                break;
            case QUESTION_CONTENT:
                table = T_QUESTION_CONTENT;
                break;
            case TREATMENT:
                table = T_TREATMENT;
                break;
            case MEDICINE_DOSAGE:
                table = T_MEDICINE_DOSAGE;
                break;
            default:
                table = "";
                Log.e(TAG,
                        "The uri not match any table, please check the Uri is right.",
                        new Exception());
        }
        return table;
    }
}
