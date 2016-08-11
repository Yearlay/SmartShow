package com.project.template.ui.utils;


import com.project.template.utils.Log;

import android.os.Handler;
import android.os.Message;

public class TemplateHandler extends Handler {

    protected final String TAG = this.getClass().getSimpleName();

    private HandlerInterface callback;

    public TemplateHandler(HandlerInterface callback) {
        if (callback == null) {
            throw new IllegalArgumentException(
                    "HandlerInterface can not be null!");
        }
        this.callback = callback;
    }

    @Override
    public void handleMessage(Message msg) {
        if (msg == null) {
            return;
        }
        // L.i(TAG,
        // "msg code = handleMessage " + Integer.toHexString(msg.what));
        switch (msg.what) {
        // common handle
            case CmdCode.MSG_COMMON_HANDLE:
                callback.handleCommonMsg(msg);
                break;
            default:
                dispatchMsg(msg);
                break;
        }
    }

    public boolean dispatchMsg(Message msg) {
        if (msg == null) {
            Log.i(TAG, "bad code msg is null");
            return false;
        }
        if (CmdCode.isUICode(msg.what)) {
            // L.i(TAG, "ui code" + Integer.toHexString(msg.what));
            return callback.handleUIMessage(msg);
        } else if (CmdCode.isNotifyCode(msg.what)) {
            // L.i(TAG, "notify code" + Integer.toHexString(msg.what));
            return callback.handleNotifyMessage(msg);
        } else {
            return callback.handleMessage(msg);
        }
    }

}
