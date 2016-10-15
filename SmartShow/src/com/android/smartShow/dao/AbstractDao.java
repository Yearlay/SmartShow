package com.android.smartShow.dao;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

public abstract class AbstractDao {
    protected final String TAG = this.getClass().getSimpleName();

    protected Context mContext;

    protected WorkAsyncQueryHandler mAsyncQueryHandler;
    protected OnePieceAsyncQueryResultCallback mCallback;

    protected SafetyDatabaseOperations mSafetyDbOperations;

    public AbstractDao(Context context, OnePieceAsyncQueryResultCallback callback){
        if (context == null) {
            throw new NullPointerException(
                    "can not create dao with null context.");
        }

        this.mContext = context.getApplicationContext();
        mCallback = callback;
        if (null != mCallback) {
            mAsyncQueryHandler = new WorkAsyncQueryHandler(
                    mContext.getContentResolver());
        }
        mSafetyDbOperations = SafetyDatabaseOperations.getInstance(mContext);
    }

    abstract protected Object cursorToModelOrModelList(int token,
            Object cookie, Cursor cursor);

    protected void query(int token, Object cookie, Uri uri, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        mAsyncQueryHandler.startQuery(token, cookie, uri, projection, selection, selectionArgs, orderBy);
    }

    class WorkAsyncQueryHandler extends AsyncQueryHandler {

        public WorkAsyncQueryHandler(ContentResolver cr) {
            super(cr);
        }

        @Override
        protected void onDeleteComplete(int token, Object cookie, int result) {
            super.onDeleteComplete(token, cookie, result);
            if (mCallback != null) {
                mCallback.onDeleteComplete(token, cookie, result);
            }
        }

        @Override
        protected void onInsertComplete(int token, Object cookie, Uri uri) {
            super.onInsertComplete(token, cookie, uri);
            if (mCallback != null) {
                mCallback.onInsertComplete(token, cookie, uri);
            }
        }

        @Override
        protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
            super.onQueryComplete(token, cookie, cursor);
            if (mCallback != null) {
                Object o = cursorToModelOrModelList(token, cookie, cursor);
                mCallback.onQueryComplete(token, cookie, o);
            }
        }

        @Override
        protected void onUpdateComplete(int token, Object cookie, int result) {
            super.onUpdateComplete(token, cookie, result);
            if (mCallback != null) {
                mCallback.onUpdateComplete(token, cookie, result);
            }
        }

    }

}
