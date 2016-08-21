
package com.project.template;


/**
 * 请求类型:<br/>
 * 共有接口1000开始递增<br/>
 * 医生接口2000开始递增<br/>
 * 病人接口3000开始递增<br/>
 * <br>
 * 请查看与接口对应的RequestBean和ResponseBean<br>
 * 例如: 1. 注册 {@link RegisterRequestBean} and {@link RegisterResponseBean}
 */
public interface Constant {

    // 返回结果类型
    public static final int RESULT_FAILURE = 0;
    public static final int RESULT_SUCCESS = 1;
    public static final int RESULT_OVER_TIME = 2;

    public static final String KEY_RESULT_RESPONSE = "response";
    public static final String KEY_RESULT_STATUS = "succeed";
    public static final String KEY_RESULT_CONTENT = "content";

    public static final String KEY_ERROR_CODE = "code";

    /** 定义共有接口请求类型 */
    public interface PublicRequest {
        // 从 1000开始递增。
    }

    /*************** 通知类型 ********************/
    public interface Receive {
        // 从 2000开始递增。
        public int RECEIVE_GROUP_HEADLINE = 2000;
    }

    public interface AccountRequest extends PublicRequest {
        // 从 3000开始递增。
        /**
         * 1. 获取验证码接口</br> {@link GetVerifyCodeRequestBean} and
         * {@link GetVerifyCodeResponseBean}
         */
        public int REQUEST_GET_VERIFY_CODE = 3000;
        /**
         * 2. 注册的接口 </br> {@link RegisterRequestBean} and
         * {@link RegisterResponseBean}
         */
        public int REQUEST_REGISTER = 3001;
        /**
         * 3. 用户登陆的接口 </br> {@link LoginRequestBean} and
         * {@link LoginResponseBean}
         */
        public int REQUEST_LOGIN = 3002;
        /**
         * 4. 更新密码的接口 </br> {@link UpdatePasswordRequestBean} and
         * {@link SucceedResponseBean}
         */
        public int REQUEST_UPDATE_PASSWORD = 3003;
        /**
         * 5. 重置密码的接口 </br> {@link ResetPasswordRequestBean} and
         * {@link SucceedResponseBean}
         */
        public int REQUEST_RESET_PASSWORD = 3004;
        /**
         * 6. 更新个人信息的接口 </br> {@link PersonalInfoBean} and
         * {@link SucceedResponseBean}
         */
        public int REQUEST_UPDATE_PERSONAL_INFO = 3005;
    }
}
