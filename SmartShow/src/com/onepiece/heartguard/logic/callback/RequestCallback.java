package com.onepiece.heartguard.logic.callback;

import java.util.Map;

public interface RequestCallback {

    public void onResult(int event, boolean isok, Map<String, Object> result);

}
