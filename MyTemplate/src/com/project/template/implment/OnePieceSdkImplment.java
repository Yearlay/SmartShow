
package com.project.template.implment;

import java.util.HashMap;
import java.util.UUID;

import com.project.template.Config;
import com.project.template.Constant.AccountRequest;
import com.project.template.OnePieceHeadlineListenner;
import com.project.template.OnePieceRequestCallback;
import com.project.template.http.DataRequest;
import com.project.template.http.OnePieceAsyncHttpClient;
import com.project.template.requestbean.RequestBean;
import com.project.template.utils.Log;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Process;

/**
 * <b>Description:</b> </br> Sdk的实现：启动异步事务线程；并对SDK的接口的具体的实现；</br> implement
 * {@link OnePieceSdk}；扩展接口的重要切入点。
 * 
 * @author 叶蕾
 */
public class OnePieceSdkImplment extends OnePieceSdk {
    private String TAG = this.getClass().getSimpleName();

    private static HashMap<String, DataRequest> sRequestQueue = new HashMap<String, DataRequest>();
    private static OnePieceSdkImplment mOnePieceSdkImplment;

    private static final int POST = 101;
    private static final int GET = 102;
    private TransactionHandler mTransactionHandler;

    OnePieceHeadlineListenner mOnePieceHeadlineListenner;

    Context mContext;

    class TransactionHandler extends Handler {

        public TransactionHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            // 根据参数what不同，处理不同的请求事件
            if (msg.what == POST) {
                (new OnePieceAsyncHttpClient()).post(mContext, (DataRequest) msg.obj);
            } else if (msg.what == GET) {
                (new OnePieceAsyncHttpClient()).get((DataRequest) msg.obj);
            }
        }

    }

    /**
     * <b>Description:</b> </br> 对SDK的实现OnePieceSdkImplment进行初始化（单例对象）。
     * 
     * @param context 从App传过来的上下文
     * @param onePieceHeadlineListenner push消息监听器
     * @return 初始化的OnePieceSdkImplment的单例对象。
     */
    public static synchronized OnePieceSdkImplment initSdk(Context context,
            OnePieceHeadlineListenner onePieceHeadlineListenner) {
        if (mOnePieceSdkImplment == null) {
            mOnePieceSdkImplment = new OnePieceSdkImplment(context);
            mOnePieceSdkImplment.mOnePieceHeadlineListenner = onePieceHeadlineListenner;
        }
        return mOnePieceSdkImplment;
    }

    public static OnePieceSdkImplment getSdk() {
        return mOnePieceSdkImplment;
    }

    private OnePieceSdkImplment(Context context) {
        super();
        Log.i(TAG, "new OnePieceSdkImplment.");
        this.mContext = context;
        // 启动事务线程，优先级低。
        HandlerThread transactionThread = new HandlerThread(
                TransactionHandler.class.getName(),
                Process.THREAD_PRIORITY_BACKGROUND);
        transactionThread.start(); // 启动工作线程
        // 创建Handler，保证事件的处理按照一定的顺序
        mTransactionHandler = new TransactionHandler(transactionThread.getLooper());
    }

    /** {@link OnePieceSdkImplment#POST} */
    private void addPostRequestWithEvent(int event, RequestBean requestBean,
            OnePieceRequestCallback callback) {
        Log.d(TAG, "event: " + event);
        DataRequest dataRequest = new DataRequest();
        try {
            dataRequest.id = getUniqueRequestId();
            dataRequest.event = event;
            dataRequest.url = Config.getUrl(event);
            Log.d(TAG, "URL: " + dataRequest.url);
            dataRequest.callback = callback;
            dataRequest.requestBean = requestBean;
            addRequest(dataRequest, POST);
        } catch (Exception e) {
            callback.onRequestFailure(dataRequest.event, "请求异常", e);
        }
    }

    private String getUniqueRequestId() {
        return UUID.randomUUID().toString();
    }

    private void addRequest(DataRequest request, int what) {
        sRequestQueue.put(request.id, request);
        Message msg = new Message();
        msg.what = what;
        msg.obj = request;
        mTransactionHandler.sendMessage(msg);
    }

    public static void removeRequestFromQueue(String id) {
        if (sRequestQueue.containsKey(id)) {
            sRequestQueue.remove(id);
        }
    }

    // Begin: 用户管理的接口 -------------------------------------------

    @Override
    public void getVerifyCode(RequestBean requestBean, OnePieceRequestCallback callback) {
        Log.i(TAG, "getVerifyCode() ->" + requestBean);
        addPostRequestWithEvent(AccountRequest.REQUEST_GET_VERIFY_CODE, requestBean, callback);
    }

    @Override
    public void registerUser(RequestBean requestBean, OnePieceRequestCallback callback) {
        Log.i(TAG, "register() ->" + requestBean);
        addPostRequestWithEvent(AccountRequest.REQUEST_REGISTER, requestBean, callback);
    }

    @Override
    public void login(RequestBean requestBean, OnePieceRequestCallback callback) {
        Log.i(TAG, "loginForPatient() ->" + requestBean);
        addPostRequestWithEvent(AccountRequest.REQUEST_LOGIN, requestBean, callback);
    }

    @Override
    public void updatePassword(RequestBean requestBean, OnePieceRequestCallback callback) {
        Log.i(TAG, "updatePassword() ->" + requestBean);
        addPostRequestWithEvent(AccountRequest.REQUEST_UPDATE_PASSWORD, requestBean, callback);
    }

    @Override
    public void resetPassword(RequestBean requestBean, OnePieceRequestCallback callback) {
        Log.i(TAG, "resetPassword() ->" + requestBean);
        addPostRequestWithEvent(AccountRequest.REQUEST_RESET_PASSWORD, requestBean, callback);
    }

    @Override
    public void updatePersonalInfo(RequestBean requestBean, OnePieceRequestCallback callback) {
        Log.i(TAG, "updatePersonalInfo() ->" + requestBean);
        addPostRequestWithEvent(AccountRequest.REQUEST_UPDATE_PERSONAL_INFO, requestBean, callback);
    }
    // End: 用户管理的接口 ---------------------------------------------

}
