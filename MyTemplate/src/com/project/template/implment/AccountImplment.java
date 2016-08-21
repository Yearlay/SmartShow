package com.project.template.implment;

import com.project.template.OnePieceRequestCallback;
import com.project.template.requestbean.RequestBean;


public interface AccountImplment {
    /**
     * 1. 获取验证码接口</br>
     * {@link GetVerifyCodeRequestBean} and {@link GetVerifyCodeResponseBean}
     */
    public void getVerifyCode(RequestBean requestBean, OnePieceRequestCallback callback);
    /**
     * 2. 注册的接口 </br>
     * {@link RegisterRequestBean} and {@link RegisterResponseBean}
     */
    public void registerUser(RequestBean requestBean, OnePieceRequestCallback callback);
    /**
     * 3. 用户登陆的接口 </br>
     * {@link LoginRequestBean} and {@link LoginResponseBean}
     */
    public void login(RequestBean requestBean, OnePieceRequestCallback callback);
    /**
     * 4. 更新密码的接口 </br>
     * {@link UpdatePasswordRequestBean} and {@link SucceedResponseBean}
     */
    public void updatePassword(RequestBean requestBean, OnePieceRequestCallback callback);
    /**
     * 5. 重置密码的接口 </br>
     * {@link ResetPasswordRequestBean} and {@link SucceedResponseBean}
     */
    public void resetPassword(RequestBean requestBean, OnePieceRequestCallback callback);
    /**
     * 6. 更新个人信息的接口 </br>
     * {@link PersonalInfoBean} and {@link SucceedResponseBean}
     */
    public void updatePersonalInfo(RequestBean requestBean, OnePieceRequestCallback callback);
}
