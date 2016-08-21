
package com.project.template.http;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import android.content.Context;
import android.text.TextUtils;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.project.template.Config;
import com.project.template.ErrorCode;
import com.project.template.commonbean.PostBean;
import com.project.template.utils.Log;

public class OnePieceAsyncHttpClient extends AsyncHttpClient {
    private static final String TAG = "OnePieceAsyncHttpClient";
    private static final String CONTENT_TYPE = Config.JSON_MIME + ";"
            + Config.JSON_CHARSET;

    class OnePieceAsyncHttpResponseHandler extends AsyncHttpResponseHandler {
        private static final int CODE_SUCESS = 200;

        DataRequest request;

        public OnePieceAsyncHttpResponseHandler(DataRequest request) {
            super();
            this.request = request;
        }

        @Override
        protected void handleFailureMessage(Throwable e,
                String responseBody) {
            super.handleFailureMessage(e, responseBody);
            request.handlerError(e, responseBody);
        }

        @Override
        protected void handleSuccessMessage(int statusCode, Header[] headers, String responseBody) {
            super.handleSuccessMessage(statusCode, headers, responseBody);
            if (statusCode == CODE_SUCESS) {
                if (!TextUtils.isEmpty(responseBody)) {
                    request.handlerResult(responseBody);
                } else {
                    request.handlerError(new Throwable(), ErrorCode.JSON_IS_EMPTY_MESSAGE);
                }
            } else {
                request.handlerError(new Throwable(), ErrorCode.RESPONSE_STATUS_EXCEPTION_MESSAGE);
            }
        }
    }

    /** HTTP Get 请求 */
    public void get(DataRequest request) {
        Log.i(TAG, "get -> DataRequest = " + request);
        RequestParams params = new RequestParams();
        params.put("request", request.requestBean.toGsonString());
        get(request.url, params, new OnePieceAsyncHttpResponseHandler(request));
    }

    public void post(Context context, DataRequest request) {
        Log.i(TAG, "doPostRequestWithJson -> request = " + request);
        int fileListSize = request.requestBean.getFileListSize();
        if (fileListSize == 0) {
            try {
                PostBean postBean = new PostBean(request.requestBean, fileListSize);
                Log.d(TAG, postBean.toGsonString());
                HttpEntity entity = new StringEntity(postBean.toGsonString(),
                        Config.JSON_CHARSET);
                post(context, request.url, entity, CONTENT_TYPE,
                        new OnePieceAsyncHttpResponseHandler(request));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else if (fileListSize > 0) {
            postForUploadFile(context, request);
        }
    }

    /** 带参数上传文件 */
    public void postForUploadFile(Context context, final DataRequest request) {
        Log.i(TAG, "doPostRequestWithParamsForFile -> request = " + request);
        // 用来存放参数的容器，类似AsyncHttpClient 封装的RequestParams
        MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
        // 封装json协议（视具体接口而定）
        try {
            PostBean postBean = new PostBean(request.requestBean,
                    request.requestBean.getFileListSize());
            Log.d(TAG, postBean.toGsonString());
            StringBody requestBody = new StringBody(postBean.toGsonString(),
                    Charset.forName(Config.JSON_CHARSET));
            entity.addPart(Config.JSON_BODY_KEY, requestBody);
        } catch (UnsupportedEncodingException e) {
            request.handlerError(e, ErrorCode.ENCODE_ERROR_MESSAGE);
        }
        // 将 "file":{stringBody} 添加到entity里面。
        if (request.requestBean.getFileList() != null) {
            for (int i = 0; i < request.requestBean.getFileList().size(); i++) {
                String partKeyName = Config.JSON_FILE + i;
                entity.addPart(partKeyName, 
                        new FileBody(request.requestBean.getFileList().get(i)));
            }
        }
        post(context, request.url, entity, null,
                new OnePieceAsyncHttpResponseHandler(request));
    }
}
