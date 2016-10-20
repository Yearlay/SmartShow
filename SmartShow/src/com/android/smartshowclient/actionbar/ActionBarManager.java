package com.android.smartshowclient.actionbar;

import com.android.smartshowclient.activity.BaseActivity;

public class ActionBarManager {
    public static final int MAIN_ACTION_BAR = 1;
    public static final int DEVICE_ACTION_BAR = 2;
    public static final int SCREEN_ACTION_BAR = 3;
    
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
                
            case ActionBarManager.DEVICE_ACTION_BAR:
                mActionBar = new DeviceActionBar(activity);
                break;
                
            case ActionBarManager.SCREEN_ACTION_BAR:
                mActionBar = new ScreenActionBar(activity);

            default:
                break;
        }
        return mActionBar;
    }

}
