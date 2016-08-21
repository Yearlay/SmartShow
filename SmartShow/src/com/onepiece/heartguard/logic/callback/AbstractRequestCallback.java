package com.onepiece.heartguard.logic.callback;

import java.util.HashMap;
import java.util.Map;

import com.project.template.Constant;
import com.project.template.ErrorCode;
import com.project.template.OnePieceRequestCallback;
import com.project.template.utils.Log;

import android.content.Context;
import android.os.Handler;


/**
 * This is an abstract class which implement the
 * MeeloStream.StreamRequestCallback This callback play the communication role
 * between with ui and sdk It'll be passed to sdk method as Parameter and sdk
 * will call the onRequestFinish if the request completed successfully,otherwise
 * the onRequestError will be called.
 * 
 * @author Gunns Guo
 * @version 1.0
 */
public abstract class AbstractRequestCallback implements OnePieceRequestCallback {

    protected final String TAG = this.getClass().getSimpleName();

    protected RequestCallback mCallback;

    protected Handler mHandler;

    protected int mRequestEvent;

    public Context mContext;

    public AbstractRequestCallback(Context context, int event, RequestCallback callback) {
        this.mCallback = callback;
        this.mRequestEvent = event;
        if (context != null) {
            this.mContext = context.getApplicationContext();
        }
        try {
            this.mHandler = new Handler();
        } catch (Exception e) {
            this.mHandler = null;
        }

        if (this.mHandler == null) {
            if (context != null) {
                try {
                    this.mHandler = new Handler(context.getMainLooper());
                } catch (Exception e) {
                    this.mHandler = null;
                }
            }
        }
    }

    private void notify(final boolean isOk, final Map<String, Object> result) {
        if (mCallback == null) {
            Log.w(TAG, "the callback is null");
            return;
        }
        if (mHandler == null) {
            mCallback.onResult(mRequestEvent, isOk, result);
        } else {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onResult(mRequestEvent, isOk, result);
                }
            });
        }
    }

    @Override
    public void onRequestSuccess(int event, String message, Map<String, Object> result) {
        Log.d(TAG, "onRequestSuccess event = " + event + ", message = " + message + ", result = "
                + result);
        // 由实现类处理成功数据
        boolean isok = onSuccessed(result);

        // 回调结果给请求的类
        notify(isok, result);
    }

    @Override
    public void onRequestFailure(int event, String message, Throwable throwable) {
        Log.w(TAG, "onRequestFailure event = " + event + ", message = " + message
                + ", Throwable = " + throwable);
        // 由实现类处理失败逻辑
        onFailure(throwable);

        // 回调结果给请求的类
        if (throwable instanceof java.net.ConnectException) {
            // 处理"HTTP连接10s超时，请检查网络情况"
            HashMap<String, Object> result = new HashMap<String, Object>();
            result.put(Constant.KEY_ERROR_CODE, ErrorCode.HTTP_CONNECT_EXCEPTION_ID);
            result.put(Constant.KEY_RESULT_CONTENT, ErrorCode.HTTP_CONNECT_EXCEPTION_MESSAGE);
            notify(false, result);
        } else {
            notify(false, null);
        }
    }

    public abstract boolean onSuccessed(Map<String, Object> result);

    public abstract void onFailure(Throwable th);
}
