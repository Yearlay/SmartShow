package com.android.smartShow.actionbar;

import com.android.smartShow.activity.BaseActivity;

import android.app.ActionBar;

public abstract class BaseActionBar {
    
    public BaseActivity mActivity;
    public ActionBar mActionBar;
    
    public BaseActionBar(BaseActivity activity) {
        super();
        this.mActivity = activity;
        mActionBar = mActivity.getActionBar();
    }

    public abstract void udpateActionBar(int stateValue);
}
