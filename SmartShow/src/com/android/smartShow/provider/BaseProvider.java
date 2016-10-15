package com.android.smartShow.provider;

import java.util.ArrayList;

import com.android.smartShow.OnePieceApplication;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

/**
 * This class define the same method which be used by all providers.
 * 
 * @author fei.zhang
 */
public abstract class BaseProvider extends ContentProvider {
    protected final String TAG = BaseProvider.class.getSimpleName();

    /** 数据库操作对象 */
    SQLiteDatabaseOperation mSQLiteDatabaseOp;

    @Override
    public ContentProviderResult[] applyBatch(
            ArrayList<ContentProviderOperation> operations)
            throws OperationApplicationException {
        // If do the default applyBatch, it doesn't used the transaction.
        // you can add the transaction logic at here.
        ContentProviderResult[] cpr = null;
        // make sure to use try finally structure
        try {
            mSQLiteDatabaseOp.beginTransaction();
            cpr = super.applyBatch(operations);
            mSQLiteDatabaseOp.setTransactionSuccessful();
        } catch (Exception ex) {
            Log.e(TAG, "transaction failed");
            ex.printStackTrace();
        } finally {
            mSQLiteDatabaseOp.endTransaction();
        }
        return cpr;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        int count = 0;
        try {
            mSQLiteDatabaseOp.beginTransaction();
            // If you want do a transaction, insert large data,
            // please add the operation at here.
            count = super.bulkInsert(uri, values);
            mSQLiteDatabaseOp.setTransactionSuccessful();
        } catch (Exception ex) {
            Log.e(TAG, "transaction failed");
            ex.printStackTrace();
        } finally {
            mSQLiteDatabaseOp.endTransaction();
        }

        if (count > 0) {
            notifyChange(uri);
        }
        return count;
    }

    /**
     * Merger two selections to one.
     * 
     */
    public String concatSelections(String selection1, String selection2) {
        if (TextUtils.isEmpty(selection1)) {
            return selection2;
        } else if (TextUtils.isEmpty(selection2)) {
            return selection1;
        } else {
            return selection1 + " AND " + selection2;
        }
    }


    /**
     */
    public void notifyChange(Uri uri) {
        getContext().getContentResolver().notifyChange(uri, null);
    }

    /**
     */
    public void notifyChange(Uri uri, boolean syncToNetwork) {
        getContext().getContentResolver()
                .notifyChange(uri, null, syncToNetwork);
    }

    @Override
    public boolean onCreate() {
        if (OnePieceApplication.getContext() == null) {
            OnePieceApplication
                    .setContext(getContext().getApplicationContext());
        }

        if (mSQLiteDatabaseOp == null) {
            mSQLiteDatabaseOp = OnePieceDatabaseHelper.getInstance(getContext());
        }
        return false;
    }

}
