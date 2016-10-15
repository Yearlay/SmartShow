package com.android.smartShow.dao;

import java.util.ArrayList;
import java.util.Arrays;

import android.annotation.SuppressLint;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;

/**
 * 防止多线程操作数据库锁死，因此增加安全处理逻辑
 */
public class SafetyDatabaseOperations {

    private static String TAG = "BatchProcessingManager";

    private static final int TYPE_APPLY_BATCH = 101;

    private static final int TYPE_BULK_INSERT = 202;

    private static final int TYPE_INSERT = 301;

    private static final int TYPE_UPDATE = 302;

    private static final int TYPE_DELETE = 303;

    private Context mContext;

    private BatchProcessingHandler mBatchProcessingHandler;

    private static SafetyDatabaseOperations sBatchProcessingManager;

    SparseArray<Object> mLockSparseArray = new SparseArray<Object>();

    SparseArray<Object> mResultSparseArray = new SparseArray<Object>();

    public static SafetyDatabaseOperations getInstance(Context context) {
        if (sBatchProcessingManager == null) {
            synchronized (SafetyDatabaseOperations.class) {
                if (sBatchProcessingManager == null) {
                    sBatchProcessingManager = new SafetyDatabaseOperations(
                            context);
                }
            }
        }
        return sBatchProcessingManager;
    }

    private SafetyDatabaseOperations(Context context) {
        super();
        this.mContext = context.getApplicationContext();
        HandlerThread batchProcessingThread = new HandlerThread(
                "BatchProcessingThread");
        batchProcessingThread.start();
        mBatchProcessingHandler = new BatchProcessingHandler(
                batchProcessingThread.getLooper());
    }

    public ContentProviderResult[] safetyApplyBatch(String authority,
            ArrayList<ContentProviderOperation> operations)
            throws OperationApplicationException {
        int lockId = -1;
        try {
            Pair<String, ArrayList<ContentProviderOperation>> pair = new Pair<String, ArrayList<ContentProviderOperation>>(
                    authority, operations);
            Message msg = new Message();
            msg.what = TYPE_APPLY_BATCH;
            msg.obj = pair;
            lockId = pair.hashCode();

            msg.arg1 = lockId;
            mBatchProcessingHandler.sendMessage(msg);
            try {
                lock(lockId);
            } catch (InterruptedException e) {
                Log.e(TAG, "applyBatchWithResult.InterruptedException ->> ", e);
            }
            return (ContentProviderResult[]) mResultSparseArray.get(lockId);
        } finally {
            mResultSparseArray.remove(lockId);
        }
    }

    public int safetyBulkInsert(Uri uri, ContentValues[] values) {
        int lockId = -1;
        try {
            Pair<Uri, ContentValues[]> pair = new Pair<Uri, ContentValues[]>(
                    uri, values);
            Message msg = new Message();
            msg.what = TYPE_BULK_INSERT;
            msg.obj = pair;
            lockId = pair.hashCode();

            msg.arg1 = lockId;
            mBatchProcessingHandler.sendMessage(msg);
            try {
                lock(lockId);
            } catch (InterruptedException e) {
                Log.e(TAG, "bulkInsertWithResult.InterruptedException ->> ", e);
            }
            return mResultSparseArray.get(lockId) == null ? 0
                    : (Integer) mResultSparseArray.get(lockId);
        } finally {
            mResultSparseArray.remove(lockId);
        }
    }

    public int safetyDelete(Uri uri, String where) {
        int lockId = -1;
        try {
            Pair<Uri, String> pair = new Pair<Uri, String>(uri, where);
            Message msg = new Message();
            msg.what = TYPE_DELETE;
            msg.obj = pair;
            lockId = pair.hashCode();

            msg.arg1 = lockId;
            mBatchProcessingHandler.sendMessage(msg);
            try {
                lock(lockId);
            } catch (InterruptedException e) {
                Log.e(TAG, "bulkInsertWithResult.InterruptedException ->> ", e);
            }
            return mResultSparseArray.get(lockId) == null ? 0
                    : (Integer) mResultSparseArray.get(lockId);
        } finally {
            mResultSparseArray.remove(lockId);
        }
    }

    public int safetyUpdate(Uri uri, ContentValues values, String where,
            String[] args) {
        Log.i(TAG, "updateWithResult uri = " + uri);
        int lockId = -1;
        try {
            Object[] object = new Object[4];
            object[0] = uri;
            object[1] = values;
            object[2] = where;
            object[3] = args;
            Message msg = new Message();
            lockId = object.hashCode();
            msg.what = TYPE_UPDATE;
            msg.obj = object;
            msg.arg1 = lockId;
            mBatchProcessingHandler.sendMessage(msg);
            try {
                lock(lockId);
            } catch (InterruptedException e) {
                Log.e(TAG, "bulkInsertWithResult.InterruptedException ->> ", e);
            }
            return mResultSparseArray.get(lockId) == null ? 0
                    : (Integer) mResultSparseArray.get(lockId);
        } finally {
            mResultSparseArray.remove(lockId);
        }
    }

