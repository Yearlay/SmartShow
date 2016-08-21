
package com.android.smartShow.service;

import java.util.ArrayList;

import com.android.smartShow.dao.UIResponse;
import com.project.template.OnePieceHeadlineListenner;
import com.project.template.OnePieceRequestCallback;
import com.project.template.commonbean.UserHeadline;
import com.project.template.implment.OnePieceSdk;
import com.project.template.implment.OnePieceSdkImplment;
import com.project.template.requestbean.RequestBean;
import com.project.template.utils.Constant.UIRequestCode;
import com.project.template.utils.Log;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;

/**
 * This class used to control all request and response.
 */
@SuppressLint("HandlerLeak")
public class CoreService extends Service {
    protected static final String TAG = "CoreService";

    private static ArrayList<Transaction> sPendingTransaction;

    private static ArrayList<UIRequest> sPendingUIRequest;

    private TransactionHandler mTransactionHandler;

    private ReceiverHandler mReceiverHandler;
    
    private UIRequestHandler mUIRequestHandler;

    private HandlerThread mTransactionThread;

    private HandlerThread mReceiverThread;

    private HandlerThread mUIRequestThread;

    private AlarmManager mAlarmManager;

    private static CoreService sCoreService = null;

    OnePieceSdk mOnePieceSdk;

    public static CoreService getCoreService() {
        return sCoreService;
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate()");
        sCoreService = this;
        // 创建两个线程，分别处理请求和服务器推送消息。
        mTransactionThread = new HandlerThread(TransactionHandler.class.getName(),
                Process.THREAD_PRIORITY_BACKGROUND);
        mReceiverThread = new HandlerThread(ReceiverHandler.class.getName(),
                Process.THREAD_PRIORITY_BACKGROUND);
        mUIRequestThread = new HandlerThread(UIRequestHandler.class.getName(),
                Process.THREAD_PRIORITY_BACKGROUND);

        if(sPendingUIRequest == null) {
            sPendingUIRequest = new ArrayList<UIRequest>();
        }
        // 启动工作线程
        mTransactionThread.start();
        mReceiverThread.start();
        mUIRequestThread.start();

        // 创建两个Handler，保证事件的处理按照一定的顺序
        mTransactionHandler = new TransactionHandler(this, mTransactionThread.getLooper());
        mReceiverHandler = new ReceiverHandler(mReceiverThread.getLooper());
        mUIRequestHandler = new UIRequestHandler(mUIRequestThread.getLooper());

        Log.d(TAG, "onCreate -> 初始化sdk，保证请求可以正常执行.\n 同时设置监听push消息的callback，保证push消息可以正常接收.");
        // TODO 判断网络
        // if (Utils.isNetworkAvailable(sCoreService)) {
        mOnePieceSdk = OnePieceSdkImplment.initSdk(sCoreService, mReceiverHandler);
        // }
        mAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 服务被系统收后会自启动，自动启动时，intent为空
        if (intent != null) {
            if (mOnePieceSdk != null) {
                // 如果非自启动，则继续执行pending的请求
                continuePendingTransaction();
                continuePendingUIRequest();
            } else {
                // TODO 清除缓存队列，直接回调异常
                // callbackPendingTransactionWithError(
                // Constant.ErrorCodes.ERROR_NETWORK_NOT_AVAILABLE,
                // getResources().getString(R.string.net_work_unavailable));
                sCoreService = null;
            }
        }
        return START_STICKY;
    }

    public static void requestTransaction(Context context, Transaction transaction) {
        Log.i(TAG, "requestTransaction getBabyInfo  sCoreService = " + sCoreService);
        if (sCoreService == null) {
            // 如果服务未启动，则将请求pending，等待服务请求后继续处理
            if (sPendingTransaction == null) {
                sPendingTransaction = new ArrayList<CoreService.Transaction>();
            }
            Intent intent = new Intent();
            intent.setClass(context, CoreService.class);
            context.startService(intent);
            sPendingTransaction.add(transaction);
        } else {
            Message msg = new Message();
            msg.obj = transaction;
            sCoreService.mTransactionHandler.sendMessage(msg);
        }
    }

    public static void requestFromUI(Context context, UIRequest uiRequest) {
        if (sCoreService == null) {
            if(sPendingUIRequest == null) {
                sPendingUIRequest = new ArrayList<UIRequest>();
            }
            sPendingUIRequest.add(uiRequest);
            Intent intent = new Intent();
            intent.setClass(context, CoreService.class);
            context.startService(intent);
        } else {
            Message msg = new Message();
            msg.obj = uiRequest;
            sCoreService.mUIRequestHandler.sendMessage(msg);
        }
    }

    public static class UIRequest {
        public int mWhat;
        public Object mObject;
        public UIResponse uiResponse;
    }

    public static class Transaction {
        public int what;

        public RequestBean object;

        public OnePieceRequestCallback callback;

    }

    class UIRequestHandler extends Handler {

        public UIRequestHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            UIRequest uiRequest= (UIRequest) msg.obj;
            Object retObject = null;
            switch (uiRequest.mWhat) {
                case UIRequestCode.ALARM:
                    // TODO: do alarm
                    break;
            }
            if (uiRequest.uiResponse != null) {
                uiRequest.uiResponse.onResponse(uiRequest.mWhat, retObject);
            }
        }
    }

    class ReceiverHandler extends Handler implements OnePieceHeadlineListenner {

        public ReceiverHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                default:
                    // TODO 由具体实现类处理接收到的通知消息
                    break;
            }
        }

        @Override
        public void onReceiveGroupHeadline(UserHeadline headline) {
            // TODO Auto-generated method stub

        }

    }

    private void continuePendingTransaction() {
        if (sPendingTransaction != null && sPendingTransaction.size() > 0) {
            for (Transaction transaction : sPendingTransaction) {
                requestTransaction(sCoreService, transaction);
            }
        }
    }

    private void continuePendingUIRequest() {
        if (sPendingUIRequest != null) {
            for (UIRequest request : sPendingUIRequest) {
                requestFromUI(sCoreService, request);
            }
        }
    }

    private void callbackPendingTransactionWithError(int errorCode, String errorMessage) {
        if (sPendingTransaction != null && sPendingTransaction.size() > 0) {
            for (Transaction transaction : sPendingTransaction) {
                transaction.callback.onRequestFailure(transaction.what, "errorCode = " + errorCode
                        + "; errorMessage = " + errorMessage, null);
            }
        }

    }
}
