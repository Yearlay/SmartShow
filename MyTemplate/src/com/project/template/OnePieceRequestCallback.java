package com.project.template;

import java.util.Map;

public interface OnePieceRequestCallback {

    public abstract void onRequestSuccess(int event, String message,
            Map<String, Object> result);

    public abstract void onRequestFailure(int event, String message,
            Throwable th);
}
