
package com.project.template;

import com.project.template.Constant.AccountRequest;

import android.text.TextUtils;
import android.util.SparseArray;

/**
 * <b>Description:</b> </br> 定义SDK的一些恒定的常量，配置信息。</br> 重要的常量:</br>
 * {@link Config#MeeloApiUrls} </br> {@link Config#getUrl(int)}
 * 
 * @author 叶蕾
 */
public class Config {
    static final String TAG = Config.class.getSimpleName();

    /** WEB_URL值为"http://192.168.0.101:8080/SmartShow" */
    public static final String WEB_URL = "http://192.168.0.101:8080/SmartShow";

    /** API适用的客户端类型 */
    public static final String CLIENT_RESOURCE = "android";

    /** JSON对象的MIME类型 */
    public static final String JSON_MIME = "application/json";

    /** JSON对象使用的字符编码 */
    public static final String JSON_CHARSET = "utf-8";

    public static final String JSON_BODY_KEY = "stringBody";
    public static final String JSON_FILE = "file";

    /** API到url的映射表 */
    static SparseArray<String> MeeloApiUrls;

    /**
     * <b>Description:</b> </br> 通过key获得接口方法的URL。
     * 
     * @param keyValue key值，存放在{@link Config#MeeloApiUrls}中。
     * @return ({@link Config#WEB_URL}+接口方法的URL)
     * @throws Exception 当接口方法为空时，抛出异常。
     */
    public static String getUrl(int keyValue) throws Exception {
        String methodUrl = MeeloApiUrls.get(keyValue);
        if (TextUtils.isEmpty(methodUrl)) {
            throw new Exception();
        }
        return WEB_URL + methodUrl;
    }

    static {
        MeeloApiUrls = new SparseArray<String>();
        // ---------账户管理接口-------
        MeeloApiUrls.put(AccountRequest.REQUEST_GET_VERIFY_CODE,
                "/account/getverifycode.action");
        MeeloApiUrls.put(AccountRequest.REQUEST_REGISTER,
                "/account/register.action");
        MeeloApiUrls.put(AccountRequest.REQUEST_LOGIN,
                "/account/login.action");
        MeeloApiUrls.put(AccountRequest.REQUEST_UPDATE_PASSWORD,
                "/account/updatepassword.action");
        MeeloApiUrls.put(AccountRequest.REQUEST_RESET_PASSWORD,
                "/account/resetpassword.action");
        MeeloApiUrls.put(AccountRequest.REQUEST_UPDATE_PERSONAL_INFO,
                "/account/updatepersonalinfo.action");
    }
}