    public int safetyInsert(Uri uri, ContentValues values) {
        Log.i(TAG, "insertWithResult uri = " + uri);
        int lockId = -1;
        try {
            Object[] object = new Object[4];
            object[0] = uri;
            object[1] = values;
            Message msg = new Message();
            lockId = object.hashCode();
            msg.what = TYPE_UPDATE;
            msg.obj = object;
            msg.arg1 = lockId;
            mBatchProcessingHandler.sendMessage(msg);
            try {
                lock(lockId);
            } catch (InterruptedException e) {
                Log.e(TAG, "bulkInsertWithResult.InterruptedException ->> ", e);
            }
            return mResultSparseArray.get(lockId) == null ? 0
                    : (Integer) mResultSparseArray.get(lockId);
        } finally {
            mResultSparseArray.remove(lockId);
        }
    }

    /**
     * lock the current thread
     * 
     * @throws InterruptedException
     */
    private void lock(int lockId) throws InterruptedException {
        Object obj = new Object();
        mLockSparseArray.put(lockId, obj);
        if (obj != null) {
            synchronized (obj) {
                Log.i(TAG, "Before wait ,lock the thread -> " + lockId);
                obj.wait();
            }
        }
    }

    /**
     * notify the waiting thread
     */
    private void unLock(int lockId) {
        Object obj = mLockSparseArray.get(lockId);
        if (obj != null) {
            synchronized (obj) {
                Log.i(TAG, "After processing , unlock the thread -> " + lockId);
                obj.notify();
                mLockSparseArray.remove(lockId);
            }
        }
    }

    @SuppressLint("HandlerLeak")
    class BatchProcessingHandler extends Handler {

        public BatchProcessingHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int lockId = msg.arg1;
            Object result = null;
            try {
                switch (msg.what) {
                    case TYPE_APPLY_BATCH: {
                        Pair<String, ArrayList<ContentProviderOperation>> pair = (Pair<String, ArrayList<ContentProviderOperation>>) msg.obj;
                        result = mContext.getContentResolver().applyBatch(
                                pair.first, pair.second);
                        break;
                    }
                    case TYPE_BULK_INSERT: {
                        Pair<Uri, ContentValues[]> pair = (Pair<Uri, ContentValues[]>) msg.obj;
                        result = mContext.getContentResolver().bulkInsert(
                                pair.first, pair.second);
                        break;
                    }
                    case TYPE_DELETE: {
                        Pair<Uri, String> pair = (Pair<Uri, String>) msg.obj;
                        result = mContext.getContentResolver().delete(
                                pair.first, pair.second, null);
                    }
                    case TYPE_UPDATE: {
                        Object object[] = (Object[]) msg.obj;
                        Uri uri = (Uri) object[0];
                        ContentValues values = (ContentValues) object[1];
                        String where = (String) object[2];
                        String[] args = (String[]) object[3];
                        Log.i(TAG, " TYPE_UPDATE -> uri = " + uri
                                + ", values = " + values + ", where = " + where
                                + ", args = " + Arrays.toString(args));
                        result = mContext.getContentResolver().update(uri,
                                values, where, args);
                        break;
                    }
                    case TYPE_INSERT: {
                        Object object[] = (Object[]) msg.obj;
                        Uri uri = (Uri) object[0];
                        ContentValues values = (ContentValues) object[1];
                        Log.i(TAG, " TYPE_UPDATE -> uri = " + uri
                                + ", values = " + values);
                        result = mContext.getContentResolver().insert(uri,
                                values);
                        break;
                    }
                    default:
                }
            } catch (Exception e) {
                Log.e(TAG, "BatchProcessingHandler.handleMessage what = "
                        + msg.what, e);
            } finally {
                if (lockId != 0 && mLockSparseArray.indexOfKey(lockId) >= 0) {
                    mResultSparseArray.put(lockId, result);
                    unLock(lockId);
                } else if (lockId != 0
                        && mLockSparseArray.indexOfKey(lockId) < 0) {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (lockId != 0 && mLockSparseArray.indexOfKey(lockId) >= 0) {
                        mResultSparseArray.put(lockId, result);
                        unLock(lockId);
                    }
                }
            }
        }
    }
}
