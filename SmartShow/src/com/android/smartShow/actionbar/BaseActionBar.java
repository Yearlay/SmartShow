package com.android.smartShow.actionbar;

import com.android.smartShow.activity.BaseActivity;

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
