package com.project.template.ui.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler.Callback;

/**
 * <ul>
 * <B>该Cache类处理以下内容</B>
 * <li>1. 用户个人信息(当前登陆账户的个人信息)
 * <li>2. 网络状态
 * <li>3. 频繁重复使用的资源(例如用户头像等)
 * <li>4 跨页面操作的内容()
 */
public class Cache {
    protected final String TAG = this.getClass().getSimpleName();

    private static Cache mCache = null;

    private Cache() {

    }

    public static Cache getCache() {
        synchronized (Cache.class) {
            if (null == mCache) {
                mCache = new Cache();
            }
        }
        return mCache;
    }

    private Callback callBack;

    private String mPhoneNumber;

    private boolean isNetWorkOpen;

    private boolean isInitNetWorkState = false;

    Bitmap mPhoto;

    public boolean isNetWorkOk(Context context) {
        if (!isInitNetWorkState) {
            // TODO 判断网络状态
            // netWorkIsOk = !Utils.hasNoNetwork(context);
            isInitNetWorkState = true;
        }
        return isNetWorkOpen;
    }

    public void setNetWorkIsOk(boolean isNetWorkOpen) {
        this.isNetWorkOpen = isNetWorkOpen;
    }

    public Callback getCallBack() {
        return callBack;
    }

    public void setCallBack(Callback callBack) {
        this.callBack = callBack;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String mPhoneNumber) {
        this.mPhoneNumber = mPhoneNumber;
    }

    public Bitmap getPhoto() {
        return Bitmap.createBitmap(mPhoto);
    }

    public void setmPhoto(Bitmap mPhoto) {
        this.mPhoto = mPhoto;
    }

}
