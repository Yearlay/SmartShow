package com.project.template.ui.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.project.template.utils.Log;

import android.os.Handler;
import android.os.Message;

public class HandlerCache implements Handler.Callback {
    protected final String TAG = this.getClass().getSimpleName();

    public static List<Handler> sUIHandlers = new ArrayList<Handler>();

    public HandlerCache() {
        Cache.getCache().setCallBack(this);
    }

    public boolean registerHandler(Handler handler) {
        boolean result;
        synchronized (sUIHandlers) {
            result = sUIHandlers.add(handler);
        }
        Cache.getCache().setCallBack(this);
        return result;
    }

    public boolean unRegisterHandler(Handler handler) {
        if (sUIHandlers == null || sUIHandlers.size() == 0) {
            return true;
        }
        boolean result;
        synchronized (sUIHandlers) {
            result = sUIHandlers.remove(handler);
        }
        Cache.getCache().setCallBack(this);
        return result;
    }

    @Override
    public boolean handleMessage(Message message) {
        if (null == message) {
            Log.e(TAG, "bad msg : message is null.");
            return false;
        }
        Log.d(TAG, "handleMessage code = " + Integer.toHexString(message.what));
        if (null == sUIHandlers) {
            Log.e(TAG, "sUIHandlers is null.");
            return false;
        }

        synchronized (sUIHandlers) {
            Iterator<Handler> iterator = sUIHandlers.iterator();
            while (iterator.hasNext()) {
                Handler handler = iterator.next();
                if (handler == null) {
                    continue;
                }
                handler.sendMessage(Message.obtain(message));
            }
        }
        return false;
    }

}
