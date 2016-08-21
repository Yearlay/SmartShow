package com.project.template.responsebean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * <b>[ErrorBean]</b> 错误的信息的Bean；<br>
 * 是{@link ResponseBean}的子类。<br>
 * 当有错误信息返回的时候，就是使用这个类进行解析。<br>
 * # <b>mCode</b>: 错误码 <br>
 * # <b>mContent</b>: 错误提示的内容<br>
 * @author 叶蕾
 *
 */
public class ErrorBean extends ResponseBean {

    @SerializedName("code") @Expose int mCode;
    @SerializedName("content") @Expose String mContent;
    
    @Override
    public String toString() {
        return "ErrorBean [mCode=" + mCode + ", mContents=" + mContent + "]";
    }
    
}
