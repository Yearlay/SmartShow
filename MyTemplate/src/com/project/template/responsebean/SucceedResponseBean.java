package com.project.template.responsebean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * <b>[SucceedResponseBean]</b> 数据更新成功与否的返回ResponseBean <br>
 * # <b>succeed</b> : 数据是否更新成功。
 * @author 叶蕾
 *
 */
public class SucceedResponseBean extends ResponseBean {
    @SerializedName("succeed")
    @Expose
    private int succeed;

    public SucceedResponseBean(int succeed) {
        super();
        this.succeed = succeed;
    }

    @Override
    public String toString() {
        return "SucceedResponseBean [succeed=" + succeed + "]";
    }

    public int getSucceed() {
        return succeed;
    }

    public void setSucceed(int succeed) {
        this.succeed = succeed;
    }
    
}
