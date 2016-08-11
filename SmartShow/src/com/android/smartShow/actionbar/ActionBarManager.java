package com.android.smartShow.actionbar;

import com.android.smartShow.activity.BaseActivity;

public class ActionBarManager {
    public static final int MAIN_ACTION_BAR = 1;
    
    BaseActionBar mActionBar;
    
    static ActionBarManager sActionBarManager;
    public static ActionBarManager instance() {
        if (sActionBarManager == null) {
            sActionBarManager = new ActionBarManager();
        }
        return sActionBarManager;
    }
    
    public BaseActionBar getActionBar(int acitonBarId, BaseActivity activity) {
        switch (acitonBarId) {
            case ActionBarManager.MAIN_ACTION_BAR:
                mActionBar = new MainActionBar(activity);
                break;

            default:
                break;
        }
        return mActionBar;
    }

}
