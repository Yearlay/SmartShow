
package com.android.smartshowclient.logic;

import java.util.Map;

import com.android.smartshowclient.callback.AbstractRequestCallback;
import com.android.smartshowclient.callback.RequestCallback;
import com.android.smartshowclient.service.CoreService;
import com.android.smartshowclient.service.CoreService.Transaction;

import android.content.Context;
import android.util.Log;

public abstract class AbstractManager {
    public final String TAG = this.getClass().getSimpleName();

    protected Context mContext;

    protected AbstractManager(Context context) {
        super();
        this.mContext = context;
    }

    public void test(String params, RequestCallback callback) {
        Log.i(TAG, "UserManager getBabyInfo");
        Transaction transaction = new Transaction();

        // 根据接口不同，传递不同的 request code
        // transaction.what = Constant.PublicRequest.REQUEST_REGISTER;
        transaction.callback = new TestCallback(mContext, transaction.what, callback);

        // 传递指定参数类型
        transaction.object = null;

        CoreService.requestTransaction(mContext, transaction);
    }

    // 在manager中实现自定义的callback
    class TestCallback extends AbstractRequestCallback {

        public TestCallback(Context context, int event, RequestCallback callback) {
            super(context, event, callback);
        }

        @Override
        public boolean onSuccessed(Map<String, Object> result) {
            Log.i(TAG, "getBabyInfo result = " + result);
            return true;
        }

        @Override
        public void onFailure(Throwable th) {

        }

    }
}
