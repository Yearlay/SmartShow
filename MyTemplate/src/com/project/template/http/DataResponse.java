
package com.project.template.http;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.project.template.Constant;
import com.project.template.ErrorCode;
import com.project.template.responsebean.ErrorBean;
import com.project.template.utils.GsonUtil;
import com.project.template.utils.Log;

import android.text.TextUtils;

/**
 * <b>Description:</b> </br> 使用Gson对Json进行解析的单例。</br>
 * 主要的思想：使用泛型进行解析；然后将解析出来的数据结构存放在HashMap中往上返回。</br> 主要的函数：</br>
 * {@link DataResponse#parseJson(String, boolean, Class)} </br>
 * {@link DataResponse#parseDefaultResult(String)}
 * 
 * @author 叶蕾
 */
public class DataResponse {
    protected static final String TAG = DataResponse.class.getSimpleName();

    private static DataResponse sParseResponse;

    /** <b>Description:</b> </br> ParseResponse单例对象 */
    public static synchronized DataResponse getInstane() {
        if (sParseResponse == null) {
            sParseResponse = new DataResponse();
        }
        return sParseResponse;
    }

    /**
     * <b>Description:</b> </br> 解析服务器返回的结果
     * 
     * @param json 服务器返回的json串
     * @param isArray 是否返回数组结果
     * @param parseClass 返回结果对应的model类型
     * @return 带有返回数据结构类型对象的HashMap。
     */
    public HashMap<String, Object> parseJson(String json, Class<?> parseClass) {
        HashMap<String, Object> resultHashMap = null;
        try {
            if (TextUtils.isEmpty(json)) { // 后续不需要再判断json为空的情况。
                throw new JSONException("json is null!");
            }
            JSONObject responseJson = new JSONObject(json);
            String ValueOfResponse = responseJson.getString(Constant.KEY_RESULT_RESPONSE);
            Log.e(TAG, " -- parse response : " + ValueOfResponse);

            JSONObject obj = new JSONObject(ValueOfResponse);
            // 判断返回的json中的status: 判断“成功”或是“失败异常”。
            int resultStatus = obj.getInt(Constant.KEY_RESULT_STATUS);
            switch (resultStatus) {
                case Constant.RESULT_SUCCESS:
                    boolean isArray = (obj.getInt("arrayflag") == 1) ? true : false;
                    resultHashMap = (!isArray) ?
                            parseJsonObject(obj.getString(Constant.KEY_RESULT_CONTENT).toString(),
                                    parseClass, resultStatus) :
                            parseJsonArray(obj.getString(Constant.KEY_RESULT_CONTENT).toString(),
                                    parseClass, resultStatus);
                    break;

                case Constant.RESULT_FAILURE:
                    parseClass = ErrorBean.class;
                    resultHashMap = parseJsonObject(obj.getString(Constant.KEY_RESULT_CONTENT)
                            .toString(),
                            parseClass, resultStatus);
                    break;

                case Constant.RESULT_OVER_TIME:
                    break;

                default:
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            JudgeJsonError(resultHashMap);
        }
        return resultHashMap;
    }

    /**
     * <b>Description:</b> </br> 默认的返回：解析只返回成功或失敗类型的结果
     * 
     * @param json json字符串。
     * @return 成功失败的HashMap，默认的返回。
     */
    public HashMap<String, Object> parseDefaultResult(String json) {
        HashMap<String, Object> resultHashMap = null;
        int status = 0;
        try {
            JSONObject obj = new JSONObject(json);
            status = obj.getInt(Constant.KEY_RESULT_STATUS);
            resultHashMap = getResult(status, null);
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            JudgeJsonError(resultHashMap);
        }
        return resultHashMap;
    }

    /** <b>Description:</b> </br> 解析返回对象类型的结果 */
    private HashMap<String, Object> parseJsonObject(String json, Class<?> t, int resultStatus) {
        // 解析登陆返回的内容，将需要返回的信息封装到result中，回调给客户端
        return getResult(resultStatus, GsonUtil.instance().getObjectFromJson(json, t));
    }

    /** <b>Description:</b> </br> 解析返回数组类型的结果 */
    private HashMap<String, Object> parseJsonArray(String json, Class<?> parseClass,
            int resultStatus) {
        HashMap<String, Object> resultHashMap = null;
        ArrayList<Object> list = new ArrayList<Object>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            int length = jsonArray.length();
            for (int i = 0; i < length; i++) {
                list.add(GsonUtil.instance().getObjectFromJson(jsonArray.get(i).toString(),
                        parseClass));
            }
            resultHashMap = getResult(resultStatus, list);
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            JudgeJsonError(resultHashMap);
        }
        return resultHashMap;
    }

    /** <b>Description:</b> </br> 判断Json的解析是否错误。 */
    private HashMap<String, Object> JudgeJsonError(HashMap<String, Object> resultHashMap) {
        if (resultHashMap == null) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put(Constant.KEY_ERROR_CODE, ErrorCode.PARSE_JSON_ERROR_ID);
            map.put("content", ErrorCode.PARSE_JSON_ERROR_MESSAGE);
            resultHashMap = getResult(Constant.RESULT_FAILURE, map);
        }
        return resultHashMap;
    }

    /**
     * <b>Description:</b> </br> 封装返回结果HashMap.
     * 
     * @param status 需要填充到HashMap中的key-"status"中的数据类型(int对象)
     * @param data 需要填充到HashMap中的key-"content"中的数据(Object对象)。
     * @return 填充之后的HashMap的对象。
     */
    private HashMap<String, Object> getResult(int status, Object data) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put(Constant.KEY_RESULT_STATUS, status);
        result.put(Constant.KEY_RESULT_CONTENT, data);
        return result;
    }
}
