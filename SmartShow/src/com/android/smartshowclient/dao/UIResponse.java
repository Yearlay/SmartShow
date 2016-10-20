package com.android.smartshowclient.dao;

import java.util.ArrayList;

public class UIResponse {
    private ArrayList<EventListener> mListener;

    public UIResponse() {
        if(mListener == null) {
            mListener = new ArrayList<EventListener>();
        }
    }

    public void setListener(EventListener listener) {
        if (mListener == null) {
            mListener = new ArrayList<EventListener>();
        }
        mListener.add(listener);
    }

    public void clearAllListener() {
        if (mListener != null) {
            mListener.clear();
        }
    }

    public void onResponse(int msgType, Object object) {
        for(EventListener listener: mListener) {
            listener.onEventCallBack(msgType, object);
        }
    }

    public interface EventListener {
        public void onEventCallBack(int MsgType, Object object);
    }
}