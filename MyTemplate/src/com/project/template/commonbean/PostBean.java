package com.project.template.commonbean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.project.template.requestbean.RequestBean;
import com.project.template.utils.GsonUtil;

public class PostBean {
    @SerializedName("request") @Expose private RequestBean requestBean;
    @SerializedName("fileCount") @Expose private int fileCount;
    
    public PostBean(RequestBean requestBean, int fileCount) {
        super();
        this.requestBean = requestBean;
        this.fileCount = fileCount;
    }

    @Override
    public String toString() {
        return "PostBean [requestBean=" + requestBean + ", fileCount=" + fileCount + "]";
    }

    public String toGsonString() {
        return GsonUtil.instance().getJsonFromObject(this);
    }
}