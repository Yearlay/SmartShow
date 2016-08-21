package com.android.smartShow.adapter;

import java.util.ArrayList;

public class ModelMenuData {
    private String mMenuText;
    private ArrayList<String> mSubMenuText;
    public ModelMenuData(String menuText, ArrayList<String> subMenuText) {
        mMenuText = menuText;
        mSubMenuText = subMenuText;
    }

    public ArrayList<String> getSubText() {
        return mSubMenuText;
    }

    public boolean getIsExpand() {
        return mSubMenuText != null && mSubMenuText.size() > 0;
    }

    public String getMenuText() {
        return mMenuText;
    }
}
