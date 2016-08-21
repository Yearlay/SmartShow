package com.project.template;

import com.project.template.commonbean.UserHeadline;

public interface OnePieceHeadlineListenner {

    /**
     * 接收到群组通知
     * 
     * @see com.xianchen.sdk.bean.UserHeadline
     * @param headline
     */
    public void onReceiveGroupHeadline(UserHeadline headline);
}
