package com.project.template.menu;

import android.graphics.drawable.Drawable;

/**
 * Created by frank on 2016/7/31.
 */

public class MenuItem {
    private Drawable mDrable;
    private String mDec;
    private int mTag;
    private int mGroupNum;
    private int mSubNum;

    public MenuItem(Drawable drawable, String dec, int tag, int group) {
        mDrable = drawable;
        mDec = dec;
        mTag = tag;
        mGroupNum = group;
        mSubNum = -1;
    }

    public MenuItem(Drawable drawable, String dec, int tag, int group, int sub) {
        mDrable = drawable;
        mDec = dec;
        mTag = tag;
        mGroupNum = group;
        mSubNum = sub;
    }

    public int getmGroupNum() {
        return mGroupNum;
    }

    public int getSubNum() {
        return mSubNum;
    }

    public int getTag() {
        return mTag;
    }

    public Drawable getmDrable() {
        return mDrable;
    }

    public String getmDec() {
        return mDec;
    }
}
