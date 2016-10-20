package com.android.smartshowclient.adapter;

import java.util.ArrayList;
import java.util.List;

import com.android.smartshowclient.R;

import android.content.Context;
import android.graphics.drawable.Drawable;

public class StoreData {
    public static class StoreDataInfo {
        public String mStoreName;
        public Drawable mStoreDrawable;
        public StoreDataInfo() {
        }
    }
    
    private List<StoreDataInfo> mData;

    public List<StoreDataInfo> getData() {
        return mData;
    }

    public void setData(List<StoreDataInfo> mData) {
        this.mData = mData;
    }
    
    private static StoreData mStoreData;
    
    public static StoreData instance(Context context) {
        if (mStoreData == null) {
            mStoreData = new StoreData(context);
        }
        return mStoreData;
    }

    public StoreData(Context context) {
        mData = new ArrayList<StoreDataInfo>();
        StoreDataInfo storeDataInfo;
        for(int i = 0; i < 10;i++) {
            storeDataInfo = new StoreDataInfo();
            storeDataInfo.mStoreName = "测试门店";
            storeDataInfo.mStoreDrawable = context.getResources().getDrawable(R.drawable.store);
            mData.add(storeDataInfo);
        }
    }
}
