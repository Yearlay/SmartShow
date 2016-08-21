package com.project.template.responsebean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * 输出举例：<br>
 * {<br>
 * "id": 1013,<br>
 * "usercode": "2000823651",<br>
 * "password": "123456",<br>
 * "registerTime": "2014-12-30 19:13:23",<br>
 * " type ": "1"<br>
 * }<br>
 * 
 * @author 叶蕾
 *
 */
public class RegisterResponseBean extends ResponseBean {
    /** [account]用户ID */
    @SerializedName("uuid") @Expose private String uuid;
    /** [account]账号(暂时限定为手机号码) */
    @SerializedName("usercode") @Expose private String userCode;
    /** [account]用户密码 */
    @SerializedName("password") @Expose private String password;
    /** [account]用户类型 1：医生  0：病人 */
    @SerializedName("type") @Expose private int type;

    @Override
    public String toString() {
        return "RegisterResponseBean [uuid=" + uuid + ", userCode=" + userCode + ", password="
                + password + ", type=" + type + "]";
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
