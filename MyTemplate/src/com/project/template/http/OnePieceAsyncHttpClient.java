
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
        HttpEntity entity = request.getEntity();
        if (entity != null) {
            post(context, request.url, entity, CONTENT_TYPE,
                    new OnePieceAsyncHttpResponseHandler(request));
        }
    }
}
