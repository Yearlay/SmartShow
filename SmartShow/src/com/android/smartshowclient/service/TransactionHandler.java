package com.android.smartshowclient.service;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.android.smartshowclient.service.CoreService.Transaction;
import com.project.template.Constant;
import com.project.template.utils.Log;

class TransactionHandler extends Handler {

    /**
     * 
     */
    private final CoreService TransactionHandler;

    public TransactionHandler(CoreService coreService, Looper looper) {
        super(looper);
        TransactionHandler = coreService;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        Transaction transaction = (Transaction) msg.obj;
        // 根据参数what不同，处理不同的请求事件
        if (TransactionHandler.mOnePieceSdk == null) {
            Log.e(CoreService.TAG, " ***** Error:  mOnePieceSdk == null ");
            return;
        }

        switch (transaction.what) {
            case Constant.AccountRequest.REQUEST_GET_VERIFY_CODE:
                TransactionHandler.mOnePieceSdk.getVerifyCode(transaction.object, transaction.callback);
                break;
            // 处理Account注册更新消息
            case Constant.AccountRequest.REQUEST_REGISTER:
                TransactionHandler.mOnePieceSdk.registerUser(transaction.object, transaction.callback);
                break;

            case Constant.AccountRequest.REQUEST_LOGIN:
                TransactionHandler.mOnePieceSdk.login(transaction.object, transaction.callback);
                break;
            case Constant.AccountRequest.REQUEST_UPDATE_PASSWORD:
                TransactionHandler.mOnePieceSdk.updatePassword(transaction.object, transaction.callback);
                break;

            case Constant.AccountRequest.REQUEST_RESET_PASSWORD:
                TransactionHandler.mOnePieceSdk.resetPassword(transaction.object, transaction.callback);
                break;

            case Constant.AccountRequest.REQUEST_UPDATE_PERSONAL_INFO:
                TransactionHandler.mOnePieceSdk.updatePersonalInfo(transaction.object, transaction.callback);
                break;

            default:
                break;
        }

    }
}