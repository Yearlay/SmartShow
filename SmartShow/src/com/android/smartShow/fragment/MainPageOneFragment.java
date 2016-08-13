
package com.android.smartShow.fragment;

import java.util.ArrayList;
import java.util.Map;

import com.android.smartShow.R;
import com.android.smartShow.view.StoreTitleView;

import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * 个人
 */
public class MainPageOneFragment extends BaseFragment 
            implements OnClickListener{
    private LinearLayout mStoreListLinearLayout;
    
    private ArrayList<StoreTitleView> mStoreItemList;
    private StoreTitleView mCurrentStoreTitleView;

    public ArrayList<StoreTitleView> getStoreItemList() {
        return mStoreItemList;
    }

    public void setStoreItemList(ArrayList<StoreTitleView> mStoreItemList) {
        this.mStoreItemList = mStoreItemList;
    }
    
    public StoreTitleView getCurrentStoreTitleView() {
        return mCurrentStoreTitleView;
    }
    
    public void setCurrentStoreTitleView(StoreTitleView currentStoreTitleView) {
        mCurrentStoreTitleView = currentStoreTitleView;
        mCurrentStoreTitleView.setSelected(true);
    }

    @Override
    public boolean handleNotifyMessage(Message msg) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean handleUIMessage(Message msg) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean handleMessage(Message msg) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onResult(int event, boolean isok, Map<String, Object> result) {
        // TODO Auto-generated method stub

    }

    @Override
    protected String setFragmentTag() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected void initData(Bundle bundle) {
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View view = inflater.inflate(R.layout.main_page_one, container, false);
        mStoreListLinearLayout = (LinearLayout) view.findViewById(R.id.store_list_linearlayout);
        
        mStoreItemList = new ArrayList<StoreTitleView>();
        String[] storeListStr = getResources().getStringArray(R.array.store_list);
        for (String storeItemStr : storeListStr) {
            StoreTitleView storeTitleView = new StoreTitleView(getContext());
            storeTitleView.setPageOneFragment(this);
            storeTitleView.getStoreTitle().setText(storeItemStr);
            mStoreItemList.add(storeTitleView);
        }
        for (StoreTitleView storeTitleView : mStoreItemList) {
            mStoreListLinearLayout.addView(storeTitleView);
        }
        if (mStoreItemList.size() > 0) {
            setCurrentStoreTitleView(mStoreItemList.get(0));
        }
        return view;
    }

    @Override
    public void onClick(View view) {
    }

}
