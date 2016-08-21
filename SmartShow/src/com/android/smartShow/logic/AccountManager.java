
package com.android.smartShow.logic;

import java.util.Map;

import android.content.Context;

import com.android.smartShow.service.CoreService;
import com.android.smartShow.service.CoreService.Transaction;
import com.onepiece.heartguard.logic.callback.AbstractRequestCallback;
import com.onepiece.heartguard.logic.callback.RequestCallback;
import com.project.template.Constant;
import com.project.template.requestbean.RequestBean;
import com.project.template.utils.Log;

public class AccountManager extends AbstractManager {

    public AccountManager(Context context) {
        super(context);
    }

    /**
     * 获取验证码
     * 
     * @param requestBean 实际对象为{@link GetVerifyCodeRequestBean}
     * @param callback 当前统一为{@link BaseResponseManager} </br>
     * @see 备注：在callback中处理实际的返回数据类型{@link GetVerifyCodeResponseBean}
     */
    public void getVerifyCode(RequestBean requestBean, RequestCallback callback) {
        Transaction transaction = new Transaction();
        transaction.what = Constant.AccountRequest.REQUEST_GET_VERIFY_CODE;
        transaction.callback = new AccountCallback(mContext, transaction.what, callback);
        transaction.object = requestBean;

        CoreService.requestTransaction(mContext, transaction);
    }

    /**
     * 用户注册
     * 
     * @param requestBean 实际对象为{@link RegisterRequestBean}
     * @param callback 当前统一为{@link BaseResponseManager} </br>
     * @see 备注：在callback中处理实际的返回数据类型{@link RegisterResponseBean}
     */
    public void register(RequestBean requestBean, RequestCallback callback) {
        Transaction transaction = new Transaction();
        transaction.what = Constant.AccountRequest.REQUEST_REGISTER;
        transaction.callback = new AccountCallback(mContext, transaction.what, callback);
        transaction.object = requestBean;

        CoreService.requestTransaction(mContext, transaction);
    }

    // 用户登录（医生、病人）
    public void login(int type, RequestBean requestBean, RequestCallback callback) {
        Transaction transaction = new Transaction();
        transaction.what = (type == RequestBean.DOCTOR_TYPE) ? Constant.AccountRequest.REQUEST_LOGIN
                :
                Constant.AccountRequest.REQUEST_LOGIN;
        transaction.callback = new AccountCallback(mContext, transaction.what, callback);
        transaction.object = requestBean;

        CoreService.requestTransaction(mContext, transaction);
    }
    
    /**
     * 更新账户密码
     * 
     * @param requestBean 实际对象为{@link UpdatePasswordRequestBean}
     * @param callback 当前统一为{@link BaseResponseManager} </br>
     * @see 备注：在callback中处理实际的返回数据类型{@link SucceedResponseBean}
     */
    public void updatePassword(RequestBean requestBean, RequestCallback callback) {
        Transaction transaction = new Transaction();
        transaction.what = Constant.AccountRequest.REQUEST_UPDATE_PASSWORD;
        transaction.callback = new AccountCallback(mContext, transaction.what, callback);
        transaction.object = requestBean;

        CoreService.requestTransaction(mContext, transaction);
    }
    
    /**
     * 重置账户密码
     * 
     * @param requestBean 实际对象为{@link ResetPasswordRequestBean}
     * @param callback 当前统一为{@link BaseResponseManager} </br>
     * @see 备注：在callback中处理实际的返回数据类型{@link SucceedResponseBean}
     */
    public void resetPassword(RequestBean requestBean, RequestCallback callback) {
        Transaction transaction = new Transaction();
        transaction.what = Constant.AccountRequest.REQUEST_RESET_PASSWORD;
        transaction.callback = new AccountCallback(mContext, transaction.what, callback);
        transaction.object = requestBean;

        CoreService.requestTransaction(mContext, transaction);
    }
    
    /**
     * 更新账户信息
     * 
     * @param requestBean 实际对象为{@link PersonalInfoBean}
     * @param callback 当前统一为{@link BaseResponseManager} </br>
     * @see 备注：在callback中处理实际的返回数据类型{@link SucceedResponseBean}
     */
    public void updatePersonalInfo(RequestBean requestBean, RequestCallback callback) {
        Transaction transaction = new Transaction();
        transaction.what = Constant.AccountRequest.REQUEST_UPDATE_PERSONAL_INFO;
        transaction.callback = new AccountCallback(mContext, transaction.what, callback);
        transaction.object = requestBean;

        CoreService.requestTransaction(mContext, transaction);
    }

    public void uploadFile(RequestBean requestBean, RequestCallback callback) {
        // TODO

    }

    // 在manager中实现自定义的callback
    class AccountCallback extends AbstractRequestCallback {

        public AccountCallback(Context context, int event, RequestCallback callback) {
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
