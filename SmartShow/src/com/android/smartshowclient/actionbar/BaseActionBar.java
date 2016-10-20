package com.android.smartshowclient.actionbar;

import com.android.smartshowclient.activity.BaseActivity;

import android.app.ActionBar;
import android.view.View.OnClickListener;

public abstract class BaseActionBar implements OnClickListener {
    
    public BaseActivity mActivity;
    public ActionBar mActionBar;
    public int mStateValue;
    
    public BaseActionBar(BaseActivity activity) {
        super();
        this.mActivity = activity;
        mActionBar = mActivity.getActionBar();
    }

    public void udpateActionBar(int stateValue) {
        mStateValue = stateValue;
    }
}
