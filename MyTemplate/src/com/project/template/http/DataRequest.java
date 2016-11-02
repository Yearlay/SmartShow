
package com.project.template.http;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import com.project.template.Constant.AccountRequest;
import com.project.template.Config;
import com.project.template.ErrorCode;
import com.project.template.OnePieceRequestCallback;
import com.project.template.commonbean.PostBean;
import com.project.template.implment.OnePieceSdkImplment;
import com.project.template.requestbean.RequestBean;
import com.project.template.responsebean.GetVerifyCodeResponseBean;
import com.project.template.responsebean.LoginResponseBean;
import com.project.template.responsebean.RegisterResponseBean;
import com.project.template.responsebean.SucceedResponseBean;

import android.util.Log;

public class DataRequest implements
        AccountRequest {

    private static final String TAG = DataRequest.class.getSimpleName();

    /** 请求的唯一id */
    public String id;

    /** 请求的事件 */
    public int event;

    /** 请求事件的监听器 */
    public OnePieceRequestCallback callback;

    /** 请求的数据对象 */
    public RequestBean requestBean;

    /** 请求事件的网络地址 */
    public String url;

    /**
     * 处理请求的正常结果
     * 
     * @param json 服务器返回的json字符串
     */
    protected void handlerResult(String json) {
        // 预留解密处理
        // json = Escape.unescape(json);

        Log.i(TAG, " handlerResult event = " + event + " -> url = " + url
                + "-> requestBean = " + requestBean);
        Log.i(TAG, " 返回结果json-> json = " + json);

        // 返回JSON对应的对象类型
        Class<?> resultClass = setResultClass();

        // 返回结果以map形式callback
        HashMap<String, Object> result = null;
        if (resultClass != null) {
            result = DataResponse.getInstane().parseJson(json, resultClass);
        } else {
            // 默认类型的接口只需返回成功或失败
            result = DataResponse.getInstane().parseDefaultResult(json);
        }

        Log.i(TAG, "onRequestSuccess event = " + event + ", result = " + result);
        callback.onRequestSuccess(event, "请求成功", result);
        removeRequestFromQueue();
    }

    private Class<?> setResultClass() {
        Class<?> resultClass = null;
        switch (event) {
            case REQUEST_GET_VERIFY_CODE:
                resultClass = GetVerifyCodeResponseBean.class;
                break;

            case REQUEST_REGISTER:
                resultClass = RegisterResponseBean.class;
                break;

            case REQUEST_LOGIN:
                resultClass = LoginResponseBean.class;
                break;

            case REQUEST_UPDATE_PASSWORD:
                resultClass = SucceedResponseBean.class;
                break;

            case REQUEST_RESET_PASSWORD:
                resultClass = SucceedResponseBean.class;
                break;

            case REQUEST_UPDATE_PERSONAL_INFO:
                resultClass = SucceedResponseBean.class;
                break;


            default:
                break;
        }
        return resultClass;
    }

    /**
     * 处理请求的异常结果
     * 
     * @param e 异常类型
     * @param message 异常提醒消息
     */
    protected void handlerError(Throwable e, String message) {
        callback.onRequestFailure(event, message, e);
        removeRequestFromQueue();
    };

    /**
     * 事件处理完成，从队列中删除
     */
    private void removeRequestFromQueue() {
        // 请求结束，从请求队列中清除
        OnePieceSdkImplment.removeRequestFromQueue(id);
    }
    
    public HttpEntity getEntity() {
        HttpEntity entity = null;
        int fileListSize = requestBean.getFileListSize();
        try {
            if (fileListSize == 0) {
                PostBean postBean = new PostBean(requestBean, requestBean.getFileListSize());
                entity = new StringEntity(postBean.toGsonString(), Config.JSON_CHARSET);
            } else if (fileListSize > 0) {
                /** 带参数上传文件 */
                entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                PostBean postBean = new PostBean(requestBean,fileListSize);
                Log.d(TAG, postBean.toGsonString());
                StringBody requestBody = new StringBody(postBean.toGsonString(),
                        Charset.forName(Config.JSON_CHARSET));
                ((MultipartEntity) entity).addPart(Config.JSON_BODY_KEY, requestBody);
                // 将 "file":{stringBody} 添加到entity里面。
                if (requestBean.getFileList() != null) {
                    for (int i = 0; i < requestBean.getFileList().size(); i++) {
                        String partKeyName = Config.JSON_FILE + i;
                        ((MultipartEntity) entity).addPart(partKeyName, 
                                new FileBody(requestBean.getFileList().get(i)));
                    }
                }
            }
        } catch (UnsupportedEncodingException e) {
            handlerError(e, ErrorCode.ENCODE_ERROR_MESSAGE);
        }
        return entity;
    }

    @Override
    public String toString() {
        return "DataRequest [id=" + id + ", event=" + event + ", callback=" + callback
                + ", requestBean=" + requestBean + ", url=" + url + "]";
    }

}
