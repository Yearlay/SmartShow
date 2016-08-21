
package com.project.template.responsebean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * “获得验证码”接口返回的ResponseBean数据类型。
 * @author 叶蕾
 *
 */
public class GetVerifyCodeResponseBean extends ResponseBean {

    @SerializedName("verifycode")
    @Expose
    private String verifycode;

    @Override
    public String toString() {
        return "GetVerifyCodeResponseBean [verifycode=" + verifycode + "]";
    }

    public String getVerifycode() {
        return verifycode;
    }

    public void setVerifycode(String verifycode) {
        this.verifycode = verifycode;
    }

}
