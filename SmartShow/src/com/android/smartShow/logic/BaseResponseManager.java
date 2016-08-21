
package com.android.smartShow.logic;

import java.util.Map;

import com.project.template.Constant;
import com.project.template.logic.callback.RequestCallback;
import com.project.template.responsebean.GetVerifyCodeResponseBean;
import com.project.template.responsebean.LoginResponseBean;
import com.project.template.responsebean.RegisterResponseBean;
import com.project.template.responsebean.ResponseBean;
import com.project.template.responsebean.SucceedResponseBean;

import android.util.Log;

public class BaseResponseManager implements RequestCallback {

    public String TAG = "BaseResponseManager";
    
    public BaseResponseManager() {
        super();
    }

    @Override
    public void onResult(int event, boolean isok, Map<String, Object> result) {
        Log.i(TAG, "onResult --> isok = " + isok + "result = " + result);
        parseResponse(isok, result, event);
    }

    // 处理call back信息
    private void parseResponse(boolean isok, Map<String, Object> result, int event) {
        Log.d(TAG, "onLoginResult: isok = " + isok);

        if (isok && result != null) {
            int status = (Integer) result.get(Constant.KEY_RESULT_STATUS);
            Object obj = result.get(Constant.KEY_RESULT_CONTENT);

            if (status == Constant.RESULT_SUCCESS) {
                Log.d(TAG, " obj : " + obj);
                changeToResponseBean(event, obj);
            } else {
                Log.e(TAG, "Exception --> Service requested info is: "
                        + obj.toString());
            }
        } else {
            Log.e(TAG, "Exception --> info :" +
                    "isOk: " + isok + ", result: " + result);
        }
    }

    private void changeToResponseBean(int event, Object obj) {
        ResponseBean responseBean = null;
        switch (event) {
            case Constant.AccountRequest.REQUEST_GET_VERIFY_CODE:
                responseBean = (GetVerifyCodeResponseBean) obj;
                break;

            case Constant.AccountRequest.REQUEST_REGISTER:
                responseBean = (RegisterResponseBean) obj;
                break;

            case Constant.AccountRequest.REQUEST_LOGIN:
                responseBean = (LoginResponseBean) obj;
                break;

            case Constant.AccountRequest.REQUEST_UPDATE_PASSWORD:
                responseBean = (SucceedResponseBean) obj;
                break;

            case Constant.AccountRequest.REQUEST_RESET_PASSWORD:
                responseBean = (SucceedResponseBean) obj;
                break;

            case Constant.AccountRequest.REQUEST_UPDATE_PERSONAL_INFO:
                responseBean = (SucceedResponseBean) obj;
                break;
                
            default:
                break;
        }
        if (responseBean != null) {
            Log.d(TAG, "responseBean : " + responseBean.getClass().getName());
        }
        Log.d(TAG, "responseBean : " + responseBean);
    }
}
