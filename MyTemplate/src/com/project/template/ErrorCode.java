package com.project.template;

public interface ErrorCode {
    
    // 解析Json异常(-1, "服务器返回Json解析异常")
    public int PARSE_JSON_ERROR_ID = -1;
    public String PARSE_JSON_ERROR_MESSAGE = "服务器返回Json解析异常";
    
    // 返回json结果为空(-2, "返回json结果为空");
    public int JSON_IS_EMPTY_ID = -2;
    public String JSON_IS_EMPTY_MESSAGE = "返回json结果为空";
    public String ENCODE_ERROR_MESSAGE = "错误的编码异常";
    
    // 返回状态异常(-3, "返回状态异常");
    public int RESPONSE_STATUS_EXCEPTION_ID = -3;
    public String RESPONSE_STATUS_EXCEPTION_MESSAGE = "返回状态异常";
    
    public int HTTP_CONNECT_EXCEPTION_ID = -4;
    public String HTTP_CONNECT_EXCEPTION_MESSAGE = "HTTP连接10s超时，请检查网络情况";
    
}
